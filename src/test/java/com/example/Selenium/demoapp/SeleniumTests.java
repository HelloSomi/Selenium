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
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        driver = new ChromeDriver(options);

        driver.get("https://www.svtplay.se/");
        driver.findElement(By.xpath("//button[contains(text(), 'Acceptera alla') and contains(@class, 'sc-5b00349a-2')]")).click();
    }

    @AfterEach
    public void after() {
        //här använder vi oss av "getCurrentUrl()" metoden för att kontroller adressen som webbläsaren/browsern visar
        // vill vi kontrollera att vi har hamnat på rätt sida eller inte
        // sedan gör vi en gämförelse av adressen/URL.en med vår fördefinerade adress med equals() metoden
        //om Url.en inte matchar det angivna adressen så ska vi navigera tillbaka till föregående sida
        if (!driver.getCurrentUrl().equals("https://www.svtplay.se/")) {

            driver.navigate().back();
        }
    }

    // Check that the title of the website is correct
    @Test
    public void testTitle() {
        //här tar vi hjälp av metoden "getTitle()" för att hämta titeln på den aktuella sidan på webbsidan och returnerar Titeln på hemsidan alltså "SVT Play"
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
        //här letar vi efter alla HTML-element som matchar css-elemntet i parantesen i webbsidan och sparar de i en Webelement lista
        List<WebElement> links = driver.findElements(By.cssSelector(".sc-f8f66c85-3 li"));

        //Denna kodrad jämför om texten för det första elementet i listan "links" är lika med "START". Om så är fallet så passerar testet, annars misslyckas det
        assertEquals("START", links.get(0).getText());

        //Denna kodrad jämför om texten för det andra elementet i listan "links" är lika med "PROGRAM". Om så är fallet så passerar testet, annars misslyckas det
        assertEquals("PROGRAM", links.get(1).getText());

        //Denna kodrad jämför om texten för det tredje elementet i listan "links" är lika med "KANALER". Om så är fallet så passerar testet, annars misslyckas det
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

    @Test
    public void testAvailabilityLink2(){
        //webDriverWait är en klass i selenium biblioteket som möjliggör väntetider för att förhindra exekveringfel/ testfel t.ex. på grund av webbsidan inte hunnit ladda upp
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        //denna kod rad väntar tills en länk med en specifik del av URL-adressen är synlig på webbsidan och sedan returnerar förväntad länk
        WebElement availabilityLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@href,'https://kontakt.svt.se/guide/cookies-och-personuppgifter')]")));
        //här gör vi en kontroll på vår förväntade resultat och det faktiska resultatet matchar och returnerar ett boolean om länken är synligt för användaren eller inte
        assertTrue(availabilityLink.isDisplayed());

        //är en instans av Webelement som använder sig av availabilityLink och letar via xpath efter ett span element med texten "Om cookies och personuppgifter" och sedan sparar det i variabeln "linkText"
        WebElement linkText = availabilityLink.findElement(By.xpath("//span[contains(text(),'Om cookies och personuppgifter')]"));

      // här gämförs länktexten i linkText med vår förväntade text "Om cookies och personuppgifter" och om de är lika kommer testen att passera , annars misslyckas testet
        assertEquals("Om cookies och personuppgifter",linkText.getText());

        //denna rad kontrollerar om variabeln "linkText" är synlig på sidan eller inte , om den är synlig kommer den returnera True annars False
        assertTrue(linkText.isDisplayed());

    }

    // Follow the link Availability in SVT Play and check the main heading
    @Test
    public void testAvailabilityPage() {

        //Vi skapar ett objekt/instans av klassen WebDriverWait för att vänta på att en viss händelse inträffar på sidan. Vi anger här en timeout på 3 sekunder.
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        // Vi väntar på att länken med texten "Tillgänglighet i SVT Play" blir synlig på sidan.
        // Vi använder här ExpectedConditions.visibilityOfElementLocated för att vänta på att elementet blir synligt på sidan.
        WebElement availabilityLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Tillgänglighet i SVT Play")));

        // Click on the "Tillgänglighet i SVT Play" link
        availabilityLink.click();

        //här tar vi hjälp av "tagName" metoden för att leta efter alla tagg namn med det angivna taggnamnet i parametern "h1"
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
    public void testProgramPage() throws InterruptedException {
        Thread.sleep(3000);
        driver.get("https://www.svtplay.se/video/Kq2bMm5/jesus-i-orkelljunga");
        WebElement program = driver.findElement(By.cssSelector(".sc-c358b5df-0.sc-12ea861d-7.dpHpXG.frWRjt"));

        assertEquals("Jesus i Örkelljunga", program.getText());
    }

    @Test
    public void testGetTvLinkName() throws InterruptedException {
        Thread.sleep(2000);
        WebElement program = driver.findElements(By.cssSelector(".sc-1eacba3e-2.lkVDcA li")).get(2).findElement(By.cssSelector(".sc-1eacba3e-7.hLeofF"));

        assertEquals("TV", program.getText());
    }

    @Test
    public void testGetFirstCategoryInListLastChance(){

        List<WebElement> lastChance = driver.findElements(By.xpath("//section[@aria-label='Sista chansen']//f-4-2-0[contains(@class,'sc-b6440fda-0 cFwUm sc-c7947d35-2 lfA-dTd')]//article"));
        assertEquals(0 , lastChance.size());
    }

    @Test
    public void testGetComputerLinkName() throws InterruptedException {
        Thread.sleep(2000);
        WebElement program = driver.findElements(By.cssSelector(".sc-1eacba3e-2.lkVDcA li")).get(0).findElement(By.cssSelector(".sc-1eacba3e-7.hLeofF"));

        assertEquals("Dator",program.getText());
    }




    @AfterAll
    static void tearDown() {
        driver.quit();
    }

}