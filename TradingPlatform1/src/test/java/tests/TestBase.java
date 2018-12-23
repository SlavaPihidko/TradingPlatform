package tests;

import appmanager.ApplicationManager;
import connmanager.ConnectionManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class TestBase extends ConnectionManager {

  protected final ApplicationManager app = new ApplicationManager();

  protected final ConnectionManager cm = new ConnectionManager();

  @BeforeMethod
  public void setUp() throws Exception {
    app.init();
  }

  @AfterMethod
  public void teardown() {
    app.stop();
  }

}


