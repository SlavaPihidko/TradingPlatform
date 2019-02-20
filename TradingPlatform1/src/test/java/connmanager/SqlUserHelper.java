package connmanager;

import model.UserData;
import model.UserLimits;

import java.sql.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

  public Set<UserData> getUserAccountInfoFromDb(String query) throws SQLException {
    Statement st = con.createStatement();
    ResultSet rs = st.executeQuery(query);

    Set<UserData> users = new HashSet<>();
    while (rs.next()){
      UserData user = new UserData()
              .withId(rs.getString("U.id"))
              .withEmail(rs.getString("U.email"))
              .withFullName(rs.getString("fullName"))
              .withMobileNumber(rs.getString("UD.phone"))
              .withAccountType(rs.getString("accounTypeName"));

      System.out.println("user from DB " + user);
      users.add(user);
    }
    rs.close();
    st.close();

    return users;
  }

  public void setIntValue(String query) throws SQLException {
    Statement st = con.createStatement();
    int rs = st.executeUpdate(query);
    System.out.println("rs :" + rs);

    st.close();
  }

  public int getPersonalFeeActiveFromDb(String query) throws SQLException {

    Statement st = con.createStatement();
    ResultSet rs = st.executeQuery(query);

    int personal_fee_active = 3;
    while (rs.next()) {
       personal_fee_active = rs.getInt("personal_fee_active");
      System.out.println("personal_fee_active : " + personal_fee_active);
    }

    rs.close();
    st.close();
    return personal_fee_active;
  }

  public int getMaxUserId(String query) throws SQLException {

    Statement st = con.createStatement();
    ResultSet rs = st.executeQuery(query);

    int userIdMax = 3;
    while (rs.next()) {
      userIdMax = rs.getInt("Max(id)");
      System.out.println("id : " + userIdMax);
    }

    rs.close();
    st.close();
    return userIdMax;
  }

  public int getIdNeo(String query) throws SQLException {

    Statement st = con.createStatement();
    ResultSet rs = st.executeQuery(query);

    int idNeo = 3;
    while (rs.next()) {
      idNeo = rs.getInt("id");
      System.out.println("id : " + idNeo);
    }

    rs.close();
    st.close();
    return idNeo;
  }

  public UserLimits getUserNeoLimitsFromDb(String query) throws SQLException {
    Statement st = con.createStatement();
    ResultSet rs = st.executeQuery(query);

    UserLimits userBtcLimit = null;
    while (rs.next()){
       userBtcLimit = new UserLimits()
              .withName(rs.getString("UA.name"))
              .withOrder_min(Double.parseDouble(rs.getString("UF.order_min")))
              .withExchange(Double.parseDouble(rs.getString("UF.exchange")))
              .withWithdraw_min(Double.parseDouble(rs.getString("UF.withdraw_min")))
              .withWithdraw_max(Double.parseDouble(rs.getString("UF.withdraw_max")));

      System.out.println("userBtcLimit from DB " + userBtcLimit);

    }
    rs.close();
    st.close();

    return userBtcLimit;
  }

  public List<String> getListNameAssetsFromDb(String query) throws SQLException {
    Statement st = con.createStatement();
    ResultSet rs = st.executeQuery(query);

    List<String> listNameAssets = new ArrayList<>() ;
    while (rs.next()){
      String nameAsset = rs.getString("name");
      listNameAssets.add(nameAsset);
    }

    System.out.println("listNameAssets " + listNameAssets);
    return listNameAssets;
  }

  public Set<UserLimits> getUserLimitsFromDbWithoutNeo(String query) throws SQLException {
    Statement st = con.createStatement();
    ResultSet rs = st.executeQuery(query);

    Set<UserLimits> userLimits = new HashSet<>();
    while (rs.next()){
      UserLimits userLimits1 = new UserLimits()
              .withName(rs.getString("UA.name"))
              .withExchange(Double.parseDouble(rs.getString("UF.exchange")))
              .withWithdraw_min(Double.parseDouble(rs.getString("UF.withdraw_min")))
              .withWithdraw_max(Double.parseDouble(rs.getString("UF.withdraw_max")));
      userLimits.add(userLimits1);
    }
    System.out.println("userLimits from DB: " + userLimits);
    return userLimits;
  }
}
