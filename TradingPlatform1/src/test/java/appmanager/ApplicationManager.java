package appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.BrowserType;

import java.util.concurrent.TimeUnit;

public class ApplicationManager {
  WebDriver wd;
  private  SessionHelper sessionHelper;
  private UserHelper userHelper;
  private NavigationHelper navigationHelper;
  String browser;

  public ApplicationManager(String browser) {
    this.browser=browser;
  }

  public void init() {

    if (browser == BrowserType.FIREFOX) {
      wd = new FirefoxDriver();
    } else if (browser == BrowserType.CHROME) {
      wd = new ChromeDriver();
    }
    wd.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    wd.get("http://209.182.216.247/admin");
    userHelper = new UserHelper(wd);
    navigationHelper = new NavigationHelper(wd);
    sessionHelper = new SessionHelper(wd);
    //tel.0931513382@gmail.com
    sessionHelper.loginToAdminPanel("dsgf@gmail.com", "Coin4Coin12345");
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

  public SessionHelper getSessionHelper() {
    return sessionHelper;
  }
}
