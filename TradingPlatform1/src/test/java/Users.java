import org.testng.annotations.Test;

public class Users extends TestBase {


  @Test
  public void checkUsers() throws InterruptedException {
    //wd.wait(10);
    goToUsers();

  }

}
