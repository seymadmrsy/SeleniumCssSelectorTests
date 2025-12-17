import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AddRecordTest {

    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://demoqa.com/webtables");
    }

    @Test
    public void addAndUpdateRecordTest() throws InterruptedException {

        // Sayfada Add yeri bulundu ve tıklandı
        WebElement add = driver.findElement(By.cssSelector("#addNewRecordButton"));
        System.out.println("ID ile Bulunan CssSelector: " + add.getText());
        add.click();

        // Form dolduruluyor
        driver.findElement(By.cssSelector("#firstName")).sendKeys("Şeyma");
        bekle(2);
        driver.findElement(By.cssSelector("#lastName")).sendKeys("DEMİRSOY");
        bekle(2);
        driver.findElement(By.cssSelector("#userEmail")).sendKeys("seymadmrsy@gmail.com");
        bekle(2);
        driver.findElement(By.cssSelector("#age")).sendKeys("29");
        bekle(2);
        driver.findElement(By.cssSelector("#salary")).sendKeys("35000");
        bekle(2);
        driver.findElement(By.cssSelector("#department")).sendKeys("Test Uzmanı");
        bekle(2);
        driver.findElement(By.cssSelector("#submit")).click();
        bekle(2);

        // Düzenleme işlemi
        WebElement editBtn = driver.findElement(By.cssSelector("#edit-record-4"));

        // Reklamdan dolayı sayfa ortalanıyor
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", editBtn);
        editBtn.click();

        // Alanları güncelle
        WebElement salaryBox = driver.findElement(By.cssSelector("#salary"));
        salaryBox.clear();
        salaryBox.sendKeys("50000");
        bekle(2);

        WebElement departmentBox = driver.findElement(By.cssSelector("#department"));
        departmentBox.clear();
        departmentBox.sendKeys("QA Tester");
        bekle(2);

        driver.findElement(By.cssSelector("#submit")).click();
        bekle(2);

        // Güncellenen değerleri tabloda kontrol et
        WebElement updateKontrolSalary =
                driver.findElement(By.cssSelector(".rt-tr-group:nth-child(4) .rt-td:nth-child(5)"));
        WebElement updateKontrolDepartment =
                driver.findElement(By.cssSelector(".rt-tr-group:nth-child(4) .rt-td:nth-child(6)"));

        String actualSalary = updateKontrolSalary.getText();
        String actualDepartment = updateKontrolDepartment.getText();

        System.out.println("Güncellenen Salary: " + actualSalary);
        System.out.println("Güncellenen Department: " + actualDepartment);

        // Assert doğrulamaları
        Assert.assertEquals(actualSalary, "50000", "Güncellenen salary yanlış!");
        Assert.assertEquals(actualDepartment, "QA Tester", "Güncellenen department yanlış!");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    public static void bekle(int saniye) {
        try {
            Thread.sleep(saniye * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Bekleme hatası: " + e.getMessage());
        }
    }
}
