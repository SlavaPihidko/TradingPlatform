import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.TimeUnit;

public class TestBase {
  FirefoxDriver wd;

  @BeforeMethod
  public void setUp() throws Exception {
    wd = new FirefoxDriver();
    wd.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    wd.get("http://209.182.216.247/admin");
    loginToAdminPanel("tel.0931513382@gmail.com", "Coin4Coin12345");
  }

  private void loginToAdminPanel(String adminEmail, String adminPassword) {
    wd.findElement(By.name("email")).click();
    wd.findElement(By.name("email")).clear();
    wd.findElement(By.name("email")).sendKeys(adminEmail);
    wd.findElement(By.name("password")).click();
    wd.findElement(By.name("password")).clear();
    wd.findElement(By.name("password")).sendKeys(adminPassword);
    wd.findElement(By.className("blue_btn")).click();
  }

  protected void goToUsers() {
    wd.findElement(By.cssSelector("a[name=users]")).click();
  }

  @AfterMethod
  public void teardown() {
    wd.quit();
  }
}
