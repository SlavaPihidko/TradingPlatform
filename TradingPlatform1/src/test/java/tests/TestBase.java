package tests;

import apirequests.ApiManager;
import appmanager.ApplicationManager;
import connmanager.ConnectionManager;
import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;

public class TestBase  {
//BrowserType.CHROME  -Dbrowser=chrome
  protected static final ApplicationManager app = new ApplicationManager(BrowserType.CHROME);

  protected static final ConnectionManager cm = new ConnectionManager();

  protected static final ApiManager am = new ApiManager();

  // Запуск Браузера если true
  @BeforeSuite
  public void setUp() throws IOException {
    app.init();
    am.dealWithApi();
  }
  // Киллинг Браузера если true
  @AfterSuite
  public void tearDown() {
    app.stop();
    cm.close();
  }

}


