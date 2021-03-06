package appmanager;

import model.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class UserHelper extends HelperBase {

  public UserHelper(WebDriver wd, WebDriverWait wait) {
    super(wd, wait);

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
    System.out.println(users);
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

  public Set<UserAccount> getUserAccountFromWeb() throws ParseException {
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

    SimpleDateFormat input = new SimpleDateFormat("dd/MM/yyyy");
    Date dateValue = input.parse(dob);
    SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
    String dobFormatted = output.format(dateValue);

    UserAccount userAccount = new UserAccount()
            .withtVerificationStatus(verificationStatus)
            .withFirst_name(first_name)
            .withLast_name(last_name)
            .withDob(dobFormatted)
            .withCountry(country)
            .withState(state)
            .withStreet(street)
            .withPost_code(post_code)
            .withFacebook_link(facebook_link);

    userAccounts.add(userAccount);
    return userAccounts;
  }

  public UserAccount getStatusAtUserAccountFromWeb() {
    String verificationStatus = wd.findElement(By.cssSelector("tr.table_row:nth-child(1) > .text-left")).getText();
    UserAccount status = new UserAccount().withtVerificationStatus(verificationStatus);
    System.out.println("status from Web: " + status);
    return status;
  }

  public Set<UserAssets> getUserBalancesFromWeb() {
    Set<UserAssets> usersAssets = new HashSet<>();
    String baseLocatorUser = "table_row";
    List<WebElement> elements = wd.findElements(By.className(baseLocatorUser));
    System.out.println("elements.size() " + elements.size());

    for(WebElement element:elements) {


      String code = element.findElement(By.cssSelector("th:nth-child(1)")).getText().toLowerCase();
      double balance = Double.parseDouble(element.findElement(By.cssSelector("th:nth-child(2)")).getText());

      //System.out.println("code " + code + "   balance " + balance);

      UserAssets userAssets = new UserAssets().withBalance(balance).withCode(code);
      usersAssets.add(userAssets);
    }
    System.out.println("usersAssets from WEB " + usersAssets);
    return usersAssets;

  }

  public Set<UserTxes> getUserTxesFromWeb() {
    Set<UserTxes> userTxes = new HashSet<>();
    String baseLocatorTxes = "table_row";
    List<WebElement> elements =  wd.findElements(By.className(baseLocatorTxes));

    System.out.println("elements.size() " + elements.size());

    for (WebElement element: elements) {
      int id = Integer.parseInt(element.findElement(By.cssSelector("th:nth-child(1)")).getText());
      String transaction_type = element.findElement(By.cssSelector("th:nth-child(2)")).getText();
      int user_id = Integer.parseInt(element.findElement(By.cssSelector("th:nth-child(3)")).getText());
      String code = element.findElement(By.cssSelector("th:nth-child(4)")).getText().toLowerCase();
      double amount = Double.parseDouble(element.findElement(By.cssSelector("th:nth-child(5)")).getText());
      String status = element.findElement(By.cssSelector("th:nth-child(6)")).getText().toLowerCase();
      String created_at = element.findElement(By.cssSelector("th:nth-child(7)")).getText();

//      System.out.println(" id " + id + " transaction_type " + transaction_type + " user_id " + user_id
//      + " code " + code + " amount " + amount + " status " + status + " created_at " + created_at);

      UserTxes userTxes1 = new UserTxes()
              .withId(id).withTransaction_type(transaction_type).withUser_id(user_id)
              .withCode(code).withAmount(amount).withStatus(status).withCreated_at(created_at);

    userTxes.add(userTxes1);
    }
    System.out.println("userTxes from WEB: " + userTxes);
    return userTxes;
  }

  public Set<UserOrders> getUserOrdersFromWeb() {
    Set<UserOrders> userOrders = new HashSet<>();
    String baseLocator = "table_row";
    List<WebElement> elements = wd.findElements(By.className(baseLocator));

    System.out.println("elements.size() " + elements.size());

    for (WebElement element: elements) {
      int id = Integer.parseInt(element.findElement(By.cssSelector("th:nth-child(1)")).getText());
      String pair = element.findElement(By.cssSelector("th:nth-child(2)")).getText();
      String type = element.findElement(By.cssSelector("th:nth-child(3)")).getText();
      int user_id = Integer.parseInt(element.findElement(By.cssSelector("th:nth-child(4)")).getText());
      double quantity = Double.parseDouble(element.findElement(By.cssSelector("th:nth-child(5)")).getText());
      String created_at = element.findElement(By.cssSelector("th:nth-child(7)")).getText();
      String status = element.findElement(By.cssSelector("th:nth-child(8)")).getText();

      UserOrders userOrders1 = new UserOrders()
              .withId(id)
              .withPair(pair)
              .withType(type)
              .withUser_id(user_id)
              .withQuantity(quantity)
              .withCreated_at(created_at)
              .withStatus(status);

      userOrders.add(userOrders1);
    }
    System.out.println("userOrders from WEB " + userOrders );

    return userOrders;
  }

  public void turnOnOffToggleAtUserLimits() {
    wd.findElement(By.cssSelector("button.btn")).click();
  }

  public Set<UserLimits> getUserLimitsWithoutNeoFromWeb() {
    Set<UserLimits> userLimits = new HashSet<>();
    String baseLocator = "table_row";
    List<WebElement> elements = wd.findElements(By.className(baseLocator));

    System.out.println("elements.size() " + elements.size());

    for(WebElement element: elements) {
      // делаем обьект без НЕО
      if(!element.findElement(By.cssSelector("th:nth-child(1)")).getText().equals("Neo")) {
        String name = element.findElement(By.cssSelector("th:nth-child(1)")).getText();
        //System.out.println("code :" + code);
        double order_min = Double.parseDouble(element.findElement(By.cssSelector("input[name='order_min']")).getAttribute("placeholder"));
        //System.out.println("order_min :" + order_min);
        double exchange = Double.parseDouble(element.findElement(By.cssSelector("input[name='exchange']")).getAttribute("placeholder"));
        double withdraw_min = Double.parseDouble(element.findElement(By.cssSelector("input[name='withdraw_min']")).getAttribute("placeholder"));
        double withdraw_max = Double.parseDouble(element.findElement(By.cssSelector("input[name='withdraw_max']")).getAttribute("placeholder"));

        UserLimits userLimits1 = new UserLimits()
                .withName(name)
                .withOrder_min(order_min)
                .withExchange(exchange)
                .withWithdraw_min(withdraw_min)
                .withWithdraw_max(withdraw_max);

        userLimits.add(userLimits1);
      }
    }
    System.out.println("userLimits from WEB :" + userLimits);
    return userLimits;
  }

  public UserLimits getUserNeoLimitsFromWeb() {
    UserLimits userBtcLimits = null;
    String baseLocator = "table_row";
    List<WebElement> elements = wd.findElements(By.className(baseLocator));
    for(WebElement element: elements) {
      if ("Neo".equals(element.findElement(By.cssSelector("th:nth-child(1)")).getText())) {

        String name = element.findElement(By.cssSelector("th:nth-child(1)")).getText();
        //System.out.println("code :" + code);
        double order_min = Double.parseDouble(element.findElement(By.cssSelector("input[name='order_min']")).getAttribute("placeholder"));
        //System.out.println("order_min :" + order_min);
        double exchange = Double.parseDouble(element.findElement(By.cssSelector("input[name='exchange']")).getAttribute("placeholder"));
        double withdraw_min = Double.parseDouble(element.findElement(By.cssSelector("input[name='withdraw_min']")).getAttribute("placeholder"));
        double withdraw_max = Double.parseDouble(element.findElement(By.cssSelector("input[name='withdraw_max']")).getAttribute("placeholder"));

        userBtcLimits = new UserLimits()
                .withName(name)
                .withOrder_min(order_min)
                .withExchange(exchange)
                .withWithdraw_min(withdraw_min)
                .withWithdraw_max(withdraw_max);

      }
    }
    System.out.println("userLimits from WEB :" + userBtcLimits);
    return userBtcLimits;
  }

  public Set<UserLimits> setUserLimits(String orderMin, String exchange, String withdrawMin, String withdrawMax) throws InterruptedException {
    Set<UserLimits> userLimits = new HashSet<>();

//    UserLimits userLimits1 = new UserLimits()
//            .withOrder_min(0.001)
//            .withExchange(0.002)
//            .withWithdraw_min(0.003)
//            .withWithdraw_max(0.004);

    String baseLocator = "table_row";
    List<WebElement> elements = wd.findElements(By.className(baseLocator));

    System.out.println("elements.size() " + elements.size());

    for(WebElement element: elements) {
      //type(Double.toString(userLimits1.getOrder_min()), By.cssSelector("input[name='order_min']"));
      element.findElement(By.cssSelector("input[name='order_min']")).clear();
      element.findElement(By.cssSelector("input[name='order_min']"))
              .sendKeys(orderMin);
      element.findElement(By.cssSelector("input[name='exchange']")).clear();
      element.findElement(By.cssSelector("input[name='exchange']"))
              .sendKeys(exchange);
      element.findElement(By.cssSelector("input[name='withdraw_min']")).clear();
      element.findElement(By.cssSelector("input[name='withdraw_min']"))
              .sendKeys(withdrawMin);
      element.findElement(By.cssSelector("input[name='withdraw_max']")).clear();
      element.findElement(By.cssSelector("input[name='withdraw_max']"))
              .sendKeys(withdrawMax);

      System.out.println("lalalla");

      String name = element.findElement(By.cssSelector("th:nth-child(1)")).getText();
      double order_min = Double.parseDouble(element.findElement(By.cssSelector("input[name='order_min']")).getAttribute("value"));
      double exchange1 = Double.parseDouble(element.findElement(By.cssSelector("input[name='exchange']")).getAttribute("value"));
      double withdraw_min = Double.parseDouble(element.findElement(By.cssSelector("input[name='withdraw_min']")).getAttribute("value"));
      double withdraw_max = Double.parseDouble(element.findElement(By.cssSelector("input[name='withdraw_max']")).getAttribute("value"));


      UserLimits userLimits2 = new UserLimits()
              .withName(name)
              .withOrder_min(order_min)
              .withExchange(exchange1)
              .withWithdraw_min(withdraw_min)
              .withWithdraw_max(withdraw_max);

      userLimits.add(userLimits2);

    }
    System.out.println("full cycle");
    System.out.println("userLimits set :" + userLimits);
    return userLimits;
  }

  public Set<UserLimits> setUserLimitsWithoutNeo(String orderMin, String exchange, String withdrawMin, String withdrawMax) throws InterruptedException {
    Set<UserLimits> userLimits = new HashSet<>();
    String name;
    double order_min;
    double exchange1;
    double withdraw_min;
    double withdraw_max;

//    UserLimits userLimits1 = new UserLimits()
//            .withOrder_min(0.004)
//            .withExchange(0.05)
//            .withWithdraw_min(0.006)
//            .withWithdraw_max(0.009);

    String baseLocator = "table_row";
    List<WebElement> elements = wd.findElements(By.className(baseLocator));

    System.out.println("elements.size() " + elements.size());

    for(WebElement element: elements) {
      if (!"Neo".equals(element.findElement(By.cssSelector("th:nth-child(1)")).getText())) {
        //System.out.println("name " + element.findElement(By.cssSelector("th:nth-child(1)")).getText());
        element.findElement(By.cssSelector("input[name='order_min']")).clear();
        element.findElement(By.cssSelector("input[name='order_min']"))
                .sendKeys(orderMin);
        element.findElement(By.cssSelector("input[name='exchange']")).clear();
        element.findElement(By.cssSelector("input[name='exchange']"))
                .sendKeys(exchange);
        element.findElement(By.cssSelector("input[name='withdraw_min']")).clear();
        element.findElement(By.cssSelector("input[name='withdraw_min']"))
                .sendKeys(withdrawMin);
        element.findElement(By.cssSelector("input[name='withdraw_max']")).clear();
        element.findElement(By.cssSelector("input[name='withdraw_max']"))
                .sendKeys(withdrawMax);
      }
      //System.out.println("lalalla");

      if (!"Neo".equals(element.findElement(By.cssSelector("th:nth-child(1)")).getText())) {
        // если  монета не NEO то берем атрибут value
         name = element.findElement(By.cssSelector("th:nth-child(1)")).getText();
         order_min = Double.parseDouble(element.findElement(By.cssSelector("input[name='order_min']")).getAttribute("value"));
         exchange1 = Double.parseDouble(element.findElement(By.cssSelector("input[name='exchange']")).getAttribute("value"));
         withdraw_min = Double.parseDouble(element.findElement(By.cssSelector("input[name='withdraw_min']")).getAttribute("value"));
         withdraw_max = Double.parseDouble(element.findElement(By.cssSelector("input[name='withdraw_max']")).getAttribute("value"));
      } else { //если монета NEO то берем атрибут placeholder
         name = element.findElement(By.cssSelector("th:nth-child(1)")).getText();
         order_min = Double.parseDouble(element.findElement(By.cssSelector("input[name='order_min']")).getAttribute("placeholder"));
         exchange1 = Double.parseDouble(element.findElement(By.cssSelector("input[name='exchange']")).getAttribute("placeholder"));
         withdraw_min = Double.parseDouble(element.findElement(By.cssSelector("input[name='withdraw_min']")).getAttribute("placeholder"));
         withdraw_max = Double.parseDouble(element.findElement(By.cssSelector("input[name='withdraw_max']")).getAttribute("placeholder"));
      }

      UserLimits userLimits2 = new UserLimits()
              .withName(name)
              .withOrder_min(order_min)
              .withExchange(exchange1)
              .withWithdraw_min(withdraw_min)
              .withWithdraw_max(withdraw_max);

      userLimits.add(userLimits2);

    }
    System.out.println("full cycle");
    System.out.println("userLimits set :" + userLimits);
    return userLimits;
  }


  public UserLimits setUserNeoLimits(String orderMin, String exchange, String withdrawMin, String withdrawMax) {
    UserLimits userLimits2 = null;
    String baseLocator = "table_row";
    List<WebElement> elements = wd.findElements(By.className(baseLocator));
    for(WebElement element: elements) {
      if ("Neo".equals(element.findElement(By.cssSelector("th:nth-child(1)")).getText())) {
        element.findElement(By.cssSelector("input[name='order_min']")).clear();
        element.findElement(By.cssSelector("input[name='order_min']"))
                .sendKeys(orderMin);
        element.findElement(By.cssSelector("input[name='exchange']")).clear();
        element.findElement(By.cssSelector("input[name='exchange']"))
                .sendKeys(exchange);
        element.findElement(By.cssSelector("input[name='withdraw_min']")).clear();
        element.findElement(By.cssSelector("input[name='withdraw_min']"))
                .sendKeys(withdrawMin);
        element.findElement(By.cssSelector("input[name='withdraw_max']")).clear();
        element.findElement(By.cssSelector("input[name='withdraw_max']"))
                .sendKeys(withdrawMax);


        String name = element.findElement(By.cssSelector("th:nth-child(1)")).getText();
        double order_min = Double.parseDouble(element.findElement(By.cssSelector("input[name='order_min']")).getAttribute("value"));
        double exchange1 = Double.parseDouble(element.findElement(By.cssSelector("input[name='exchange']")).getAttribute("value"));
        double withdraw_min = Double.parseDouble(element.findElement(By.cssSelector("input[name='withdraw_min']")).getAttribute("value"));
        double withdraw_max = Double.parseDouble(element.findElement(By.cssSelector("input[name='withdraw_max']")).getAttribute("value"));


        userLimits2 = new UserLimits()
                .withName(name)
                .withOrder_min(order_min)
                .withExchange(exchange1)
                .withWithdraw_min(withdraw_min)
                .withWithdraw_max(withdraw_max);
      }
    }
    System.out.println("userNeoLimits from WEB: " + userLimits2 );
    return userLimits2;
  }

  public UserLimits setUserNeoEmptyValueLimits(String orderMin, String exchange, String withdrawMin, String withdrawMax) {
    UserLimits userLimits2 = null;
    String baseLocator = "table_row";
    List<WebElement> elements = wd.findElements(By.className(baseLocator));
    for(WebElement element: elements) {
      if ("Neo".equals(element.findElement(By.cssSelector("th:nth-child(1)")).getText())) {
        element.findElement(By.cssSelector("input[name='order_min']")).clear();
        element.findElement(By.cssSelector("input[name='order_min']"))
                .sendKeys(orderMin);
        element.findElement(By.cssSelector("input[name='order_min']"))
                .sendKeys(Keys.BACK_SPACE);
        element.findElement(By.cssSelector("input[name='exchange']")).clear();
        element.findElement(By.cssSelector("input[name='exchange']"))
                .sendKeys(exchange);
        element.findElement(By.cssSelector("input[name='exchange']"))
                .sendKeys(Keys.BACK_SPACE);
        element.findElement(By.cssSelector("input[name='withdraw_min']")).clear();
        element.findElement(By.cssSelector("input[name='withdraw_min']"))
                .sendKeys(withdrawMin);
        element.findElement(By.cssSelector("input[name='withdraw_min']"))
                .sendKeys(Keys.BACK_SPACE);
        element.findElement(By.cssSelector("input[name='withdraw_max']")).clear();
        element.findElement(By.cssSelector("input[name='withdraw_max']"))
                .sendKeys(withdrawMax);
        element.findElement(By.cssSelector("input[name='withdraw_max']"))
                .sendKeys(Keys.BACK_SPACE);
      }
    }
     return null;
  }

  public Set<UserLimits> setUserZeroLimitsWithoutNeo(String orderMin, String exchange, String withdrawMin, String withdrawMax) {
    UserLimits userLimits2 = null;
    String baseLocator = "table_row";
    List<WebElement> elements = wd.findElements(By.className(baseLocator));
    for(WebElement element: elements) {
      if (!"Neo".equals(element.findElement(By.cssSelector("th:nth-child(1)")).getText())) {
        element.findElement(By.cssSelector("input[name='order_min']")).clear();
        element.findElement(By.cssSelector("input[name='order_min']"))
                .sendKeys(orderMin);
        element.findElement(By.cssSelector("input[name='order_min']"))
                .sendKeys(Keys.BACK_SPACE);
        element.findElement(By.cssSelector("input[name='exchange']")).clear();
        element.findElement(By.cssSelector("input[name='exchange']"))
                .sendKeys(exchange);
        element.findElement(By.cssSelector("input[name='exchange']"))
                .sendKeys(Keys.BACK_SPACE);
        element.findElement(By.cssSelector("input[name='withdraw_min']")).clear();
        element.findElement(By.cssSelector("input[name='withdraw_min']"))
                .sendKeys(withdrawMin);
        element.findElement(By.cssSelector("input[name='withdraw_min']"))
                .sendKeys(Keys.BACK_SPACE);
        element.findElement(By.cssSelector("input[name='withdraw_max']")).clear();
        element.findElement(By.cssSelector("input[name='withdraw_max']"))
                .sendKeys(withdrawMax);
        element.findElement(By.cssSelector("input[name='withdraw_max']"))
                .sendKeys(Keys.BACK_SPACE);
      }
    }
    return null;
  }

  public UserLimits setUserNeoEmptyLimits() throws InterruptedException {

    String baseLocator = "table_row";
    List<WebElement> elements = wd.findElements(By.className(baseLocator));
    for(WebElement element: elements) {
      if ("Neo".equals(element.findElement(By.cssSelector("th:nth-child(1)")).getText())) {

      element.findElement(By.cssSelector("input[name='order_min']")).click();

      element.findElement(By.cssSelector("input[name='exchange']")).click();

      element.findElement(By.cssSelector("input[name='withdraw_min']")).click();

      element.findElement(By.cssSelector("input[name='withdraw_max']")).click();
    }
    }
    return null;
  }

  public Set<UserLimits> setUserEmptyLimitsWithoutNeo() throws InterruptedException {

    String baseLocator = "table_row";
    List<WebElement> elements = wd.findElements(By.className(baseLocator));
    for(WebElement element: elements) {
      if (!"Neo".equals(element.findElement(By.cssSelector("th:nth-child(1)")).getText())) {

        element.findElement(By.cssSelector("input[name='order_min']")).click();

        element.findElement(By.cssSelector("input[name='exchange']")).click();

        element.findElement(By.cssSelector("input[name='withdraw_min']")).click();

        element.findElement(By.cssSelector("input[name='withdraw_max']")).click();
      }
    }
    return null;
  }
}
