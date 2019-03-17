package appmanager;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ApplicationManager {
  WebDriver wd;
  WebDriverWait wait;
  private SessionHelper sessionHelper;
  private UserHelper userHelper;
  private NavigationHelper navigationHelper;
  private String browser;
  private Properties properties;

  public ApplicationManager(String browser) {
    this.browser=browser;
    properties = new Properties();
  }

  public void init() throws IOException {

    String targetWeb = System.getProperty("targetWeb", "localWeb");
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", targetWeb))));

    if("".equals(properties.getProperty("selenium.server"))) {
      if (browser.equals(BrowserType.FIREFOX)) {
        wd = new FirefoxDriver();
        System.out.println("I am " + BrowserType.FIREFOX);
        System.out.println("I am from system property " + System.getProperty("browser"));
      } else if (browser.equals(BrowserType.CHROME)) {
        System.out.println("I am " + BrowserType.CHROME);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-fullscreen");
//      DesiredCapabilities capabilities = new DesiredCapabilities();
//      capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        wd = new ChromeDriver(options);
        System.out.println("Свойства браузера: \n" + ((ChromeDriver) wd).getCapabilities());
      } else if (browser.equals(BrowserType.IE)) {
        wd = new InternetExplorerDriver();
      }
    } else {
      DesiredCapabilities capabilities = new DesiredCapabilities();
      capabilities.setBrowserName(browser);
      capabilities.setPlatform(Platform.fromString(System.getProperty("platform")));
      //capabilities.setPlatform(Platform.fromString(System.getProperty("platform", "win7")));
      wd = new RemoteWebDriver(new URL(properties.getProperty("selenium.server")), capabilities);
    }

    wait = new WebDriverWait(wd, 20);

    wd.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
//    if(browser == BrowserType.CHROME) {
//      wd.manage().window().setSize(new Dimension(1360, 720)); // устанавливает ширину открывающегося окна
//    }
    wd.get(properties.getProperty("web.BaseUrl"));
    userHelper = new UserHelper(wd, wait);
    navigationHelper = new NavigationHelper(wd, wait);
    sessionHelper = new SessionHelper(wd,wait);
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

  public byte[] takeScreenshot() {
    return ((TakesScreenshot) wd).getScreenshotAs(OutputType.BYTES);
  }
}
