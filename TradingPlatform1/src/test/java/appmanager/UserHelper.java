package appmanager;

import model.UserData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.ArrayList;
import java.util.List;

public class UserHelper extends HelperBase {

  public UserHelper(FirefoxDriver wd) {
    super(wd);

  }


  /*public List<UserData> getUsersList() {
    List<UserData> users = new ArrayList<UserData>();
    List<WebElement> elements = wd.findElements(By.className("table_row"));
    for (WebElement element : elements) {
      String id = element.getText();
      UserData user = new UserData(id);
    }
    return users;
  }*/
  public UserData getOneUserFromWeb() {
    String userId = wd.findElement(By.cssSelector("tr.table_row > th:nth-child(1)")).getText();
    String userFirstName = wd.findElement(By.cssSelector("tr.table_row > th:nth-child(2)")).getText();
    String userLastName = wd.findElement(By.cssSelector("tr.table_row > th:nth-child(2)")).getText();
    String userEmail = wd.findElement(By.cssSelector("tr.table_row > th:nth-child(3)")).getText();
    String userLastLogin = wd.findElement(By.cssSelector("tr.table_row > th:nth-child(4)")).getText();
    String created = wd.findElement(By.cssSelector("tr.table_row > th:nth-child(5)")).getText();
    String kyc = wd.findElement(By.cssSelector("tr.table_row > th:nth-child(6)")).getText();
    String status = wd.findElement(By.cssSelector("tr.table_row > th:nth-child(7)")).getText();

    UserData user = new UserData(userId, userFirstName, userLastName, userEmail, userLastLogin, created, kyc, status);
    return user;
  }

  public List<UserData> getUsersFromWeb() {
    List<UserData> users = new ArrayList<UserData>();
    List<WebElement> elements = wd.findElements(By.className("table_row"));

    for (WebElement element : elements) {

      String userId = wd.findElement(By.cssSelector("tr.table_row > th:nth-child(1)")).getText();
      String userFirstName = wd.findElement(By.cssSelector("tr.table_row > th:nth-child(2)")).getText();
      String userLastName = wd.findElement(By.cssSelector("tr.table_row > th:nth-child(2)")).getText();
      String userEmail = wd.findElement(By.cssSelector("tr.table_row > th:nth-child(3)")).getText();
      String userLastLogin = wd.findElement(By.cssSelector("tr.table_row > th:nth-child(4)")).getText();
      String created = wd.findElement(By.cssSelector("tr.table_row > th:nth-child(5)")).getText();
      String kyc = wd.findElement(By.cssSelector("tr.table_row > th:nth-child(6)")).getText();
      String status = wd.findElement(By.cssSelector("tr.table_row > th:nth-child(7)")).getText();

      users.add(new UserData(userId, userFirstName, userLastName, userEmail, userLastLogin, created, kyc, status));
    }
    return users;
  }

}
