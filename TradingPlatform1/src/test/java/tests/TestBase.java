package tests;

import appmanager.ApplicationManager;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import model.UserData;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

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


