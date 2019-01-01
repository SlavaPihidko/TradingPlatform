package tests;

import apirequests.ApiManager;
import appmanager.ApplicationManager;
import connmanager.ConnectionManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class TestBase  {

  protected final ApplicationManager app = new ApplicationManager();

  protected final ConnectionManager cm = new ConnectionManager();

  protected final ApiManager am = new ApiManager();

  @BeforeMethod
  public void setUp() throws Exception {
    app.init();
    am.dealWithApi();
  }

  @AfterMethod
  public void teardown() {
    app.stop();
  }

}


