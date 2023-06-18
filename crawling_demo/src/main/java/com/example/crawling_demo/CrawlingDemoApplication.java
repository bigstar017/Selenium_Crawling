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

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class CrawlingDemoApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(CrawlingDemoApplication.class, args).getBean(CrawlingDemoApplication.class).test();
    }

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
        options.addArguments("--start-maximized"); // 최대크기로
        options.addArguments("--remote-allow-origins=*"); // Websocket connection 에러 해결
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL); // 페이지가 로드될 때까지 대기, Normal : 로드 이벤트 실행이 반환될 때까지 기다린다.

        // WebDriver 객체를 생성
        WebDriver driver = new ChromeDriver(options);

        // 크롤링할 페이지
        try {
            // 무신사 스토어 코디맵 페이지로 이동
            driver.navigate().to("https://www.musinsa.com/app/codimap/lists");

            // 남자 옷을 봐야하기 때문에 우 하단 '남성' 클릭
            WebElement gotoMen = driver.findElement(By.cssSelector("body > div.wrap > div.right_area > div.global-filter > button.global-filter__button.global-filter__button--mensinsa"));
            gotoMen.click();
            Thread.sleep(1000);

            // 스타일 카테고리 선택 : 캐주얼(2) / 댄디(4) / 포멀(5) / 스트릿(9)
//            int[] categoryNum = {2, 4, 5, 9};
            int[] categoryNum = {2};
            for (int i = 0; i < categoryNum.length; i++) {
                String styleCategory = driver.findElement(By.cssSelector("#catelist > div:nth-child(2) > div > dl > dd > ul > li:nth-child(" + categoryNum[i] + ") > a")).getText();
                styleCategory = styleCategory.substring(3);
                driver.findElement(By.cssSelector("#catelist > div:nth-child(2) > div > dl > dd > ul > li:nth-child(" + categoryNum[i] + ") > a")).click();
                Thread.sleep(1000);

                // 각 카테고리에서 크롤링 실행 메서드
                data(styleCategory, driver);

//				driver.navigate().back();
            }

        } finally {
            // WebDriver 종료
//			driver.quit();
            System.out.println("################### END ###################");
        }
    }

    private void data(String styleCategory, WebDriver driver) throws InterruptedException {
        for (int i = 1; i <= 60; i++) {
            // 각 코디 디테일로 이동
            driver.findElement(By.cssSelector("body > div.wrap > div.right_area > form > div.right_contents.hover_box > div > ul > li:nth-child(" + i + ") > div.style-list-item__thumbnail > a")).click();
            Thread.sleep(1000);

            // 코디 상품 항목 탐색 메서드
            detail(styleCategory, driver);

            driver.navigate().back();
            Thread.sleep(1000);
        }

        COORDI coordi = new COORDI();
        coordi.setSTYLE_CATEGORY(styleCategory);
    }

    private void detail(String styleCategory, WebDriver driver) throws InterruptedException {

        // 각 코디 상품 디테일로 이동
        int itemNum = driver.findElements(By.cssSelector("#style_info > div.styling_goods.codimap-goods > div > div > div > div.styling_list.swiper-wrapper > div")).size();
        System.out.println(itemNum);

        for (int i = 1; i <= itemNum; i++) {
            if (i == 1) {
                driver.findElement(By.cssSelector("#style_info > div.styling_goods.codimap-goods > div > div > div > div.styling_list.swiper-wrapper > div.swiper-slide.style_contents_size.swiper-slide-active > div.box-img > a")).click();
                Thread.sleep(2000);
            }

            else if (i == 2) {
                driver.findElement(By.cssSelector("#style_info > div.styling_goods.codimap-goods > div > div > div > div.styling_list.swiper-wrapper > div.swiper-slide.style_contents_size.swiper-slide-next > div.box-img > a")).click();
                Thread.sleep(2000);
            }

            else {
                driver.findElement(By.cssSelector("#style_info > div.styling_goods.codimap-goods > div > div > div > div.styling_list.swiper-wrapper > div:nth-child(" + i + ") > div.box-img > a")).click();
                Thread.sleep(2000);
            }

            driver.navigate().back();
        }

    }
}
