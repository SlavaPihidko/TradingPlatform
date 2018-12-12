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
  public UserData getUserList(){
    //locator for le po tr.table_row > th:nth-child(2)
    String userId = wd.findElement(By.cssSelector("tr.table_row > th:nth-child(1)")).getText();
    UserData user = new UserData(userId);
    return user;
  }

}
