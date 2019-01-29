package tests;

import model.*;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Set;
import static org.testng.Assert.assertEquals;

public class Users extends TestBase {

  int userId = 262;

// Тест берет первого юзера с API и этого же юзера ID 262 с БД и сравнивает эти обьекты по всем полям.
  @Test(priority = 1)
  public void checkUserAtListFromApiAndDb() throws IOException, ParseException, SQLException {

    Set<UserData> oneUserFromRequest =  am.getApiUserHelper().getOneUserFromApi(true);
// Устанавливаем коннект с БД
    cm.getConnection();
    Set<UserData> oneUserFromDb = cm.getSqlUserHelper().getOneUserFromDb("SELECT U.id," +
            " concat(UD.first_name, ' ', UD.last_name) as fullName, U.email, U.last_login," +
            " U.created_at, US.name as kyc, UAS.name as status\n" +
            "FROM coin4coin_db.users U  \n" +
            "join coin4coin_db.user_datas UD on U.id = UD.user_id \n" +
            "join coin4coin_db.user_statuses US on U.status_id = US.id\n" +
            "join coin4coin_db.user_account_statuses UAS on U.account_status_id = UAS.id\n" +
            "where U.id=(select Max(users.id) from coin4coin_db.users);");
// oneUserFromDB - expected result
  assertEquals(oneUserFromRequest, oneUserFromDb);
  }

  // Тест берет первого юзера с WEB морды и этого же юзера (первого юзера) с АПИ и сравнивает эти обьекты по всем полям.
  @Test(priority = 2)
  public void checkUserAtListFromApiAndWeb() throws InterruptedException, ParseException, IOException {

    app.goTo().usersPage();
    //Thread.sleep(7000);
    Set<UserData> oneUserFromWeb = app.getUserHelper().getOneUserFromWeb(false);
    Set<UserData> userOneFromRequest =  am.getApiUserHelper().getOneUserFromApi(false);

    assertEquals(oneUserFromWeb, userOneFromRequest);

  }

  @Test(priority = 3)
  public void checkUserAccountInfoFromApiAndWeb() throws InterruptedException, IOException {
    //app.goTo().usersPage(); // если используем тест в Suite, то не нужно перехрдить на страничку и засыпать
    //Thread.sleep(7000);
    app.goTo().userInfo();
    Thread.sleep(5000);
    Set<UserData> userAccountInfoFromWeb = app.getUserHelper().getUserAccountInfoFromWeb();
    System.out.println("userAccountInfoFromWeb equal : " + userAccountInfoFromWeb);
    Set<UserData> userAccountInfoFromApi = am.getApiUserHelper().getUserAccountInfoFromApi();

    assertEquals(userAccountInfoFromWeb,userAccountInfoFromApi );
  }

  @Test(priority = 4)
  public void checkUserAccountInfoFromApiAndDb() throws IOException, SQLException {
    Set<UserData> userAccountInfoFromApi = am.getApiUserHelper().getUserAccountInfoFromApi();
    //cm.getConnection(); // если используем тест в Suite, то не нужно делать еще один коннект к ДБ
    Set<UserData> userAccountInfoFromDb = cm.getSqlUserHelper()
            .getUserAccountInfoFromDb("SELECT U.id, concat(UD.first_name, ' ', UD.last_name) as fullName," +
                    " UD.phone, U.email, UT.name as accounTypeName \n" +
                    "FROM coin4coin_db.users U \n" +
                    "join coin4coin_db.user_datas UD on U.id = UD.user_id \n" +
                    "join coin4coin_db.account_types UT on U.account_type_id=UT.id\n" +
                    "where U.id=(select Max(users.id) from coin4coin_db.users)");

    assertEquals(userAccountInfoFromApi,userAccountInfoFromDb);
  }

  @Test(priority = 5)
  public void checkUserAccountFromApiAndWeb() throws InterruptedException, IOException, ParseException {
    app.goTo().usersPage(); // если используем тест в Suite, то не нужно перехрдить на страничку и засыпать
    Thread.sleep(9000);
    app.goTo().userInfo();
    Thread.sleep(5000);
    Set<UserAccount> userAccountFromApi = am.getApiUserHelper().getUserAccountFromApi();
    Set<UserAccount> userAccountFromWeb = app.getUserHelper().getUserAccountFromWeb();
    System.out.println("userAccountFromWeb :" + userAccountFromWeb);
    assertEquals(userAccountFromWeb, userAccountFromApi);
  }

  // Добавить кейс checkUserAccountFromApiAndDB()

  @Test
  public void checkApproveButtonAtUserAccount() throws InterruptedException, ParseException, IOException, SQLException {
    // подготовка теста, установка status_id=3
    cm.getConnection();
    cm.getSqlUserHelper().setIntValue("update coin4coin_db.user_verifications " +
            "set status_id=3 where user_id=262 and verification_id=3;");

    // тест когда, status_id=3 Waiting и появляются кнопки, нажимаем approve
    app.goTo().usersPage(); // если используем тест в Suite, то не нужно перехрдить на страничку и засыпать
    Thread.sleep(9000);
    app.goTo().userInfo();
    Thread.sleep(4000);
    System.out.println("Before:\n");
    UserAccount statusFromWebBefore = app.getUserHelper().getStatusAtUserAccountFromWeb();
    UserAccount statusFromApiBefore = am.getApiUserHelper().getStatusAtUserAccountFromApi();

    assertEquals(statusFromWebBefore, statusFromApiBefore);

    app.press().approveButton();

    Thread.sleep(3000);
    System.out.println("After:\n");
    UserAccount statusFromWebAfter = app.getUserHelper().getStatusAtUserAccountFromWeb();
    UserAccount statusFromApiAfter = am.getApiUserHelper().getStatusAtUserAccountFromApi();

    assertEquals(statusFromWebAfter,statusFromApiAfter);
  }

  @Test
  public void checkRejectButtonAtUserAccount() throws IOException, SQLException, InterruptedException {
    // подготовка теста, установка status_id=3
    cm.getConnection();
    cm.getSqlUserHelper().setIntValue("update coin4coin_db.user_verifications " +
            "set status_id=3 where user_id=262 and verification_id=3;");

    // тест когда, status_id=3 Waiting и появляются кнопки, нажимаем Reject
    app.goTo().usersPage(); // если используем тест в Suite, то не нужно перехрдить на страничку и засыпать
    Thread.sleep(9000);
    app.goTo().userInfo();
    Thread.sleep(4000);
    System.out.println("Before:\n");
    UserAccount statusFromWebBefore = app.getUserHelper().getStatusAtUserAccountFromWeb();
    UserAccount statusFromApiBefore = am.getApiUserHelper().getStatusAtUserAccountFromApi();

    assertEquals(statusFromWebBefore, statusFromApiBefore);

    app.press().rejectButton();

    Thread.sleep(3000);
    System.out.println("After:\n");
    UserAccount statusFromWebAfter = app.getUserHelper().getStatusAtUserAccountFromWeb();
    UserAccount statusFromApiAfter = am.getApiUserHelper().getStatusAtUserAccountFromApi();

    assertEquals(statusFromWebAfter,statusFromApiAfter);
  }

  @Test
  public void checkUserBalancesFromApiAndWeb() throws InterruptedException, IOException {
    app.goTo().usersPage(); // если используем тест в Suite, то не нужно переходить на страничку и засыпать
    Thread.sleep(9000);
    app.goTo().userInfo();
    Thread.sleep(4000);
    app.goTo().userBalances();
    Set<UserAssets> userBalancesFromWeb = app.getUserHelper().getUserBalancesFromWeb();
    Set<UserAssets> userBalancesFromApi = am.getApiUserHelper().getUserBalancesFromApi();
    assertEquals(userBalancesFromWeb, userBalancesFromApi);
  }

  @Test
  public void checkUserTransactionsFromApiAndWeb() throws InterruptedException, IOException {
    app.goTo().usersPage(); // если используем тест в Suite, то не нужно переходить на страничку и засыпать
    Thread.sleep(9000);
    app.goTo().userInfo();
    Thread.sleep(4000);
    app.goTo().userTransactions();
    Set<UserTxes> userTxesFromWeb = app.getUserHelper().getUserTxesFromWeb();
    Set<UserTxes> userTxesFromApi = am.getApiUserHelper().getUserTransactionsFromApi();
    assertEquals(userTxesFromWeb, userTxesFromApi);
  }

  @Test
  public void checkTextIfUserHasNotOrders() throws InterruptedException {
    app.goTo().usersPage(); // если используем тест в Suite, то не нужно переходить на страничку и засыпать
    Thread.sleep(9000);
    app.goTo().userInfo();
    Thread.sleep(4000);
    app.goTo().userOrders();
    assertEquals(app.getUserHelper()
            .text(By.cssSelector("table.table.orders tr:nth-child(2) > th")), "This user has no orders");
  }

  @Test
  public  void checkUserOrdersFromApiAndWeb() throws InterruptedException, IOException {
    app.goTo().usersPage(); // если используем тест в Suite, то не нужно переходить на страничку и засыпать
    Thread.sleep(9000);
    app.goTo().userInfo();
    Thread.sleep(4000);
    app.goTo().userOrders();
    Set<UserOrders> userOrdersFromWeb = app.getUserHelper().getUserOrdersFromWeb();
    Set<UserOrders> userOrdersFromApi = am.getApiUserHelper().getUserOrdersFromApi();
    assertEquals(userOrdersFromWeb, userOrdersFromApi);
  }

  @Test
  public void checkTextIfUsersLimitsOff() throws InterruptedException {
    app.goTo().usersPage(); // если используем тест в Suite, то не нужно переходить на страничку и засыпать
    Thread.sleep(9000);
    app.goTo().userInfo();
    Thread.sleep(4000);
    app.goTo().userLimits();
    Thread.sleep(1000);
//    System.out.println(app.getUserHelper()
//            .text(By.cssSelector(".text-center:nth-child(1)")));
    assertEquals(app.getUserHelper()
            .text(By.cssSelector(".text-center:nth-child(1)")), "Personal limits is not active");
  }

  @Test
  public void checkStateOfToggleIfToggleOffAtUserLimits() throws IOException, SQLException, InterruptedException {
    // подготовка теста, установка personal_fee_active=0
    cm.getConnection();
    cm.getSqlUserHelper().setIntValue(String.format("update coin4coin_db.users " +
            "set personal_fee_active=0 where id=%s;", userId));
    app.goTo().usersPage(); // если используем тест в Suite, то не нужно переходить на страничку и засыпать
    Thread.sleep(9000);
    app.goTo().userInfo();
    Thread.sleep(4000);
    app.goTo().userLimits();
    Thread.sleep(1000);
    assertEquals(app.getUserHelper()
            .elementPresent(By.cssSelector("button.active")), false);
  }

  @Test
  public void checkStateOfToggleIfToggleOnAtUserLimits() throws IOException, SQLException, InterruptedException {
    // подготовка теста, установка personal_fee_active=0
    cm.getConnection();
    cm.getSqlUserHelper().setIntValue(String.format("update coin4coin_db.users " +
            "set personal_fee_active=1 where id=%s;",  userId));
    app.goTo().usersPage(); // если используем тест в Suite, то не нужно переходить на страничку и засыпать
    Thread.sleep(9000);
    app.goTo().userInfo();
    Thread.sleep(4000);
    app.goTo().userLimits();
    Thread.sleep(1000);
    assertEquals(app.getUserHelper()
            .elementPresent(By.cssSelector("button.active")), true);
  }

  @Test
  public void checkTurnOnToggleAtUserLimits() throws IOException, SQLException, InterruptedException {
    // подготовка теста, установка personal_fee_active=0
    cm.getConnection();
    cm.getSqlUserHelper().setIntValue(String.format("update coin4coin_db.users " +
            "set personal_fee_active=0 where id=%s;",  userId));
    app.goTo().usersPage(); // если используем тест в Suite, то не нужно переходить на страничку и засыпать
    Thread.sleep(9000);
    app.goTo().userInfo();
    Thread.sleep(4000);
    app.goTo().userLimits();
    Thread.sleep(1000);
    app.getUserHelper().turnOnTogleAtUserLimits();
    Thread.sleep(4000);
  }

}
