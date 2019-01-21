package appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class NavigationHelper extends HelperBase {

  public NavigationHelper(WebDriver wd){
    super(wd);
  }

  public void usersPage() {
    wd.findElement(By.cssSelector("a[name=users]")).click();
  }

  // выбираем самого верхнего юзера с списка и переходим в его аккаунт
  public void userInfo() {
    String userId = wd.findElement(By.cssSelector("tr.table_row > th:nth-child(1)")).getText();
    wd.findElement(By.cssSelector(String.format("a[href='/users/%s']", userId))).click();
  }

  public void approveButton() {
    wd.findElement(By.cssSelector("div.btns > button.green_btn")).click();
  }

  public void rejectButton() {
    wd.findElement(By.cssSelector("div.btns > button.red_btn")).click();
  }

  public void userBalances() {
    wd.findElement(By.cssSelector("div.buttons a[data-name='balance']")).click();
  }

  public void userTransactions() {
    wd.findElement(By.cssSelector("div.buttons a[data-name='transactions']")).click();
  }
}
