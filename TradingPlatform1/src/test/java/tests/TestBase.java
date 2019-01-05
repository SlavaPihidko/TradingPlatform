package tests;

import apirequests.ApiManager;
import appmanager.ApplicationManager;
import connmanager.ConnectionManager;
import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import java.io.IOException;

public class TestBase  {

  protected static final ApplicationManager app = new ApplicationManager(BrowserType.CHROME);

  protected static final ConnectionManager cm = new ConnectionManager();

  protected static final ApiManager am = new ApiManager();

  @BeforeSuite
  public void setUp() throws IOException {
    app.init();
    am.dealWithApi();
  }

  @AfterSuite
  public void tearDown() {
    app.stop();
    cm.close();
  }

}


