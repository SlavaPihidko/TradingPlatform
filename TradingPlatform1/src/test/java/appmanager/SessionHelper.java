package appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SessionHelper extends HelperBase {

  public SessionHelper(WebDriver wd, WebDriverWait wait){
    super(wd,wait);
  }



  public void loginToAdminPanel(String adminEmail, String adminPassword) {
    type(adminEmail, By.name("email"));
    type(adminPassword, By.name("password"));
    click(By.className("blue_btn"));
  }


}
