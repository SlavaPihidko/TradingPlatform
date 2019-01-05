package appmanager;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.BrowserType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ApplicationManager {
  WebDriver wd;
  private  SessionHelper sessionHelper;
  private UserHelper userHelper;
  private NavigationHelper navigationHelper;
  private String browser;
  private  Properties properties;

  public ApplicationManager(String browser) {
    this.browser=browser;
    properties = new Properties();
  }

  public void init() throws IOException {

    String targetWeb = System.getProperty("targetWeb", "localWeb");
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", targetWeb))));

    if (browser == BrowserType.FIREFOX) {
      wd = new FirefoxDriver();
    } else if (browser == BrowserType.CHROME) {
      wd = new ChromeDriver();
    }
    wd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    if(browser == BrowserType.CHROME) {
      wd.manage().window().setSize(new Dimension(1360, 720)); // устанавливает ширину открывающегося окна
    }
    wd.get(properties.getProperty("web.BaseUrl"));
    userHelper = new UserHelper(wd);
    navigationHelper = new NavigationHelper(wd);
    sessionHelper = new SessionHelper(wd);
    //tel.0931513382@gmail.com
    sessionHelper.loginToAdminPanel(properties.getProperty("web.login"), properties.getProperty("web.password"));
  }

  public void stop() {
    wd.quit();
  }

  public UserHelper getUserHelper() {
    return userHelper;
  }

  public NavigationHelper goTo() {
    return navigationHelper;
  }

  public SessionHelper getSessionHelper() {
    return sessionHelper;
  }
}
