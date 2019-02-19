package tests;

import apirequests.ApiManager;
import appmanager.ApplicationManager;
import connmanager.ConnectionManager;
import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import java.io.IOException;
import java.sql.SQLException;

public class TestBase  {


  public   ApplicationManager app;// = new ApplicationManager(BrowserType.CHROME);

  public   ConnectionManager cm;// = new ConnectionManager();

  public   ApiManager am;// = new ApiManager();

  public int getUserIdMax() {
    return userIdMax;
  }

  public int userIdMax;

  public TestBase() {
    this.app = new ApplicationManager(BrowserType.CHROME);
    this.cm = new ConnectionManager();
    this.am = new ApiManager();
  }

  @BeforeSuite
  public void setUp() throws IOException, SQLException {
    app.init();
    am.dealWithApi();
    cm.getConnection();
    userIdMax = cm.getSqlUserHelper().getMaxUserId("select Max(id) from coin4coin_db.users");
  }

  @AfterSuite
  public void tearDown() {
    app.stop();
    cm.close();
  }

}


