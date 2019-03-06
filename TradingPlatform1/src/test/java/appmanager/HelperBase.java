package appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class HelperBase {
  protected WebDriver wd;
  protected WebDriverWait wait;

  public HelperBase(WebDriver wd, WebDriverWait wait) {
    this.wd = wd;
    this.wait = wait;
  }
//public HelperBase(WebDriver wd) {
//  this.wd = wd;
//}
//
//  public HelperBase(WebDriverWait wait) {
//    this.wait = wait;
//  }

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

  public void isElementPresentTextToBe(By locator, String st) {
      wait.until(textToBe(locator,st));
  }

  public boolean isElementPresent(By locator) {
    wait.until(presenceOfElementLocated(locator));
    return true;
  }

  public WebElement isElementPresent2(By locator) {
    return wait.until(presenceOfElementLocated(locator));
  }

  public boolean notElementPresent(WebElement sp) {
    wait.until(stalenessOf(sp));
    return true;
  }
}
