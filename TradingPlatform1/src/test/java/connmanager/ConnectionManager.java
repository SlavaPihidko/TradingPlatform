package connmanager;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import model.UserData;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ConnectionManager {

  protected  SqlUserHelper sqlUserHelper ;

  private Connection con;
  private Session session;

  public Connection getConnection() {

    boolean useSSH = true;
    String host = "209.182.216.247";
    int sshPort = 22;
    String sshPasword = "pxKL37ZA=n3A";
    String sqlIp = "127.0.0.1";
    int sqlPort = 3306;
    int lPort = 4321;
    var user = "root";
    var password = "qwerty";



      JSch jSch = new JSch();
      try {
        this.session = jSch.getSession(user, host, sshPort);
        this.session.setPassword(sshPasword);
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
