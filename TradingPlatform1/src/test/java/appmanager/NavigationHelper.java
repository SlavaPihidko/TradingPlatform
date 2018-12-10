package appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

public class NavigationHelper {
  private FirefoxDriver wd;

  public NavigationHelper(FirefoxDriver wd){
    this.wd=wd;
  }

  public void goToUsers() {
    wd.findElement(By.cssSelector("a[name=users]")).click();
  }
}
