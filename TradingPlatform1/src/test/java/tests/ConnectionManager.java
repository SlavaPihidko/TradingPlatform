package tests;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

  private Connection con = null;
  private Session session = null;

  public Connection getConnection() {
    Connection con = null;



    boolean useSSH = true;
    int sshPort = 22;
    String sqlIp = "127.0.0.1";
    int sqlPort = 3306;


    if (useSSH) {
      JSch jSch = new JSch();
      try {
        this.session = jSch.getSession("root",
                "209.182.216.247",
                Integer.valueOf(22));
        this.session.setPassword("pxKL37ZA=n3A");
        this.session.setConfig("StrictHostKeyChecking", "no");
        System.out.println("Establishing Connection...");
        this.session.connect();
        int assinged_port = this.session.setPortForwardingL(sshPort, sqlIp, sqlPort);
        System.out.println(sqlIp+":"+assinged_port+" -> "+sqlIp+":"+sqlPort);
      } catch (JSchException e) {
        e.printStackTrace();
      }
    }

    var connectionString = String.format("jdbc:mysql://127.0.0.1:3036/coin4coin_db?autoReconnect=true&useSSL=false",
            sqlIp, useSSH ? sshPort : sqlPort);

    var user = "root";
    var password = "qwerty";

    try {
      Class.forName("com.mysql.jdbc.Driver");
      con = DriverManager.getConnection(connectionString, user, password);
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

    return con;
  }

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
}
