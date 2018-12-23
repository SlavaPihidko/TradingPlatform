package tests;

import appmanager.ApplicationManager;
import connmanager.ConnectionManager;

public class TestBase extends ConnectionManager {

  protected final ApplicationManager app = new ApplicationManager();

  protected final ConnectionManager cm = new ConnectionManager();

 // @BeforeMethod
  public void setUp() throws Exception {
    app.init();
  }

 // @AfterMethod
  public void teardown() {
    app.stop();
  }

}


