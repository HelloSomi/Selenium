package com.example.Selenium.demoapp;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ApplicationTests {

    @Test
    void contextLoads() {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        WebDriver driver = new ChromeDriver(options);

        driver.get("https://www.iths.se/");

        WebElement websiteHeading = driver.findElement(By.xpath("\n" +
                "//*[@id=\"frontpage\"]/div/div[1]/div/div/div[1]/h1"));
        String websiteHeadingText = websiteHeading.getText();

        assertEquals("Här startar din IT-karriär! ", websiteHeadingText,"Huvudrubriken är fel");

        driver.quit();



    }

}
