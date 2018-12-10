package appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class ApplicationManager {
  private UserHelper userHelper;
  private NavigationHelper navigationHelper;
  FirefoxDriver wd;

  public void init() {
    wd = new FirefoxDriver();
    wd.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    wd.get("http://209.182.216.247/admin");
    userHelper = new UserHelper(wd);
    navigationHelper = new NavigationHelper(wd);
    loginToAdminPanel("tel.0931513382@gmail.com", "Coin4Coin12345");
  }

  public void loginToAdminPanel(String adminEmail, String adminPassword) {
    wd.findElement(By.name("email")).click();
    wd.findElement(By.name("email")).clear();
    wd.findElement(By.name("email")).sendKeys(adminEmail);
    wd.findElement(By.name("password")).click();
    wd.findElement(By.name("password")).clear();
    wd.findElement(By.name("password")).sendKeys(adminPassword);
    wd.findElement(By.className("blue_btn")).click();
  }

  public void stop() {
    wd.quit();
  }

  public UserHelper getUserHelper() {
    return userHelper;
  }

  public NavigationHelper getNavigationHelper() {
    return navigationHelper;
  }
}
