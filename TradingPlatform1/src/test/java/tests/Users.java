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

  //int userId = cm.getSqlUserHelper().getMaxUserId("select Max(id) from coin4coin_db.users");
  //String baseAdminPage = "http://209.182.216.247/admin/";

//  public Users() throws SQLException {
//  }

  // Тест берет первого юзера с API и этого же юзера с БД и сравнивает эти обьекты по всем полям.
  @Test(priority = 1)
  public void checkUserAtListFromApiAndDb() throws IOException, ParseException, SQLException {

    Set<UserData> oneUserFromRequest =  am.getApiUserHelper().getOneUserFromApi(true);
    Set<UserData> oneUserFromDb = cm.getSqlUserHelper().getOneUserFromDb("SELECT U.id," +
            " concat(UD.first_name, ' ', UD.last_name) as fullName, U.email, U.last_login," +
            " U.created_at, US.name as kyc, UAS.name as status\n" +
            "FROM coin4coin_db.users U  \n" +
            "join coin4coin_db.user_datas UD on U.id = UD.user_id \n" +
            "join coin4coin_db.user_statuses US on U.status_id = US.id\n" +
            "join coin4coin_db.user_account_statuses UAS on U.account_status_id = UAS.id\n" +
            "where U.id=(select Max(users.id) from coin4coin_db.users);");
  assertEquals(oneUserFromRequest, oneUserFromDb);
  }

  // Тест берет первого юзера с WEB морды и этого же юзера (первого юзера) с АПИ и сравнивает эти обьекты по всем полям.
  @Test(priority = 2)
  public void checkUserAtListFromApiAndWeb() throws InterruptedException, ParseException, IOException {

    app.goTo().usersPage();
    Thread.sleep(7000);
    Set<UserData> oneUserFromWeb = app.getUserHelper().getOneUserFromWeb(false);
    Set<UserData> userOneFromRequest =  am.getApiUserHelper().getOneUserFromApi(false);
    assertEquals(oneUserFromWeb, userOneFromRequest);
  }

  @Test(priority = 3)
  public void checkUserAccountInfoFromApiAndWeb() throws InterruptedException, IOException {
    app.getSessionHelper().getBaseAdminPage(baseAdminPage);
    app.goTo().usersPage();
    Thread.sleep(7000);
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
    app.getSessionHelper().getBaseAdminPage(baseAdminPage);
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

  @Test (priority = 6)
  public void checkApproveButtonAtUserAccount() throws InterruptedException, ParseException, IOException, SQLException {
    // подготовка теста, установка status_id=3
    //cm.getConnection();
    cm.getSqlUserHelper().setIntValue("update coin4coin_db.user_verifications " +
            "set status_id=3 where user_id=262 and verification_id=3;");

    // тест когда, status_id=3 Waiting и появляются кнопки, нажимаем approve
    app.getSessionHelper().getBaseAdminPage(baseAdminPage);
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

  @Test (priority = 7)
  public void checkRejectButtonAtUserAccount() throws IOException, SQLException, InterruptedException {
    // подготовка теста, установка status_id=3
    cm.getSqlUserHelper().setIntValue("update coin4coin_db.user_verifications " +
            "set status_id=3 where user_id=262 and verification_id=3;");

    // тест когда, status_id=3 Waiting и появляются кнопки, нажимаем Reject
    app.getSessionHelper().getBaseAdminPage(baseAdminPage);
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

  @Test (priority = 8)
  public void checkUserBalancesFromApiAndWeb() throws InterruptedException, IOException {
    app.getSessionHelper().getBaseAdminPage(baseAdminPage);
    app.goTo().usersPage(); // если используем тест в Suite, то не нужно переходить на страничку и засыпать
    Thread.sleep(9000);
    app.goTo().userInfo();
    Thread.sleep(4000);
    app.goTo().userBalances();
    Thread.sleep(1000);
    Set<UserAssets> userBalancesFromWeb = app.getUserHelper().getUserBalancesFromWeb();
    Set<UserAssets> userBalancesFromApi = am.getApiUserHelper().getUserBalancesFromApi();
    assertEquals(userBalancesFromWeb, userBalancesFromApi);
  }

  @Test (priority = 9)
  public void checkUserTransactionsFromApiAndWeb() throws InterruptedException, IOException {
    app.getSessionHelper().getBaseAdminPage(baseAdminPage);
    app.goTo().usersPage();
    Thread.sleep(9000);
    app.goTo().userInfo();
    Thread.sleep(4000);
    app.goTo().userTransactions();
    Thread.sleep(1000);
    Set<UserTxes> userTxesFromWeb = app.getUserHelper().getUserTxesFromWeb();
    Set<UserTxes> userTxesFromApi = am.getApiUserHelper().getUserTransactionsFromApi();
    assertEquals(userTxesFromWeb, userTxesFromApi);
  }

  /*@Test (priority = 10) // Наш юзер 262 имеет ордера
  public void checkTextIfUserHasNotOrders() throws InterruptedException {
    app.getSessionHelper().getBaseAdminPage(baseAdminPage);
    app.goTo().usersPage(); // если используем тест в Suite, то не нужно переходить на страничку и засыпать
    Thread.sleep(9000);
    app.goTo().userInfo();
    Thread.sleep(4000);
    app.goTo().userOrders();
    assertEquals(app.getUserHelper()
            .text(By.cssSelector("table.table.orders tr:nth-child(2) > th")), "This user has no orders");
  } */

  @Test (priority = 11)
  public  void checkUserOrdersFromApiAndWeb() throws InterruptedException, IOException {
    app.getSessionHelper().getBaseAdminPage(baseAdminPage);
    app.goTo().usersPage();
    Thread.sleep(9000);
    app.goTo().userInfo();
    Thread.sleep(4000);
    app.goTo().userOrders();
    Thread.sleep(1000);
    Set<UserOrders> userOrdersFromWeb = app.getUserHelper().getUserOrdersFromWeb();
    Set<UserOrders> userOrdersFromApi = am.getApiUserHelper().getUserOrdersFromApi();
    assertEquals(userOrdersFromWeb, userOrdersFromApi);
  }

  @Test (priority = 12)
  public void checkTextIfUsersLimitsOff() throws InterruptedException, IOException, SQLException {
    // подготовка теста, установка personal_fee_active=0
    int userIdMax = cm.getSqlUserHelper().getMaxUserId("select Max(id) from coin4coin_db.users");
    cm.getSqlUserHelper().setIntValue(String.format("update coin4coin_db.users " +
            "set personal_fee_active=0 where id=%s;", userIdMax));
    app.getSessionHelper().getBaseAdminPage(baseAdminPage);
    app.goTo().usersPage(); // если используем тест в Suite, то не нужно переходить на страничку и засыпать
    Thread.sleep(9000);
    app.goTo().userInfo();
    Thread.sleep(4000);
    app.goTo().userLimits();
    Thread.sleep(1000);
    assertEquals(app.getUserHelper()
            .text(By.cssSelector(".text-center:nth-child(1)")), "Personal limits is not active");
  }

  @Test (priority = 13)
  public void checkStateOfToggleIfToggleOffAtUserLimits() throws IOException, SQLException, InterruptedException {
    // подготовка теста, установка personal_fee_active=0
    int userIdMax = cm.getSqlUserHelper().getMaxUserId("select Max(id) from coin4coin_db.users");
    cm.getSqlUserHelper().setIntValue(String.format("update coin4coin_db.users " +
            "set personal_fee_active=0 where id=%s;", userIdMax));
    app.getSessionHelper().getBaseAdminPage(baseAdminPage);
    app.goTo().usersPage(); // если используем тест в Suite, то не нужно переходить на страничку и засыпать
    Thread.sleep(9000);
    app.goTo().userInfo();
    Thread.sleep(4000);
    app.goTo().userLimits();
    Thread.sleep(1000);
    assertEquals(app.getUserHelper()
            .elementPresent(By.cssSelector("button.active")), false);
  }

  @Test (priority = 14)
  public void checkStateOfToggleIfToggleOnAtUserLimits() throws IOException, SQLException, InterruptedException {
    // подготовка теста, установка personal_fee_active=1
    int userIdMax = cm.getSqlUserHelper().getMaxUserId("select Max(id) from coin4coin_db.users");
    cm.getSqlUserHelper().setIntValue(String.format("update coin4coin_db.users " +
            "set personal_fee_active=1 where id=%s;",  userIdMax));
    app.getSessionHelper().getBaseAdminPage(baseAdminPage);
    app.goTo().usersPage();
    Thread.sleep(9000);
    app.goTo().userInfo();
    Thread.sleep(4000);
    app.goTo().userLimits();
    Thread.sleep(1000);
    assertEquals(app.getUserHelper()
            .elementPresent(By.cssSelector("button.active")), true);
  }

  @Test (priority = 15)
  public void checkTurnOnToggleAtUserLimits() throws IOException, SQLException, InterruptedException {
    // подготовка теста, установка personal_fee_active=0
    //cm.getConnection();
    int userIdMax = cm.getSqlUserHelper().getMaxUserId("select Max(id) from coin4coin_db.users");
    cm.getSqlUserHelper().setIntValue(String.format("update coin4coin_db.users " +
            "set personal_fee_active=0 where id=%s;",  userIdMax));
    app.getSessionHelper().getBaseAdminPage(baseAdminPage);
    app.goTo().usersPage(); // если используем тест в Suite, то не нужно переходить на страничку и засыпать
    Thread.sleep(9000);
    app.goTo().userInfo();
    Thread.sleep(4000);
    app.goTo().userLimits();
    Thread.sleep(1000);
    // в этом случае включаем тогл
    app.getUserHelper().turnOnOffToggleAtUserLimits();
    Thread.sleep(2000);
    int personalFeeActiveFromDb = cm.getSqlUserHelper()
            .getPersonalFeeActiveFromDb(String.format("SELECT personal_fee_active " +
                    "FROM coin4coin_db.users where id=%s;", userIdMax));
    assertEquals(1, personalFeeActiveFromDb);
  }


  @Test (priority = 16)
  public void checkTurnOffToggleAtUserLimits() throws IOException, SQLException, InterruptedException {
    // подготовка теста, установка personal_fee_active=1
    int userIdMax = cm.getSqlUserHelper().getMaxUserId("select Max(id) from coin4coin_db.users");
    cm.getSqlUserHelper().setIntValue(String.format("update coin4coin_db.users " +
            "set personal_fee_active=1 where id=%s;",  userIdMax));
    app.getSessionHelper().getBaseAdminPage(baseAdminPage);
    app.goTo().usersPage();
    Thread.sleep(9000);
    app.goTo().userInfo();
    Thread.sleep(4000);
    app.goTo().userLimits();
    Thread.sleep(1000);
    // в этом случае выключаем тогл
    app.getUserHelper().turnOnOffToggleAtUserLimits();
    Thread.sleep(2000);
    int personalFeeActiveFromDb = cm.getSqlUserHelper()
            .getPersonalFeeActiveFromDb(String.format("SELECT personal_fee_active " +
                    "FROM coin4coin_db.users where id=%s;", userIdMax));
    assertEquals(0, personalFeeActiveFromDb);
  }

  /*SELECT UA.code, UF.order_min, UF.exchange, UF.withdraw_min, UF.withdraw_max
FROM coin4coin_db.user_fees UF
join coin4coin_db.assets UA on UF.asset_id=UA.id where UF.user_id=262;*/
  @Test (priority = 17)
  public  void checkUserLimitsFromApiAndWeb() throws InterruptedException, IOException, SQLException {
    // подготовка теста, установка personal_fee_active=1
    int userIdMax = cm.getSqlUserHelper().getMaxUserId("select Max(id) from coin4coin_db.users");
    cm.getSqlUserHelper().setIntValue(String.format("update coin4coin_db.users " +
            "set personal_fee_active=1 where id=%s;",  userIdMax));
    app.getSessionHelper().getBaseAdminPage(baseAdminPage);
    app.goTo().usersPage(); // если используем тест в Suite, то не нужно переходить на страничку и засыпать
    Thread.sleep(9000);
    app.goTo().userInfo();
    Thread.sleep(4000);
    app.goTo().userLimits();
    Thread.sleep(2000);
    Set<UserLimits> userLimitsFromWeb = app.getUserHelper().getUserLimitsWithoutNeoFromWeb();
    Set<UserLimits> userLimitsFromApi = am.getApiUserHelper().getUserLimitsWithoutNeoFromApi();
    assertEquals(userLimitsFromWeb, userLimitsFromApi);
  }

  @Test (priority = 18) // проверяем что в Все вссеты вообщем записываются значения, передаются и сохраняются в БД
  public void checkSetUserLimitsFromWebAndDb() throws IOException, SQLException, InterruptedException {
    // подготовка теста, установка personal_fee_active=1
    int userIdMax = cm.getSqlUserHelper().getMaxUserId("select Max(id) from coin4coin_db.users");
    cm.getSqlUserHelper().setIntValue(String.format("update coin4coin_db.users " +
            "set personal_fee_active=1 where id=%s;",  userIdMax));
    app.getSessionHelper().getBaseAdminPage(baseAdminPage);
    app.goTo().usersPage();
    Thread.sleep(9000);
    app.goTo().userInfo();
    Thread.sleep(4000);
    app.goTo().userLimits();
    Thread.sleep(4000);
    Set<UserLimits> userLimitsSetFromWeb = app.getUserHelper()
            .setUserLimits("0.001",
                            "0.002",
                            "0.003",
                            "0.004");
    app.press().saveButtonAtUserLimits();
    Thread.sleep(5000);
    Set<UserLimits> userLimitsFromApi = am.getApiUserHelper().getUserLimitsWithoutNeoFromApi();
    assertEquals(userLimitsSetFromWeb, userLimitsFromApi);
  }

}
