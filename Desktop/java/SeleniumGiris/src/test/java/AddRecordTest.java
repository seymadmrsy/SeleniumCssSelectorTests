import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.testng.Assert;

import java.time.Duration;


public class AddRecordTest {
    public static void main(String[] args) throws InterruptedException {

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://demoqa.com/webtables");

        //Sayfada Add yeri bulundu ve tıklandı
        WebElement add = driver.findElement(By.cssSelector("#addNewRecordButton"));
        System.out.println("ID ile Bulunan CssSelector: " + add.getText());
        add.click();

        //Formda doldurulu
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


        //düzenleme işlemi yapılıyor
        WebElement editBtn = driver.findElement(By.cssSelector("#edit-record-4"));
        //sayfada reklam olduğu için sayfa ortalandı
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", editBtn);
        editBtn.click();


        //elemanları update et
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

        //update edilen elementler tabloda yerleri cssSelector ile bulundu.
        WebElement updateKontrolSalary = driver.findElement(By.cssSelector(".rt-tr-group:nth-child(4) .rt-td:nth-child(5)"));
        WebElement updateKontrolDepartment = driver.findElement(By.cssSelector(".rt-tr-group:nth-child(4) .rt-td:nth-child(6)"));
        System.out.println("Kontrol Salary: " + updateKontrolSalary.getText());
        System.out.println("Kontrol Department: " + updateKontrolDepartment.getText());

        //tablodan metinler alındı.
        String actualSalary = updateKontrolSalary.getText();
        String actualDepartment = updateKontrolDepartment.getText();

        //Konsola yazdırıldı
        System.out.println("Güncellenen Salary: " + actualSalary);
        System.out.println("Güncellenen Depertman: " + actualDepartment);

        //assert ile doğrulama yapıldı
        Assert.assertEquals("50000", actualSalary,"Güncelenen salary yanlış!");
        Assert.assertEquals("QA Tester",actualDepartment,"Güncellenen depertman yanlış!");





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