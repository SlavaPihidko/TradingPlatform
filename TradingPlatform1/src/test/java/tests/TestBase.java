package tests;

import appmanager.ApplicationManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.sql.*;

public class TestBase {

  protected final ApplicationManager app = new ApplicationManager();

  @BeforeMethod
  public void setUp() throws Exception {
    app.init();
  }

  @AfterMethod
  public void teardown() {
    app.stop();
  }


  public void connToDB() {

    String userName;
    String password;
    String dbURL;
    Connection conn;

    userName = "root";
    password = "qwerty";
    dbURL = "jdbc:mysql://146.71.78.211:3306?autoReconnect=true&useSSL=false";

    try {
      conn = DriverManager.getConnection(dbURL, userName, password);

      Statement st = conn.createStatement();
      ResultSet rs = st.executeQuery("SELECT id FROM puredex.users where id=10;");

      System.out.println(rs);


      rs.close();
      st.close();
      conn.close();
      // Do something with the Connection
    } catch (SQLException ex) {

      // handle any errors
      System.out.println("SQLException: " + ex.getMessage());
      System.out.println("SQLState: " + ex.getSQLState());
      System.out.println("VendorError: " + ex.getErrorCode());
    }
  }

}
