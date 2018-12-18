package tests;

import model.UserData;
import org.testng.annotations.Test;

import java.util.List;
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
  public void checkConnToDB(){
   cam.getConnection();
   cam.close();
  }


}
