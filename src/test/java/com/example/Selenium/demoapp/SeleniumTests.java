package com.example.Selenium.demoapp;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SeleniumTests {
    private static WebDriver driver;

    @BeforeAll
    static void setUp() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\bin\\chromedriver.exe");
        driver = new ChromeDriver(options);

        driver.get("https://www.svtplay.se/");
        driver.findElement(By.xpath("//button[contains(text(), 'Acceptera alla') and contains(@class, 'sc-5b00349a-2')]")).click();
    }

    @AfterEach
    public void after() {
        if (!driver.getCurrentUrl().equals("https://www.svtplay.se/")) {
            driver.navigate().back();
        }
    }

    // Check that the title of the website is correct
    @Test
    public void testTitle() {
        assertEquals("SVT Play", driver.getTitle());
    }

    // Check that the website logo is visible
    @Test
    public void testLogo() {
        assertTrue(driver.findElement(By.cssSelector("a[data-rt='play-logo'] svg")).isDisplayed());
        //OR : assertTrue(driver.findElement(By.cssSelector(".sc-31022b15-0.bNFQDk")).isDisplayed());
        //OR : assertTrue(driver.findElement(By.className("sc-f8f66c85-1")).isDisplayed());
    }

    // Check the names of the three links in the main menu "Start, Programs, Channels"
    @Test
    public void testMainMenuLinks() {
        List<WebElement> links = driver.findElements(By.cssSelector(".sc-f8f66c85-3 li"));
        assertEquals("START", links.get(0).getText());
        assertEquals("PROGRAM", links.get(1).getText());
        assertEquals("KANALER", links.get(2).getText());
    }

    // Check that the link for "Availability in SVT Play" is visible and that the correct link text is displayed
    @Test
    public void testAvailabilityLink() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebElement availabilityLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Tillgänglighet i SVT Play")));
        //OR : WebElement availabilityLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@href,'https://kontakt.svt.se/guide/tillganglighet')]")));

        assertTrue(availabilityLink.isDisplayed());

        WebElement linkText = availabilityLink.findElement(By.xpath("//span[contains(text(),'Tillgänglighet i SVT Play')]"));

        assertEquals("Tillgänglighet i SVT Play", linkText.getText());
        assertTrue(linkText.isDisplayed());
    }

    // Follow the link Availability in SVT Play and check the main heading
    @Test
    public void testAvailabilityPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebElement availabilityLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Tillgänglighet i SVT Play")));

        // Click on the "Tillgänglighet i SVT Play" link
        availabilityLink.click();

        assertEquals("Så arbetar SVT med tillgänglighet", driver.findElement(By.tagName("h1")).getText());
    }

    // Use the "click()" method to navigate to the "Program" page. Check the number of categories listed.
    @Test
    public void testProgramPageCategoryCount() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div[role='main']")));
        WebElement programLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/program']")));

        programLink.click();

        WebElement categorySection = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("section[aria-label='Kategorier']")));
        List<WebElement> categoryList = categorySection.findElements(By.tagName("article"));
        assertEquals(17, categoryList.size());
    }

    // Write another 5 different tests for SVT Play where at least Locators for xpath, CSSselector and className are used
    @Test
    public void testFind_DragRaceSverige_Program() {
        WebElement program = driver.findElement(By.className("sc-c7947d35-4")).findElement(By.xpath("//a[@href='/drag-race-sverige']"));

        assertEquals("Drag Race Sverige", program.getAttribute("aria-label"));
    }

    @Test
    public void testGet_Uppenbarelsen_LinkFromFilmtips() {
        WebElement film = driver.findElement(By.xpath("//section[@aria-label='Filmtips']//f-4-2-0[contains(@class,'sc-c7947d35-4 dRNUOY')]//article//a[@aria-label='Uppenbarelsen']"));

        assertEquals("https://www.svtplay.se/video/jMd27LY/uppenbarelsen", film.getAttribute("href"));
    }

    @Test
    public void testChannelLink() {
        WebElement link = driver.findElement(By.xpath("//a[@href='/kanaler']"));

        assertEquals("KANALER", link.getText());
    }

    @Test
    public void testRecommendationsListCount() {
        List<WebElement> recommendations = driver.findElements(By.xpath("//section[@aria-label='Rekommenderat']//f-4-2-0[contains(@class,'sc-c7947d35-4 dRNUOY')]//article"));
        //List<WebElement> recommendations = driver.findElement(By.xpath("//section[@aria-label='Rekommenderat']")).findElement(By.cssSelector(".sc-c7947d35-4.dRNUOY")).findElements(By.tagName("article"));

        assertEquals(6, recommendations.size());
    }

    @Test
    public void testProgramPage() {
        driver.get("https://www.svtplay.se/video/Kq2bMm5/jesus-i-orkelljunga");
        WebElement program = driver.findElement(By.cssSelector(".sc-c358b5df-0.sc-12ea861d-7.dpHpXG.frWRjt"));

        assertEquals("Jesus i Örkelljunga", program.getText());
    }

    @Test
    public void test() throws InterruptedException {
        Thread.sleep(2000);
        WebElement program = driver.findElements(By.cssSelector(".sc-1eacba3e-2.lkVDcA li")).get(2).findElement(By.cssSelector(".sc-1eacba3e-7.hLeofF"));

        assertEquals("TV", program.getText());
    }


    // Test the search form by searching for “Agenda”




    @AfterAll
    static void tearDown() {
        driver.quit();
    }

}