package tests;

import apirequests.ApiManager;
import appmanager.ApplicationManager;
import connmanager.ConnectionManager;
import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import java.io.IOException;

public class TestBase  {


  public   ApplicationManager app;// = new ApplicationManager(BrowserType.CHROME);

  public   ConnectionManager cm;// = new ConnectionManager();

  public   ApiManager am;// = new ApiManager();

  public TestBase() {
    this.app = new ApplicationManager(BrowserType.CHROME);
    this.cm = new ConnectionManager();
    this.am = new ApiManager();
  }

  @BeforeSuite
  public void setUp() throws IOException {
    app.init();
    am.dealWithApi();
    cm.getConnection();
  }

  @AfterSuite
  public void tearDown() {
    app.stop();
    cm.close();
  }

}


