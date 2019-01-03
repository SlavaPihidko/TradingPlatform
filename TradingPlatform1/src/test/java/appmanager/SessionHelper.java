package appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SessionHelper extends HelperBase {

  public SessionHelper(WebDriver wd){
    super(wd);
  }

  public void loginToAdminPanel(String adminEmail, String adminPassword) {
    type(adminEmail, By.name("email"));
    type(adminPassword, By.name("password"));
    click(By.className("blue_btn"));
  }

}
