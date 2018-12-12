package tests;

import model.UserData;
import org.testng.annotations.Test;

import java.util.List;

public class Users extends TestBase {


  @Test
  public void checkUsers() throws InterruptedException {
    //wd.wait(10);
    app.getNavigationHelper().goToUsers();
    Thread.sleep(10000);
    UserData oneUser = app.getUserHelper().getUserList();
    System.out.println("ID of first user: " + oneUser.getId());
  }

}
