package com.example.crawling_demo;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CrawlingDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrawlingDemoApplication.class, args).getBean(CrawlingDemoApplication.class).test();
	}

	public void test() {
		System.out.println("################### START ###################");

		// WebDriver 경로를 설정
		System.setProperty("webdriver.chrome.driver", "C:\\drivers\\chromedriver.exe");

		// WebDriver 옵션을 설정
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized"); // 최대크기로
		options.setPageLoadStrategy(PageLoadStrategy.NORMAL); // 페이지가 로드될 때까지 대기, Normal : 로드 이벤트 실행이 반환될 때까지 기다린다.

		// WebDriver 객체를 생성
		WebDriver driver = new ChromeDriver(options);

		// 크롤링할 페이지
		try {
			// 무신사 스토어 코디맵 페이지로 이동
			driver.get("https://www.musinsa.com/app/codimap/lists");



		} finally {

			// WebDriver 종료
			driver.quit();
			System.out.println("################### END ###################");
		}
	}
}
