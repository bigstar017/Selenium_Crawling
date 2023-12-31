package com.example.crawling_demo;

import com.example.dto.*;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class CrawlingDemoApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(CrawlingDemoApplication.class, args).getBean(CrawlingDemoApplication.class).test();
    }

    // 각각의 DB의 역할을 대신할 List들
    static List<COORDI> coordiList = new ArrayList<>();
    static List<TOP> topList = new ArrayList<>();
    static List<PANTS> pantsList = new ArrayList<>();
    static List<SHOES> shoesList = new ArrayList<>();
    static List<OUTER> outerList = new ArrayList<>();

    public void test() throws InterruptedException {
        System.out.println("################### START ###################");

        // WebDriver 경로를 설정
        System.setProperty("webdriver.chrome.driver", "C:\\drivers\\chromedriver.exe");

        // WebDriver 옵션을 설정
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--start-maximized"); // 최대크기로
        options.addArguments("--window-size=1920x1080");
        options.addArguments("--headless"); // 브라우저 직접 띄우지 않음
        options.addArguments("--disable-gpu");
        options.addArguments("--remote-allow-origins=*"); // Websocket connection 에러 해결
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL); // 페이지가 로드될 때까지 대기, Normal : 로드 이벤트 실행이 반환될 때까지 기다린다.

        // WebDriver 객체를 생성
        WebDriver driver = new ChromeDriver(options);

        // 웹페이지가 충분히 로딩되지 않아서 생기는 문제 방지
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

        try {
            // 무신사 스토어 코디맵 페이지로 이동
            driver.navigate().to("https://www.musinsa.com/app/codimap/lists");

            // 남자 옷을 봐야하기 때문에 우 하단 '남성' 클릭
            driver.findElement(By.cssSelector("body > div.wrap > div.right_area > div.global-filter > button.global-filter__button.global-filter__button--mensinsa")).click();

            // 스타일 카테고리 선택 : 캐주얼(2) / 댄디(4) / 포멀(5) / 스트릿(9)
            int[] categoryNum = {2, 4, 5, 9};
            for (int i = 0; i < categoryNum.length; i++) {
                String mainURL = driver.getCurrentUrl();
                switch (categoryNum[i]) {
                    case 2:
                        System.out.println("################### 캐주얼 코디맵 크롤링 시작 ###################");
                        break;
                    case 4:
                        System.out.println("################### 댄디 코디맵 크롤링 시작 ###################");
                        break;
                    case 5:
                        System.out.println("################### 포멀 코디맵 크롤링 시작 ###################");
                        break;
                    case 9:
                        System.out.println("################### 스트릿 코디맵 크롤링 시작 ###################");
                        break;
                }

                // 각 스타일 카테고리에서 크롤링 실행 메서드
                detailCoordi(driver, categoryNum[i], mainURL);
            }
        } catch (Exception e) {
            System.out.println("Exception 1");
            e.printStackTrace();
        } finally {
            // WebDriver 종료
            driver.quit();
            System.out.println("################### END ###################");
        }
    }

    private void detailCoordi(WebDriver driver, int categoryNum, String mainURL) {
        driver.navigate().to(mainURL);
        // 캐쥬얼은 30개 페이지 크롤링 나머지는 20개 페이지 크롤링
        int num = 2;
        if (categoryNum == 2) {
            num = 3;
        }

        for (int page = 0; page < num; page++) {
            outer: for (int i = 3; i <= 12; i++) {
                for (int j = 1; j <= 60; j++) {
                    try {
                        // 각 코디를 선택하기 전 카테고리와 현재 페이지를 클릭
                        driver.findElement(By.cssSelector("#catelist > div:nth-child(2) > div > dl > dd > ul > li:nth-child(" + categoryNum + ") > a")).click();
                        if (page > 0) {
                            for (int k = 0; k < page; k++) {
                                driver.findElement(By.cssSelector("body > div.wrap > div.right_area > form > div.right_contents.hover_box > div > div.pagination-box.box > div > div > a:nth-child(13)")).click();
                            }
                        }
                        driver.findElement(By.cssSelector("body > div.wrap > div.right_area > form > div.right_contents.hover_box > div > div.pagination-box.box > div > div > a:nth-child(" + i + ")")).click();

                        // 현재 카테고리 이름
                        String styleCategory = driver.findElement(By.cssSelector("#catelist > div:nth-child(2) > div > dl > dd > ul > li:nth-child(" + categoryNum + ") > a")).getText();
                        styleCategory = styleCategory.substring(3);

                        // 각 코디의 디테일로 이동
                        driver.findElement(By.cssSelector("body > div.wrap > div.right_area > form > div.right_contents.hover_box > div > ul > li:nth-child(" + j + ") > div.style-list-item__thumbnail > a")).click();

                        // 코디 디테일 주소 저장
                        String coordiURL = driver.getCurrentUrl();

                        // 코디 상품 항목 탐색 메서드
                        detailGoods(styleCategory, driver, coordiURL);
                    } catch (Exception e) {
                        System.out.println("detailCoordi 부분 실행 중 예외 발생");
                        e.printStackTrace();
                    } finally {
                        driver.navigate().to(mainURL);
                    }
                }
                System.out.println("################### Next Page : " + (page * 10 + (i - 1)) + "Page ###################");
            }
        }
    }

    private void detailGoods(String styleCategory, WebDriver driver, String coordiURL) throws InterruptedException {
        // 코디 정보 저장
        COORDI coordi = new COORDI();
        coordi.setSTYLE_CATEGORY(styleCategory);

        // 각 코디 상품 디테일로 이동
        int itemNum = driver.findElements(By.cssSelector("#style_info > div.styling_goods.codimap-goods > div > div > div > div.styling_list.swiper-wrapper > div")).size();
        System.out.println("################### 등록되어 있는 코디 상품의 갯수 : " + itemNum + " ###################");

        for (int i = 1; i <= itemNum; i++) {
            try {
                if (i == 1) {
                    driver.findElement(By.cssSelector("#style_info > div.styling_goods.codimap-goods > div > div > div > div.styling_list.swiper-wrapper > div.swiper-slide.style_contents_size.swiper-slide-active > a.brand_item")).click();
                    saveData(driver, coordiURL);
                } else if (i == 2) {
                    driver.findElement(By.cssSelector("#style_info > div.styling_goods.codimap-goods > div > div > div > div.styling_list.swiper-wrapper > div.swiper-slide.style_contents_size.swiper-slide-next > a.brand_item")).click();
                    saveData(driver, coordiURL);
                } else {
                    driver.findElement(By.cssSelector("#style_info > div.styling_goods.codimap-goods > div > div > div > div.styling_list.swiper-wrapper > div:nth-child(" + i + ") > a.brand_item")).click();
                    saveData(driver, coordiURL);
                }
            } catch (Exception e) {
                System.out.println("Exception 3");
                // 뒤로가기를 다시 실행하고, 그 다음 디테일로 이동하도록 재 작성
                if (driver.getCurrentUrl().contains("goods")) {
                    driver.navigate().back();

                    if (i == 1) {
                        driver.findElement(By.cssSelector("#style_info > div.styling_goods.codimap-goods > div > div > div > div.styling_list.swiper-wrapper > div.swiper-slide.style_contents_size.swiper-slide-active > a.brand_item")).click();
                        saveData(driver, coordiURL);
                    } else if (i == 2) {
                        driver.findElement(By.cssSelector("#style_info > div.styling_goods.codimap-goods > div > div > div > div.styling_list.swiper-wrapper > div.swiper-slide.style_contents_size.swiper-slide-next > a.brand_item")).click();
                        saveData(driver, coordiURL);
                    } else {
                        driver.findElement(By.cssSelector("#style_info > div.styling_goods.codimap-goods > div > div > div > div.styling_list.swiper-wrapper > div:nth-child(" + i + ") > a.brand_item")).click();
                        saveData(driver, coordiURL);
                    }
                }
            }
        }
    }

    private void saveData(WebDriver driver, String coordiURL) throws InterruptedException {
        // 선택한 상품이 어떤 종류인지 판단
        String productInfo = driver.findElement(By.cssSelector("#page_product_detail > div.right_area.page_detail_product > div.right_contents.section_product_summary > div.product_info > p > a:nth-child(1)")).getText();
        try {
            if (productInfo.equals("상의")) {
                TOP top = new TOP();
                top.setTOP_ID(topList.size()); // 임시로 상의 키는 리스트의 크기로 한다.

                String tmpBrand = driver.findElement(By.cssSelector("#page_product_detail > div.right_area.page_detail_product > div.right_contents.section_product_summary > div.product_info > p > a:nth-child(3)")).getText();
                int brandLength = tmpBrand.length();
                top.setBRAND(tmpBrand.substring(1, brandLength - 1));

                top.setCATEGORY(driver.findElement(By.cssSelector("#page_product_detail > div.right_area.page_detail_product > div.right_contents.section_product_summary > div.product_info > p > a:nth-child(2)")).getText());

                top.setCOLOR(getColor(driver));

                // 오류 발견 : 옷의 시즌 정보가 누락되어 있는 제품이 있음
                try {
                    String clothSeason = driver.findElement(By.cssSelector("#product_order_info > div.explan_product.product_info_section > ul > li:nth-child(2) > p.product_article_contents > strong")).getText().substring(5);
                    if (clothSeason.charAt(0) == 'L') {
                        top.setSEASON("ALL");
                    } else {
                        top.setSEASON(clothSeason);
                    }
                } catch (Exception f) {

                }

                top.setITEM(driver.findElement(By.cssSelector("#page_product_detail > div.right_area.page_detail_product > div.right_contents.section_product_summary > span > em")).getText());

                String tmpPrice = driver.findElement(By.cssSelector("#list_price")).getText();
                if (tmpPrice.contains("~")) {
                    tmpPrice = tmpPrice.substring(tmpPrice.indexOf("~") + 2);
                    tmpPrice = tmpPrice.replace(",", "");
                    tmpPrice = tmpPrice.replace("원", "");
                } else {
                    tmpPrice = tmpPrice.replace(",", "");
                    tmpPrice = tmpPrice.replace("원", "");
                }
                top.setPRICE(Integer.parseInt(tmpPrice));

                top.setIMG(driver.findElement(By.cssSelector("#bigimg")).getAttribute("src"));

                top.setURL(driver.getCurrentUrl());

                topList.add(top);
                System.out.println("top : " + top.toString());
            } else if (productInfo.equals("바지")) {
                PANTS pants = new PANTS();
                pants.setPANTS_ID(pantsList.size());

                String tmpBrand = driver.findElement(By.cssSelector("#page_product_detail > div.right_area.page_detail_product > div.right_contents.section_product_summary > div.product_info > p > a:nth-child(3)")).getText();
                int brandLength = tmpBrand.length();
                pants.setBRAND(tmpBrand.substring(1, brandLength - 1));

                pants.setCATEGORY(driver.findElement(By.cssSelector("#page_product_detail > div.right_area.page_detail_product > div.right_contents.section_product_summary > div.product_info > p > a:nth-child(2)")).getText());

                pants.setCOLOR(getColor(driver));

                // 오류 발견 : 옷의 시즌 정보가 누락되어 있는 제품이 있음
                try {
                    String clothSeason = driver.findElement(By.cssSelector("#product_order_info > div.explan_product.product_info_section > ul > li:nth-child(2) > p.product_article_contents > strong")).getText().substring(5);
                    if (clothSeason.charAt(0) == 'L') {
                        pants.setSEASON("ALL");
                    } else {
                        pants.setSEASON(clothSeason);
                    }
                } catch (Exception f) {

                }

                pants.setITEM(driver.findElement(By.cssSelector("#page_product_detail > div.right_area.page_detail_product > div.right_contents.section_product_summary > span > em")).getText());

                String tmpPrice = driver.findElement(By.cssSelector("#list_price")).getText();
                if (tmpPrice.contains("~")) {
                    tmpPrice = tmpPrice.substring(tmpPrice.indexOf("~") + 2);
                    tmpPrice = tmpPrice.replace(",", "");
                    tmpPrice = tmpPrice.replace("원", "");
                } else {
                    tmpPrice = tmpPrice.replace(",", "");
                    tmpPrice = tmpPrice.replace("원", "");
                }
                pants.setPRICE(Integer.parseInt(tmpPrice));

                pants.setIMG(driver.findElement(By.cssSelector("#bigimg")).getAttribute("src"));

                pants.setURL(driver.getCurrentUrl());
                pantsList.add(pants);
                System.out.println("pants : " + pants.toString());
            } else if (productInfo.equals("스니커즈") || productInfo.equals("신발")) {
                SHOES shoes = new SHOES();
                shoes.setSHOES_ID(shoesList.size());

                String tmpBrand = driver.findElement(By.cssSelector("#page_product_detail > div.right_area.page_detail_product > div.right_contents.section_product_summary > div.product_info > p > a:nth-child(3)")).getText();
                int brandLength = tmpBrand.length();
                shoes.setBRAND(tmpBrand.substring(1, brandLength - 1));

                shoes.setCATEGORY(driver.findElement(By.cssSelector("#page_product_detail > div.right_area.page_detail_product > div.right_contents.section_product_summary > div.product_info > p > a:nth-child(2)")).getText());

                shoes.setCOLOR(getColor(driver));

                // 오류 발견 : 옷의 시즌 정보가 누락되어 있는 제품이 있음
                try {
                    String clothSeason = driver.findElement(By.cssSelector("#product_order_info > div.explan_product.product_info_section > ul > li:nth-child(2) > p.product_article_contents > strong")).getText().substring(5);
                    if (clothSeason.charAt(0) == 'L') {
                        shoes.setSEASON("ALL");
                    } else {
                        shoes.setSEASON(clothSeason);
                    }
                } catch (Exception f) {

                }

                shoes.setITEM(driver.findElement(By.cssSelector("#page_product_detail > div.right_area.page_detail_product > div.right_contents.section_product_summary > span > em")).getText());

                String tmpPrice = driver.findElement(By.cssSelector("#list_price")).getText();
                if (tmpPrice.contains("~")) {
                    tmpPrice = tmpPrice.substring(tmpPrice.indexOf("~") + 2);
                    tmpPrice = tmpPrice.replace(",", "");
                    tmpPrice = tmpPrice.replace("원", "");
                } else {
                    tmpPrice = tmpPrice.replace(",", "");
                    tmpPrice = tmpPrice.replace("원", "");
                }
                shoes.setPRICE(Integer.parseInt(tmpPrice));

                shoes.setIMG(driver.findElement(By.cssSelector("#bigimg")).getAttribute("src"));

                shoes.setURL(driver.getCurrentUrl());
                shoesList.add(shoes);
                System.out.println("shoes : " + shoes.toString());
            } else if (productInfo.equals("아우터")) {
                OUTER outer = new OUTER();
                outer.setOUTER_ID(outerList.size());

                String tmpBrand = driver.findElement(By.cssSelector("#page_product_detail > div.right_area.page_detail_product > div.right_contents.section_product_summary > div.product_info > p > a:nth-child(3)")).getText();
                int brandLength = tmpBrand.length();
                outer.setBRAND(tmpBrand.substring(1, brandLength - 1));

                outer.setCATEGORY(driver.findElement(By.cssSelector("#page_product_detail > div.right_area.page_detail_product > div.right_contents.section_product_summary > div.product_info > p > a:nth-child(2)")).getText());

                outer.setCOLOR(getColor(driver));

                // 오류 발견 : 옷의 시즌 정보가 누락되어 있는 제품이 있음
                try {
                    String clothSeason = driver.findElement(By.cssSelector("#product_order_info > div.explan_product.product_info_section > ul > li:nth-child(2) > p.product_article_contents > strong")).getText().substring(5);
                    if (clothSeason.charAt(0) == 'L') {
                        outer.setSEASON("ALL");
                    } else {
                        outer.setSEASON(clothSeason);
                    }
                } catch (Exception f) {

                }

                outer.setITEM(driver.findElement(By.cssSelector("#page_product_detail > div.right_area.page_detail_product > div.right_contents.section_product_summary > span > em")).getText());

                String tmpPrice = driver.findElement(By.cssSelector("#list_price")).getText();
                if (tmpPrice.contains("~")) {
                    tmpPrice = tmpPrice.substring(tmpPrice.indexOf("~") + 2);
                    tmpPrice = tmpPrice.replace(",", "");
                    tmpPrice = tmpPrice.replace("원", "");
                } else {
                    tmpPrice = tmpPrice.replace(",", "");
                    tmpPrice = tmpPrice.replace("원", "");
                }
                outer.setPRICE(Integer.parseInt(tmpPrice));

                outer.setIMG(driver.findElement(By.cssSelector("#bigimg")).getAttribute("src"));

                outer.setURL(driver.getCurrentUrl());
                outerList.add(outer);
                System.out.println("outer : " + outer.toString());
            }
        } catch (Exception e) {
            System.out.println("");
            e.printStackTrace();
        } finally {
            driver.navigate().to(coordiURL);
        }
    }

    private String getColor(WebDriver driver) {
        return null;
    }
}
