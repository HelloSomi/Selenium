package com.example.Selenium.demoapp;

import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(Cucumber.class)
@CucumberContextConfiguration
@SpringBootTest
@CucumberOptions(features = "src/test/resources")
public class StepDefinition {
    private static WebDriver driver;

    @BeforeAll
    public static void setUp() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\bin\\chromedriver.exe");
        driver = new ChromeDriver(options);
    }

    @AfterAll
    public static void tearDown() {
        driver.close();
        driver.quit();
    }

    @Given("User is on the homepage")
    public void userIsOnTheHomepage() throws InterruptedException {

        driver.get("https://www.svtplay.se/");
        WebElement cookiePopup = driver.findElement(By.xpath("//button[contains(text(), 'Acceptera alla') and contains(@class, 'sc-5b00349a-2')]"));
        if(cookiePopup.isDisplayed())
            cookiePopup.click();
    }

    @Then("User should see the correct website title {string}")
    public void userShouldSeeTheCorrectWebsiteTitle(String expectedTitle) {
        String actualTitle = driver.getTitle();
        Assertions.assertEquals(expectedTitle, actualTitle);
    }

    @Then("User should see the website logo")
    public void userShouldSeeTheWebsiteLogo() {
        assertTrue(driver.findElement(By.cssSelector("a[data-rt='play-logo'] svg")).isDisplayed());
    }

    @Then("User should see the links {string} in the main menu")
    public void userShouldSeeTheLinksInTheMainMenu(String expectedLinks) {
        List<WebElement> links = driver.findElements(By.cssSelector(".sc-f8f66c85-3 li"));
        assertEquals(expectedLinks, links.get(0).getText() + ", " + links.get(1).getText() + ", " + links.get(2).getText());
    }

    @Then("User should see the link {string} with correct link text")
    public void userShouldSeeTheLinkWithCorrectLinkText(String expectedLinkText) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement availabilityLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Tillgänglighet i SVT Play")));

        assertTrue(availabilityLink.isDisplayed());

        WebElement linkText = availabilityLink.findElement(By.xpath("//span[contains(text(),'Tillgänglighet i SVT Play')]"));

        assertEquals(expectedLinkText, linkText.getText());
        assertTrue(linkText.isDisplayed());
    }

    @When("User clicks the link Tillgänglighet i SVT Play")
    public void userClicksTheLinkForAvailabilityInSVTPlay() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement availabilityLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Tillgänglighet i SVT Play")));

        assertTrue(availabilityLink.isDisplayed());

        availabilityLink.click();
    }

    @Then("User should see the main heading {string}")
    public void userShouldSeeTheMainHeading(String expectedHeading) {
        assertEquals(expectedHeading, driver.findElement(By.tagName("h1")).getText());
    }

    @When("User clicks the Programs link")
    public void userNavigatesToTheProgramsPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div[role='main']")));
        WebElement programLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/program']")));

        programLink.click();
    }

    @Then("User should see {int} categories listed")
    public void userShouldSeeCategoriesListed(int expectedCount) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement categorySection = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("section[aria-label='Kategorier']")));
        List<WebElement> categoryList = categorySection.findElements(By.tagName("article"));
        assertEquals(expectedCount, categoryList.size());
    }

    @Then("User should see the {string} link in the footer with correct link text")
    public void userShouldSeeKontaktLinkInTheFooter(String expectedLinkText){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        //wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div[data-rt='footer-buttons']")));
        WebElement contactLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='https://kontakt.svt.se']")));

        assertTrue(contactLink.isDisplayed());
        assertEquals(expectedLinkText, contactLink.findElement(By.cssSelector(".sc-5b00349a-5")).getText());
    }

    @Then("User should see {string} movie in the FilmTips section")
    public void userShouldSeeUppenbarelsenInTheFilmTips(String expectedMovie) throws InterruptedException {
        Thread.sleep(5000);
        assertTrue(driver.findElement(
                        By.xpath("//section[@aria-label='Filmtips']//f-4-2-0[contains(@class,'sc-c7947d35-4 dRNUOY')]//article//a[@aria-label='"+expectedMovie+"']"))
                .isDisplayed());
    }

    @Then("User should see the {string} program")
    public void userShpuldSeeDragRaceSverigeProgram(String expectedProgram) throws InterruptedException {
        Thread.sleep(5000);

        WebElement program = driver.findElement(By.className("sc-c7947d35-4")).findElement(By.xpath("//a[@href='/drag-race-sverige']"));

        assertEquals(expectedProgram, program.getAttribute("aria-label"));
    }

    @Then("User should see {int} items in recommendation rail")
    public void userShouldSeeValidNumberOfItemsInRecommendationRail(int expectedCount) {
        List<WebElement> recommendations = driver.findElements(By.xpath("//section[@aria-label='Rekommenderat']//f-4-2-0[contains(@class,'sc-c7947d35-4 dRNUOY')]//article"));
        //List<WebElement> recommendations = driver.findElement(By.xpath("//section[@aria-label='Rekommenderat']")).findElement(By.cssSelector(".sc-c7947d35-4.dRNUOY")).findElements(By.tagName("article"));

        assertEquals(expectedCount, recommendations.size());
    }

    @When("User inputs {string} on the search form and submits")
    public void userInputsValueOnTheSearchForm(String searchTerm) throws InterruptedException {
        Thread.sleep(5000);

        WebElement searchField = driver.findElement(By.name("q"));
        searchField.clear();
        searchField.sendKeys(searchTerm);
    }

    @Then("User should see {string} on the search result")
    public void userShouldSeeSearchResultOnTheSearchForm(String expectedResult) throws InterruptedException {

        Thread.sleep(5000);

        List<WebElement> searchResults = driver.findElements(By.cssSelector(".sc-9b48bf1b-1.inJkRt li"));

        assertTrue(searchResults.size() > 0);
        assertEquals(expectedResult, searchResults.get(0).getText());
    }
}
