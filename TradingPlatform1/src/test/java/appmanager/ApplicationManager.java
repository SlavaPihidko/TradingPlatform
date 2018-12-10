package appmanager;

import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class ApplicationManager {
  FirefoxDriver wd;
  private  SessionHelper sessionHelper;
  private UserHelper userHelper;
  private NavigationHelper navigationHelper;

  public void init() {
    wd = new FirefoxDriver();
    wd.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    wd.get("http://209.182.216.247/admin");
    userHelper = new UserHelper(wd);
    navigationHelper = new NavigationHelper(wd);
    sessionHelper = new SessionHelper(wd);
    sessionHelper.loginToAdminPanel("tel.0931513382@gmail.com", "Coin4Coin12345");
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
