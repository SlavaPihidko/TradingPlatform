package appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class NavigationHelper extends HelperBase {

  public NavigationHelper(WebDriver wd){
    super(wd);
  }

  public void usersPage() {
    wd.findElement(By.cssSelector("a[name=users]")).click();
  }
}
