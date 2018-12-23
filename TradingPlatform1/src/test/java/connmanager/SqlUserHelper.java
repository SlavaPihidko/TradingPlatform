package connmanager;

import model.UserData;

import java.sql.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class SqlUserHelper   {

private Connection con;

  public SqlUserHelper(Connection con) {
    this.con = con;
  }

  public void makeDbQueryForUsers( String query ) throws SQLException {
    Statement st = con.createStatement();

    ResultSet rs = st.executeQuery(query);
    Set<UserData> userFromDB = null;
    userFromDB = new HashSet<>();
    while (rs.next()) {
      UserData userData = new UserData(
              rs.getString("U.id"),
              rs.getString("UD.first_name"),
              rs.getString("UD.last_name"),
              rs.getString("U.email"),
              rs.getString("U.last_login"),
              rs.getString("U.created_at"),
              rs.getString("verifyStatus"),
              rs.getString("status"));
      userFromDB.add(userData);

      System.out.println();
    }
    for (UserData n : userFromDB) {
      System.out.println("User from DB equal : " + n);
    }

    rs.close();
    st.close();
  }
}
