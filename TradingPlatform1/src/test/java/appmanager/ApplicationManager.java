package appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ApplicationManager {
  WebDriver wd;
  //WebDriverWait wait;
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
      ChromeOptions options = new ChromeOptions();
      options.addArguments("start-fullscreen");
//      DesiredCapabilities caps = new DesiredCapabilities();
//      caps.setCapability(ChromeOptions.CAPABILITY, options);
      wd = new ChromeDriver(options);
      System.out.println( "Свойства браузера: \n" + ((ChromeDriver) wd).getCapabilities());
    } else if (browser == BrowserType.IE) {
      wd = new InternetExplorerDriver();
    }

    // wait = new WebDriverWait(wd, 10);

    wd.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
//    if(browser == BrowserType.CHROME) {
//      wd.manage().window().setSize(new Dimension(1360, 720)); // устанавливает ширину открывающегося окна
//    }
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

  public NavigationHelper press() {
    return navigationHelper;
  }

  public SessionHelper getSessionHelper() {
    return sessionHelper;
  }
}
