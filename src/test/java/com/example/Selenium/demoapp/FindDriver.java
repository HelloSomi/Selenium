package com.example.Selenium.demoapp;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
public class FindDriver {

@Test
    void  findMyDriver(){

//Setting system properties of ChromeDriver
        System.setProperty("webdriver.chrome.driver", "C://Selenium-java browserstack//chromedriver_win32//chromedriver.exe");

//Creating an object of ChromeDriver
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

//Deleting all the cookies
        driver.manage().deleteAllCookies();

//Specifiying pageLoadTimeout and Implicit wait
        driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

//launching the specified URL
        driver.get("https://www.google.com/");

//Locating the elements using name locator for the text box
        driver.findElement(By.name("q")).sendKeys("YouTube");

//name locator for google search button
        WebElement searchIcon = driver.findElement(By.name("btnK"));
        searchIcon.click();
    }
}
