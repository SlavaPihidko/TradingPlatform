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

  // Запуск Браузера если true
  public void setUp(boolean withWeb)  {
    if(withWeb == true)
    {
      app.init();
    }
    am.dealWithApi();
  }
  // Киллинг Браузера если true
  public void tearDown(boolean withWeb) {
    if(withWeb == true)
    {
    app.stop();
    }
    cm.close();
  }

}


