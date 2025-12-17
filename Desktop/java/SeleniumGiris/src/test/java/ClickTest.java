import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ClickTest {

    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://demoqa.com/elements");
    }

    @Test
    public void dynamicClickTest() throws InterruptedException {

        // CssSelector ile Buttons bulundu
        WebElement buttonClick = driver.findElement(By.cssSelector("div ul li:nth-child(5)"));
        System.out.println("Bulunan değer : " + buttonClick.getText());
        buttonClick.click();

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", buttonClick);

        WebElement clickMe = driver.findElement(By.cssSelector("div.mt-4:nth-child(4) button"));
        System.out.println("Bulunan buton : " + clickMe.getText());
        clickMe.click();

        Thread.sleep(3000);

        WebElement dogruMu = driver.findElement(By.cssSelector("#dynamicClickMessage"));
        System.out.println("Mesaj : " + dogruMu.getText());

        String actualMessage = dogruMu.getText();
        String expectedMessage = "You have done a dynamic click";

        Assert.assertEquals(actualMessage, expectedMessage, "Mesaj beklenen şekilde gelmedi!");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
