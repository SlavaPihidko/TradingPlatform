import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Login {
    FirefoxDriver wd;


    @BeforeMethod
    public void setUp() throws Exception {
        wd = new FirefoxDriver();
        wd.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    }

    @Test
    public void Login() throws InterruptedException {
        wd.get("http://146.71.78.211/");
        wd.findElement(By.linkText("Login")).click();
        wd.findElement(By.name("email")).click();
        wd.findElement(By.name("email")).clear();
        wd.findElement(By.name("email")).sendKeys("tel.0931513382@gmail.com");
        wd.findElement(By.name("password")).click();
        wd.findElement(By.name("password")).clear();
        wd.findElement(By.name("password")).sendKeys("TestPureDex1234567");

        // работа с фреймом
        // WebElement frameElement = wd.findElement(By.tagName("iframe"));
        WebElement frameElement = wd.findElement(By.tagName("iframe"));
        wd.switchTo().frame(frameElement);
        wd.findElement(By.cssSelector("div.recaptcha-checkbox-checkmark")).click();
        wd.switchTo().defaultContent();

        wd.findElement(By.className("square-btn")).click();
        wd.wait(10);
        wd.findElement(By.linkText("Wallets")).click();
    }
}
