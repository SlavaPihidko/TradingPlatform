package appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SessionHelper {

  private FirefoxDriver wd;
  public SessionHelper(FirefoxDriver wd){
    this.wd = wd;
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
}
