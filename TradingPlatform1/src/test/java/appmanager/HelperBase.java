package appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class HelperBase {
  protected WebDriver wd;

  public HelperBase(WebDriver wd) {
    this.wd = wd;
  }

  protected void click(By locator) {
    wd.findElement(locator).click();
  }

  public void type(String text, By locator) {
    click(locator);
    wd.findElement(locator).clear();
    wd.findElement(locator).sendKeys(text);
  }

//  protected void typeDouble(Double d, By locator) {
//    click(locator);
//    wd.findElement(locator).clear();
//    wd.findElement(locator).sendKeys(d);
//  }

  public String text(By locator) {
return wd.findElement(locator).getText();
  }

  public boolean elementPresent(By locator) {
    try {
      wd.findElement(locator);
      return true;
    } catch (NoSuchElementException ex) {
      return false;
    }
  }

  public void getBaseAdminPage(String url) {
    wd.get(url);
  }
}
