package tests;

import model.UserData;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class Users extends TestBase {


  @Test
  public void checkUsers() throws InterruptedException {

    app.getNavigationHelper().goToUsers();
    Thread.sleep(10000);
    UserData oneUser = app.getUserHelper().getOneUserFromWeb();
    System.out.println(
                    " ID of first user: " + oneUser.getId() + "\n" +
                    " FirstName of first user: " + oneUser.getFirstName() + "\n" +
                    " LastName of first user: " + oneUser.getLastName() + "\n" +
                    " Email of first user: " +oneUser.getEmail() + "\n" +
                    " LastLogin of first user: " + oneUser.getLastLogin() + "\n" +
                    " Created of first user: " + oneUser.getCreated() + "\n" +
                    " KYC of first user: " + oneUser.getKyc() + "\n" +
                    " Status of first user: " + oneUser.getStatus() + "\n" );

    Set<UserData> users = app.getUserHelper().getUsersFromWeb();
    System.out.println(users);

  }

 @Test
  public void checkConnToDB() throws SQLException {

   Statement st = cm.getConnection().createStatement();
   makeDbQuery(st,"SELECT id FROM coin4coin_db.users where id=262");
   st.close();
   //cm.getConnection().close();
  }

  private void makeDbQuery(Statement st, String query ) throws SQLException {
    ResultSet rs = st.executeQuery(query);

    Set<UserData> userFromDB = null;
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
  }
}
