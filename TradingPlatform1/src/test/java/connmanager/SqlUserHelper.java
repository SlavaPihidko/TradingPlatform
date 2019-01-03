package connmanager;

import model.UserData;
import java.sql.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class SqlUserHelper extends SqlHelperBase {

  public SqlUserHelper(Connection con) {
    super(con);
  }

  public Set<UserData> getOneUserFromDb(String query) throws SQLException {

    Statement st = con.createStatement();
    ResultSet rs = st.executeQuery(query);

    Set<UserData> userOneFromDB = new HashSet<>();
    while (rs.next()) {
      UserData userData = new UserData()
              .withId(rs.getString("U.id"))
              .withFullName(rs.getString("fullName"))
              .withEmail(rs.getString("U.email"))
              .withLastLogin(rs.getString("U.last_login"))
              .withCreated(rs.getString("U.created_at"))
              .withKyc(rs.getString("kyc"))
              .withStatus(rs.getString("status"));

       userOneFromDB.add(userData);

      System.out.println();
    }
    for (UserData n : userOneFromDB) {
      System.out.println("User from DB equal : " + n);
    }

      rs.close();
      st.close();

      return userOneFromDB;
    }

  }
