package tests;

import model.UserData;
import model.UserDataForApi;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Set;

public class UsersApi extends TestBase {

  @Test
  public void testListOfUsers() throws IOException {

    System.out.println(am.getApiUserHelper().getUsersFromApi());
  }
}
