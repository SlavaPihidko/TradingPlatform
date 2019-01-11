package appmanager;

import model.UserAccount;
import model.UserData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class UserHelper extends HelperBase {

  public UserHelper(WebDriver wd) {
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
  public Set<UserData> getOneUserFromWeb(boolean dateWithDash) throws ParseException {
    Set<UserData> users = new HashSet<>();
    String userId = wd.findElement(By.cssSelector("tr.table_row > th:nth-child(1)")).getText();
    String userFullName = wd.findElement(By.cssSelector("tr.table_row > th:nth-child(2)")).getText();
    String userEmail = wd.findElement(By.cssSelector("tr.table_row > th:nth-child(3)")).getText();
    String userLastLogin = wd.findElement(By.cssSelector("tr.table_row > th:nth-child(4)")).getText();
    String created = wd.findElement(By.cssSelector("tr.table_row > th:nth-child(5)")).getText();
    String kyc = wd.findElement(By.cssSelector("tr.table_row > th:nth-child(6)")).getText();
    String status = wd.findElement(By.cssSelector("tr.table_row > th:nth-child(7)")).getText();

    if (dateWithDash == true) {
      //Приведение форматы даты к такой что в БД для userLastLogin
      SimpleDateFormat input = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
      Date dateValue = input.parse(userLastLogin);
      SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
      String userLastLoginFormatted = output.format(dateValue);

      //Приведение форматы даты к такой что в БД для created
      Date dateValue2 = input.parse(created);
      String createdFormatted = output.format(dateValue2);
      UserData user = new UserData()
                          .withId(userId)
                          .withFullName(userFullName)
                          .withEmail(userEmail)
                          .withLastLogin(userLastLoginFormatted)
                          .withCreated(createdFormatted)
                          .withKyc(kyc)
                          .withStatus(status);
      System.out.println("User from WEB equal : " + user);
      users.add(user);
    }
else {
      UserData user = new UserData()
                          .withId(userId)
                          .withFullName(userFullName)
                          .withEmail(userEmail)
                          .withLastLogin(userLastLogin)
                          .withCreated(created)
                          .withKyc(kyc)
                          .withStatus(status);
      System.out.println("User from WEB equal : " + user);
      users.add(user);
    }
    return users;
  }

  public Set<UserData> getUsersFromWeb() throws InterruptedException {
    Set<UserData> users = new HashSet<>();
    String baseLocatorUser = "table_row";
    List<WebElement> elements = wd.findElements(By.className(baseLocatorUser));

    for (WebElement element : elements) {

        String userId = element.findElement(By.cssSelector("th:nth-child(1)")).getText();
        String userFullName = element.findElement(By.cssSelector("th:nth-child(2)")).getText();

        String userEmail = element.findElement(By.cssSelector("th:nth-child(3)")).getText();
        String userLastLogin = element.findElement(By.cssSelector("th:nth-child(4)")).getText();
        String created = element.findElement(By.cssSelector("th:nth-child(5)")).getText();
        String kyc = element.findElement(By.cssSelector("th:nth-child(6)")).getText();
        String status = element.findElement(By.cssSelector("th:nth-child(7)")).getText();

        users.add(new UserData()
                      .withId(userId)
                      .withFullName(userFullName)
                      .withEmail(userEmail)
                      .withLastLogin(userLastLogin)
                      .withCreated(created)
                      .withKyc(kyc)
                      .withStatus(status)
        );
    }
    /*
    // последнее число пагинации
    String lastPageOfPagination = wd.findElement(By.cssSelector("ul.pagination li:nth-last-child(2)")).getText();
    int lp = Integer.parseInt(lastPageOfPagination); //приведение типов
    System.out.println("last page of User page " +lastPageOfPagination);
    //Пагинация. Ходим по страницам.
    if(lp > 1 ) {
      for (int i = 3; i <= 6; i++) {
        click(By.cssSelector("ul.pagination li:nth-child(" + i + ")"));
        System.out.println("Текущая страница " + wd.findElement(By.cssSelector("ul.pagination li.active")).getText());
        Thread.sleep(2000);
      }
    }
    if(lp > 7) {
      for (int i = 0; i <=  lp - 6; i++) {
        click(By.cssSelector("ul.pagination li:nth-child(6)"));
      System.out.println(" второй цикл Текущая страница " + wd.findElement(By.cssSelector("ul.pagination li.active")).getText());
      Thread.sleep(2000);}
    }
    */

    return users;
  }

  public Set<UserData> getUserAccountInfoFromWeb() {
    Set<UserData> users = new HashSet<>();
    String userId = wd.findElement(By.cssSelector("div.information > p.user_info:nth-child(1)")).getText();
    String accountType = wd.findElement(By.cssSelector("div.information > p.user_info:nth-child(2)")).getText();
    String fullName = wd.findElement(By.cssSelector("div.information > p.user_info:nth-child(3)")).getText();
    String mobileNumber = wd.findElement(By.cssSelector("div.information > p.user_info:nth-child(4)")).getText();
    String email = wd.findElement(By.cssSelector("div.information > p.user_info:nth-child(5)")).getText();

    //Режим строки и удаляем по бокам пробелы
    String [] id = userId.split(":");
    String subUserId = id[1].trim();
    String [] name = fullName.split(":");
    String subFullName = name[1].trim();
    String [] mail = email.split(":");
    String subEmail = mail[1].trim();
    String [] type = accountType.split(":");
    String subAccountType = type[1].trim();
    String [] mobile = mobileNumber.split(":");
    String subMobileNumber = mobile[1].trim();


    UserData user = new UserData()
            .withId(subUserId)
            .withFullName(subFullName)
            .withEmail(subEmail)
            .withAccountType(subAccountType)
            .withMobileNumber(subMobileNumber);

    users.add(user);
    return users;
  }

  public Set<UserAccount> getUserAccountFromWeb() {
    Set<UserAccount> userAccounts = new HashSet<>();
    String verificationStatus = wd.findElement(By.cssSelector("tr.table_row:nth-child(1) > .text-left")).getText();
    String first_name = wd.findElement(By.cssSelector("tr.table_row:nth-child(2) > .text-left")).getText();
    String last_name = wd.findElement(By.cssSelector("tr.table_row:nth-child(3) > .text-left")).getText();
    String dob = wd.findElement(By.cssSelector("tr.table_row:nth-child(4) > .text-left")).getText();
    String country = wd.findElement(By.cssSelector("tr.table_row:nth-child(5) > .text-left")).getText();
    String state = wd.findElement(By.cssSelector("tr.table_row:nth-child(6) > .text-left")).getText();
    String street = wd.findElement(By.cssSelector("tr.table_row:nth-child(7) > .text-left")).getText();
    String post_code = wd.findElement(By.cssSelector("tr.table_row:nth-child(8) > .text-left")).getText();
    String facebook_link = wd.findElement(By.cssSelector("tr.table_row:nth-child(9) > .text-left")).getText();

    UserAccount userAccount = new UserAccount()
            .withtVerificationStatus(verificationStatus)
            .withFirst_name(first_name)
            .withLast_name(last_name)
            .withDob(dob)
            .withCountry(country)
            .withState(state)
            .withStreet(street)
            .withPost_code(post_code)
            .withFacebook_link(facebook_link);

    userAccounts.add(userAccount);
    return userAccounts;
  }
}
