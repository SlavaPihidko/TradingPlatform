package appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SessionHelper {

  private FirefoxDriver wd;
  public SessionHelper(FirefoxDriver wd){
    this.wd = wd;
  }

  public void loginToAdminPanel(String adminEmail, String adminPassword) {
    type(adminEmail, By.name("email"));
    type(adminPassword, By.name("password"));
    click(By.className("blue_btn"));
  }

  private void click(By locator) {
    wd.findElement(locator).click();
  }

  private void type(String text, By locator) {
    click(locator);
    wd.findElement(locator).clear();
    wd.findElement(locator).sendKeys(text);
  }
}
