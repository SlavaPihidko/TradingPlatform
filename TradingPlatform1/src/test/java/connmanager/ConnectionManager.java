package connmanager;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {

  protected  SqlUserHelper sqlUserHelper ;

  private Connection con;
  private Session session;
  private  Properties propertiesForDb;


  public Connection getConnection() throws IOException {

    propertiesForDb = new Properties();
    String targetDb = System.getProperty("targetDb", "localDb");
    propertiesForDb.load(new FileReader(new File(String.format("src/test/resources/%s.properties", targetDb))));

    String host = propertiesForDb.getProperty("Db.host");
    int sshPort = 22;
    String sshPassword = propertiesForDb.getProperty("Db.sshPassword");
    String sqlIp = "127.0.0.1";
    int sqlPort = 3306;
    int lPort = 4321;
    var user = propertiesForDb.getProperty("Db.user");
    var password = propertiesForDb.getProperty("Db.password");


      JSch jSch = new JSch();
      try {
        this.session = jSch.getSession(user, host, sshPort);
        this.session.setPassword(sshPassword);
        this.session.setConfig("StrictHostKeyChecking", "no");
        System.out.println("Establishing Connection...");
        this.session.connect();
        int assigned_port = this.session.setPortForwardingL(lPort, sqlIp, sqlPort);
        System.out.println(sqlIp + ":" + assigned_port + " -> " + sqlIp + ":" + sqlPort);
      } catch (JSchException e) {
        e.printStackTrace();
      }

    var connectionString = String.format("jdbc:mysql://localhost:4321?autoReconnect=true&useSSL=false");

    try {
      Class.forName("com.mysql.jdbc.Driver");
      con = DriverManager.getConnection(connectionString, user, password);
// Создание обьектов класса помощников
      sqlUserHelper = new SqlUserHelper(con);
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
    catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

    return con;
  }

  //Подключение к БД без SSH
  /*public void connToDB() {

    String userName;
    String password;
    String dbURL;
    Connection conn;

    userName = "root";
    password = "qwerty";
    dbURL = "jdbc:mysql://146.71.78.211:3306?autoReconnect=true&useSSL=false";

    Set<UserData> userFromDB = null;


    try {
      conn = DriverManager.getConnection(dbURL, userName, password);

      Statement st = conn.createStatement();
      ResultSet rs = st.executeQuery("SELECT id FROM puredex.users where id=2;");

      System.out.println(rs);

      userFromDB = new HashSet<>();
      while (rs.next()) {
        UserData userData = new UserData(rs.getString("id"));
        userFromDB.add(userData);

        System.out.println();
      }
      for (UserData n : userFromDB) {
        System.out.println("User from DB equal : " + n);
      }


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
  } */

  public void close() {
    if (this.con != null) {
      try {
        this.con.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    if (this.session != null) {
      this.session.disconnect();
    }
  }

  public SqlUserHelper getSqlUserHelper() {
    return sqlUserHelper;
  }
}
