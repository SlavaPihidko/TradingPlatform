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

  public int userIdMax;
  public int idNeo;
  public int personalFeeActive;
  public String baseAdminPage = "http://209.182.216.247/admin/";

  public   ApplicationManager app;// = new ApplicationManager(BrowserType.CHROME);
  public   ConnectionManager cm;// = new ConnectionManager();
  public   ApiManager am;// = new ApiManager();


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
    idNeo = cm.getSqlUserHelper().getIdNeo("SELECT id FROM coin4coin_db.assets where code='neo'");
    personalFeeActive = cm.getSqlUserHelper()
            .getPersonalFeeActiveFromDb(String.format("select personal_fee_active\n" +
                    "from coin4coin_db.users \n" +
                    "where id=%s", userIdMax));
  }

  @AfterSuite
  public void tearDown() {
    app.stop();
    cm.close();
  }

  public int getUserIdMax() {
    return userIdMax;
  }
  public int getIdNeo() {
    return idNeo;
  }

}


