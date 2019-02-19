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
  String baseAdminPage = "http://209.182.216.247/admin/";

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
    Set<UserLimits> userLimitsFromWeb = app.getUserHelper().getUserLimitsFromWeb();
    Set<UserLimits> userLimitsFromApi = am.getApiUserHelper().getUserLimitsFromApi();
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
    Set<UserLimits> userLimitsFromApi = am.getApiUserHelper().getUserLimitsFromApi();
    assertEquals(userLimitsSetFromWeb, userLimitsFromApi);
  }

  @Test (priority = 19) //проверяем что в все ассеты кроме НЕО вообщем записываются значения, передаются и сохраняются в БД
  public void checkSetUserLimitsWithoutNeoFromWebAndDb() throws IOException, SQLException, InterruptedException {
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
            .setUserLimitsWithoutNeo("0.004",
                                    "0.005",
                                    "0.006",
                                    "0.007");
    app.press().saveButtonAtUserLimits();
    Thread.sleep(5000);
    Set<UserLimits> userLimitsFromApi = am.getApiUserHelper().getUserLimitsFromApi();
    assertEquals(userLimitsSetFromWeb, userLimitsFromApi);
  }
@Test (priority = 20)// Установка Минимальные значения на Все ассеты кроме НЕО
  public void checkSetUserLimitsWithoutNeoFromWebAndDb_2() throws IOException, SQLException, InterruptedException {
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
            .setUserLimitsWithoutNeo("0.0000000001",
                    "0.0000000001",
                    "0.0000000001",
                    "0.0000000001");
    app.press().saveButtonAtUserLimits();
    Thread.sleep(5000);
    Set<UserLimits> userLimitsFromApi = am.getApiUserHelper().getUserLimitsFromApi();
    assertEquals(userLimitsSetFromWeb, userLimitsFromApi);
  }

  @Test (priority = 21) // установка Макс значений на все ассеты кроме Нео
  public void checkSetUserLimitsWithoutNeoFromWebAndDb_3() throws IOException, SQLException, InterruptedException {
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
            .setUserLimitsWithoutNeo("10000000000",
                    "10000000000",
                    "10000000000",
                    "10000000000");
    app.press().saveButtonAtUserLimits();
    Thread.sleep(5000);
    Set<UserLimits> userLimitsFromApi = am.getApiUserHelper().getUserLimitsFromApi();
    assertEquals(userLimitsSetFromWeb, userLimitsFromApi);
  }

  @Test (priority = 22)
  //проверяем что в Все ассеты кроме Нео НЕ записываюься символы кроме цифр и единой точки,
  // только цифры и цифра с точкой передаются и сохраняются в БД
  public void checkSetUserLimitsWithoutNeoFromWebAndDb_4() throws IOException, SQLException, InterruptedException {
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
            .setUserLimitsWithoutNeo(
                    "0.",
                    "abcd!@#$%^&*()0..0001",
                    ".0001",
                    "abcd  -=+0.0001");
    app.press().saveButtonAtUserLimits();
    Thread.sleep(5000);
    Set<UserLimits> userLimitsFromApi = am.getApiUserHelper().getUserLimitsFromApi();
    assertEquals(userLimitsSetFromWeb, userLimitsFromApi);
  }

  @Test (priority = 23)
  //проверяем что в Все ассеты кроме  НЕО кликаем по форме,
  // и это никак не влияет на сохранение.(отправляется пустой массив)
  public void checkSetUserLimitsWithoutNeoFromWebAndDb_5() throws IOException, SQLException, InterruptedException {
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
    Set<UserLimits> userLimitsSetFromWebBefore = app.getUserHelper()
            .setUserEmptyLimitsWithoutNeo();
    app.press().saveButtonAtUserLimits();
    Thread.sleep(5000);
    Set<UserLimits> userLimitsFromApi = am.getApiUserHelper().getUserLimitsFromApi();
    Set<UserLimits> userLimitsSetFromWebAfter = app.getUserHelper().getUserLimitsFromWeb();
    assertEquals(userLimitsSetFromWebAfter, userLimitsFromApi);
  }

  @Test (priority = 24)
  //проверяем что в Все ассеты кроме Нео записали 0
  public void checkSetUserLimitsWithoutNeoFromWebAndDb_6() throws IOException, SQLException, InterruptedException {
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
            .setUserLimitsWithoutNeo(
                    "0",
                    "0",
                    "0",
                    "0");
    app.press().saveButtonAtUserLimits();
    Thread.sleep(5000);
    Set<UserLimits> userLimitsFromApi = am.getApiUserHelper().getUserLimitsFromApi();
    assertEquals(userLimitsSetFromWeb, userLimitsFromApi);
  }

  @Test  (priority = 25)
  //проверяем что в Все ассеты кроме НЕО записали значение больше значения 10000000000,
  // сейчас сервер возвращает 500 ошибку если значение больше 10000000000
  public void checkSetUserLimitsWithoutNeoFromWebAndDb_7() throws IOException, SQLException, InterruptedException {
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
            .setUserLimitsWithoutNeo(
                    "123456789012",
                    "123456789012",
                    "123456789012",
                    "123456789012");
    app.press().saveButtonAtUserLimits();
    Thread.sleep(5000);
    Set<UserLimits> userLimitsFromApi = am.getApiUserHelper().getUserLimitsFromApi();
    assertEquals(userLimitsSetFromWeb, userLimitsFromApi);
  }

  @Test (priority = 26)
  //проверяем что в Все ассеты кроме НЕО передется максимальное кол символов+1, но последний символ не записывается,
  // так как есть ограничение на Фронтенде
  public void checkSetUserLimitsWithoutNeoFromWebAndDb_8() throws IOException, SQLException, InterruptedException {
    // подготовка теста, установка personal_fee_active=1
    int userIdMax = cm.getSqlUserHelper().getMaxUserId("select Max(id) from coin4coin_db.users");
    cm.getSqlUserHelper().setIntValue(String.format("update coin4coin_db.users " +
            "set personal_fee_active=1 where id=%s;",  userIdMax));
    //app.getSessionHelper().getBaseAdminPage(baseAdminPage);
    app.goTo().usersPage();
    Thread.sleep(9000);
    app.goTo().userInfo();
    Thread.sleep(4000);
    app.goTo().userLimits();
    Thread.sleep(4000);
    Set<UserLimits> userLimitsSetFromWeb = app.getUserHelper()
            .setUserLimitsWithoutNeo(
                    "0.00000000001",
                    "0.00000000001",
                    "0.00000000001",
                    "0.00000000001");
    app.press().saveButtonAtUserLimits();
    Thread.sleep(5000);
    Set<UserLimits> userLimitsFromApi = am.getApiUserHelper().getUserLimitsFromApi();
    assertEquals(userLimitsSetFromWeb, userLimitsFromApi);
  }
 @Test (priority = 27)
  //проверяем что в Все ассеты кроме НЕО записали значения, сразу же стерли, в форме путота, в запросе передается 0
  public void checkSetUserLimitsWithoutNeoFromWebAndDb_9() throws IOException, SQLException, InterruptedException {
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
    Set<UserLimits> userLimitsSetFromWebBefore = app.getUserHelper()
            .setUserZeroLimitsWithoutNeo(
                    "1",
                    "1",
                    "1",
                    "1");
    app.press().saveButtonAtUserLimits();
    Thread.sleep(5000);
    Set<UserLimits> userLimitsFromApi = am.getApiUserHelper().getUserLimitsFromApi();
    Set<UserLimits> userLimitsSetFromWebAfter = app.getUserHelper().getUserLimitsFromWeb();
    assertEquals(userLimitsSetFromWebAfter, userLimitsFromApi);
  }


  // =============================================================================================
  // =============================================================================================

  @Test (priority = 28)
  //проверяем что в НЕО вообщем записываюься значения, передаются и сохраняются в БД
  public void checkSetNeoValueAtUserLimits_1() throws IOException, SQLException, InterruptedException {
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
    UserLimits userNeoLimitFromWeb = app.getUserHelper()
            .setUserNeoLimits(
                    "1",
                    "2",
                    "3",
                    "4");
    Thread.sleep(5000);
    app.press().saveButtonAtUserLimits();
    Thread.sleep(5000);
    UserLimits userNeoLimitFromDb = cm.getSqlUserHelper()
            .getUserNeoLimitsFromDb("SELECT UA.code, UA.name, UF.order_min, UF.exchange, UF.withdraw_min, UF.withdraw_max \n" +
                    "FROM coin4coin_db.user_fees UF\n" +
                    "join coin4coin_db.assets UA on UF.asset_id=UA.id where UF.user_id=262 and UF.asset_id=12;");
    assertEquals(userNeoLimitFromDb, userNeoLimitFromWeb);
  }

  @Test  (priority = 29)
  //проверяем что в НЕО записываюься Минимальные значения, передаются и сохраняются в БД
  public void checkSetNeoValueAtUserLimits_2() throws IOException, SQLException, InterruptedException {
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
    Thread.sleep(2000);
    UserLimits userNeoLimitsFromWeb = app.getUserHelper()
            .setUserNeoLimits(
                              "0.0000000001",
                              "0.0000000001",
                              "1",
                              "1"
                            );
    Thread.sleep(5000);
    app.press().saveButtonAtUserLimits();
    Thread.sleep(5000);
    UserLimits userNeoLimitsFromDb = cm.getSqlUserHelper()
            .getUserNeoLimitsFromDb("SELECT UA.code, UA.name, UF.order_min, UF.exchange, UF.withdraw_min, UF.withdraw_max \n" +
                    "FROM coin4coin_db.user_fees UF\n" +
                    "join coin4coin_db.assets UA on UF.asset_id=UA.id where UF.user_id=262 and UF.asset_id=12;");
    assertEquals(userNeoLimitsFromDb, userNeoLimitsFromWeb);
  }

  @Test  (priority = 30)
  //проверяем что в НЕО записываюься Максимальные значения, передаются и сохраняются в БД
  public void checkSetNeoValueAtUserLimits_3() throws IOException, SQLException, InterruptedException {
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
    Thread.sleep(2000);
    UserLimits userNeoLimitsFromWeb = app.getUserHelper()
            .setUserNeoLimits(
                    "10000000000",
                    "10000000000",
                    "10000000000",
                    "10000000000"
            );
    Thread.sleep(5000);
    app.press().saveButtonAtUserLimits();
    Thread.sleep(5000);
    UserLimits userNeoLimitsFromDb = cm.getSqlUserHelper()
            .getUserNeoLimitsFromDb("SELECT UA.code, UA.name, UF.order_min, UF.exchange, UF.withdraw_min, UF.withdraw_max \n" +
                    "FROM coin4coin_db.user_fees UF\n" +
                    "join coin4coin_db.assets UA on UF.asset_id=UA.id where UF.user_id=262 and UF.asset_id=12;");
    assertEquals(userNeoLimitsFromDb, userNeoLimitsFromWeb);
  }

  @Test  (priority = 31)
  public void checkSetNeoValueAtUserLimits_4() throws SQLException, InterruptedException, IOException {
    System.out.println("===checkSetNeoValueAtUserLimits_4===");
    System.out.println("  //проверяем что в НЕО НЕ записываюься символы кроме цифр и единой точки,\n" +
            "  // только цифры и цифра с точкой передаются и сохраняются в БД");
    // подготовка теста
    int userIdMax = cm.getSqlUserHelper().getMaxUserId("select Max(id) from coin4coin_db.users");
    int idNeo = cm.getSqlUserHelper().getIdNeo("SELECT id FROM coin4coin_db.assets where code='neo'");
    int personalFeeActive = cm.getSqlUserHelper()
            .getPersonalFeeActiveFromDb(String.format("select personal_fee_active\n" +
                    "from coin4coin_db.users where id=%s", userIdMax));
    // подготовка теста, установка personal_fee_active=1
    if(personalFeeActive == 0) {
      cm.getSqlUserHelper().setIntValue(String.format("update coin4coin_db.users " +
              "set personal_fee_active=1 where id=%s;", userIdMax));
    }
    // тело теста
    app.getSessionHelper().getBaseAdminPage(baseAdminPage);
    app.goTo().usersPage();
    Thread.sleep(9000);
    app.goTo().userInfo();
    Thread.sleep(4000);
    app.goTo().userLimits();
    Thread.sleep(2000);
    UserLimits userNeoLimitsFromWebBeforeSaving = app.getUserHelper()
            .setUserNeoLimits(
                    "0.",
                    "abcd!@#$%^&*()0..0001",
                    ".0001",
                    "abcd  -=+0.0001"
            );
    Thread.sleep(5000);
    app.press().saveButtonAtUserLimits();
    Thread.sleep(5000);
    UserLimits userNeoLimitsFromDb = cm.getSqlUserHelper()
            .getUserNeoLimitsFromDb(String.format("SELECT UA.code, UA.name, UF.order_min, UF.exchange, UF.withdraw_min, UF.withdraw_max \n" +
                    "FROM coin4coin_db.user_fees UF\n" +
                    "join coin4coin_db.assets UA on UF.asset_id=UA.id where UF.user_id=%s and UF.asset_id=%s;",userIdMax,idNeo));
    UserLimits userNeoLimitsFromWebAfter = app.getUserHelper().getUserNeoLimitsFromWeb();
    UserLimits userNeoLimitsFromApi = am.getApiUserHelper().getUserNeoLimitsFromApi();
    assertEquals(userNeoLimitsFromDb, userNeoLimitsFromWebBeforeSaving);
    assertEquals(userNeoLimitsFromApi, userNeoLimitsFromDb);
    assertEquals(userNeoLimitsFromWebAfter, userNeoLimitsFromApi);
  }

  @Test (priority = 32)
  public void checkSetNeoValueAtUserLimits_5() throws SQLException, InterruptedException, IOException {
    System.out.println("===checkSetNeoValueAtUserLimits_5()===");
    System.out.println("  //проверяем что в НЕО кликаем по форме,\n" +
            "  // и это никак не влияет на сохранение.(отправляется пустой массив)");
    // подготовка теста
    int userIdMax = cm.getSqlUserHelper().getMaxUserId("select Max(id) from coin4coin_db.users");
    int idNeo = cm.getSqlUserHelper().getIdNeo("SELECT id FROM coin4coin_db.assets where code='neo'");
    int personalFeeActive = cm.getSqlUserHelper()
            .getPersonalFeeActiveFromDb(String.format("select personal_fee_active\n" +
                    "from coin4coin_db.users where id=%s", userIdMax));
    // подготовка теста, установка personal_fee_active=1
    if(personalFeeActive == 0) {
      cm.getSqlUserHelper().setIntValue(String.format("update coin4coin_db.users " +
              "set personal_fee_active=1 where id=%s;", userIdMax));
    }
    // тело теста
    app.getSessionHelper().getBaseAdminPage(baseAdminPage);
    app.goTo().usersPage();
    Thread.sleep(9000);
    app.goTo().userInfo();
    Thread.sleep(4000);
    app.goTo().userLimits();
    Thread.sleep(2000);
    UserLimits userNeoLimitsFromWebBefore = app.getUserHelper().getUserNeoLimitsFromWeb();
    // здесь просто прокликали поля, значения в плейсхолдере, значения атрибут value не имеет
    app.getUserHelper().setUserNeoEmptyLimits();
    Thread.sleep(2000);
    app.press().saveButtonAtUserLimits();
    Thread.sleep(5000);
    UserLimits userNeoLimitsFromDb = cm.getSqlUserHelper()
            .getUserNeoLimitsFromDb(String.format("SELECT UA.code, UA.name, UF.order_min, UF.exchange, UF.withdraw_min, UF.withdraw_max \n" +
                    "FROM coin4coin_db.user_fees UF\n" +
                    "join coin4coin_db.assets UA on UF.asset_id=UA.id where UF.user_id=%s and UF.asset_id=%s;",userIdMax,idNeo));
    UserLimits userNeoLimitsFromWebAfter = app.getUserHelper().getUserNeoLimitsFromWeb();
    UserLimits userNeoLimitsFromApi = am.getApiUserHelper().getUserNeoLimitsFromApi();
    assertEquals(userNeoLimitsFromWebBefore, userNeoLimitsFromWebAfter);
    assertEquals(userNeoLimitsFromWebAfter, userNeoLimitsFromApi);
    assertEquals(userNeoLimitsFromApi, userNeoLimitsFromDb);
  }

  @Test  (priority = 33)
  public void checkSetNeoValueAtUserLimits_6() throws SQLException, InterruptedException, IOException {
    System.out.println("===checkSetNeoValueAtUserLimits_6===");
    System.out.println(" проверяем что в НЕО записали значение 0\n");
    // подготовка теста
    int userIdMax = cm.getSqlUserHelper().getMaxUserId("select Max(id) from coin4coin_db.users");
    int idNeo = cm.getSqlUserHelper().getIdNeo("SELECT id FROM coin4coin_db.assets where code='neo'");
    int personalFeeActive = cm.getSqlUserHelper()
            .getPersonalFeeActiveFromDb(String.format("select personal_fee_active\n" +
                    "from coin4coin_db.users \n" +
                    "where id=%s", userIdMax));
    // подготовка теста, установка personal_fee_active=1
    if(personalFeeActive == 0) {
      cm.getSqlUserHelper().setIntValue(String.format("update coin4coin_db.users " +
              "set personal_fee_active=1 where id=%s;", userIdMax));
    }
    UserLimits expectedResult = new UserLimits()
            .withName("Neo")
            .withOrder_min(0)
            .withExchange(0)
            .withWithdraw_min(0)
            .withWithdraw_max(0);
    // тело теста
    app.getSessionHelper().getBaseAdminPage(baseAdminPage);
    app.goTo().usersPage();
    Thread.sleep(9000);
    app.goTo().userInfo();
    Thread.sleep(4000);
    app.goTo().userLimits();
    Thread.sleep(2000);
    UserLimits userNeoLimitsFromWeb = app.getUserHelper()
            .setUserNeoLimits(
                    "0",
                    "0",
                    "0",
                    "0"
            );
    Thread.sleep(5000);
    app.press().saveButtonAtUserLimits();
    Thread.sleep(5000);
    UserLimits userNeoLimitsFromDb = cm.getSqlUserHelper()
            .getUserNeoLimitsFromDb(String.format("SELECT UA.code, UA.name, UF.order_min, UF.exchange, UF.withdraw_min, UF.withdraw_max \n" +
                    "FROM coin4coin_db.user_fees UF\n" +
                    "join coin4coin_db.assets UA on UF.asset_id=UA.id where UF.user_id=%s and UF.asset_id=%s;",userIdMax,idNeo));
    UserLimits userNeoLimitsFromApi = am.getApiUserHelper().getUserNeoLimitsFromApi();
    assertEquals(userNeoLimitsFromDb, expectedResult);
    assertEquals(userNeoLimitsFromApi, userNeoLimitsFromDb);
    assertEquals(userNeoLimitsFromWeb, userNeoLimitsFromApi);
  }

  @Test  (priority = 34)
  public void checkSetNeoValueAtUserLimits_7() throws SQLException, InterruptedException {
    System.out.println("====checkSetNeoValueAtUserLimits_7===");
    System.out.println("проверяем что в НЕО записали значение больше значения 10000000000," +
            " сейчас сервер возвращает 500 ошибку если значение больше 10000000000\n");
    // подготовка теста
    int userIdMax = cm.getSqlUserHelper().getMaxUserId("select Max(id) from coin4coin_db.users");
    int idNeo = cm.getSqlUserHelper().getIdNeo("SELECT id FROM coin4coin_db.assets where code='neo'");
    int personalFeeActive = cm.getSqlUserHelper()
            .getPersonalFeeActiveFromDb(String.format("select personal_fee_active\n" +
                    "from coin4coin_db.users \n" +
                    "where id=%s", userIdMax));
    // подготовка теста, установка personal_fee_active=1
    if(personalFeeActive == 0) {
      cm.getSqlUserHelper().setIntValue(String.format("update coin4coin_db.users " +
              "set personal_fee_active=1 where id=%s;", userIdMax));
    }
    // тело теста
    app.getSessionHelper().getBaseAdminPage(baseAdminPage);
    app.goTo().usersPage();
    Thread.sleep(9000);
    app.goTo().userInfo();
    Thread.sleep(4000);
    app.goTo().userLimits();
    Thread.sleep(2000);
    UserLimits userNeoLimitsFromWeb = app.getUserHelper()
            .setUserNeoLimits(
                    "123456789012",
                    "123456789012",
                    "123456789012",
                    "123456789012"
            );
    Thread.sleep(5000);
    app.press().saveButtonAtUserLimits();
    Thread.sleep(5000);
    UserLimits userNeoLimitsFromDb = cm.getSqlUserHelper()
            .getUserNeoLimitsFromDb(String.format("SELECT UA.code, UA.name, UF.order_min, UF.exchange, UF.withdraw_min, UF.withdraw_max \n" +
                    "FROM coin4coin_db.user_fees UF\n" +
                    "join coin4coin_db.assets UA on UF.asset_id=UA.id where UF.user_id=%s and UF.asset_id=%s;", userIdMax,idNeo));
    assertEquals(userNeoLimitsFromDb, userNeoLimitsFromWeb);
  }

  @Test  (priority = 35)
  public void checkSetNeoValueAtUserLimits_8() throws SQLException, InterruptedException, IOException {
    System.out.println(" ===checkSetNeoValueAtUserLimits_8===");
    System.out.println("  //проверяем что в НЕО передется максимальное кол символов+1, но последний символ не записывается,\n" +
            "  // так как есть ограничение на Фронтенде\n");
    // подготовка теста
    int userIdMax = cm.getSqlUserHelper().getMaxUserId("select Max(id) from coin4coin_db.users");
    int idNeo = cm.getSqlUserHelper().getIdNeo("SELECT id FROM coin4coin_db.assets where code='neo'");
    int personalFeeActive = cm.getSqlUserHelper()
            .getPersonalFeeActiveFromDb(String.format("select personal_fee_active\n" +
                    "from coin4coin_db.users \n" +
                    "where id=%s", userIdMax));
    // подготовка теста, установка personal_fee_active=1
    if(personalFeeActive == 0) {
      cm.getSqlUserHelper().setIntValue(String.format("update coin4coin_db.users " +
              "set personal_fee_active=1 where id=%s;", userIdMax));
    }
    UserLimits expectedResult = new UserLimits()
            .withName("Neo")
            .withOrder_min(0.0000000001)
            .withExchange(0.0000000001)
            .withWithdraw_min(1)
            .withWithdraw_max(1);
    // тело теста
    app.getSessionHelper().getBaseAdminPage(baseAdminPage);
    app.goTo().usersPage();
    Thread.sleep(9000);
    app.goTo().userInfo();
    Thread.sleep(4000);
    app.goTo().userLimits();
    Thread.sleep(2000);
    UserLimits userNeoLimitsFromWeb = app.getUserHelper()
            .setUserNeoLimits(
                    "0.00000000012",
                    "0.00000000012",
                    "1",
                    "1");
    Thread.sleep(5000);
    app.press().saveButtonAtUserLimits();
    Thread.sleep(5000);
    UserLimits userNeoLimitsFromDb = cm.getSqlUserHelper()
            .getUserNeoLimitsFromDb(String.format("SELECT UA.code, UA.name, UF.order_min, UF.exchange, UF.withdraw_min, UF.withdraw_max \n" +
                    "FROM coin4coin_db.user_fees UF\n" +
                    "join coin4coin_db.assets UA on UF.asset_id=UA.id where UF.user_id=%s and UF.asset_id=%s;",userIdMax, idNeo));
    UserLimits userNeoLimitsFromApi = am.getApiUserHelper().getUserNeoLimitsFromApi();
    assertEquals(userNeoLimitsFromDb, expectedResult);
    assertEquals(userNeoLimitsFromApi, userNeoLimitsFromDb);
    assertEquals(userNeoLimitsFromWeb, userNeoLimitsFromApi);
  }

  @Test  (priority = 36)
  public void checkSetNeoValueAtUserLimits_9() throws SQLException, InterruptedException, IOException {
    System.out.println("===checkSetNeoValueAtUserLimits_9===\n");
    System.out.println("Проверяем что в НЕО записали значения," +
            " сразу же стерли, в форме пустота, в запросе передается 0\n");
    // подготовка теста
    UserLimits expectedResult = new UserLimits()
            .withName("Neo")
            .withOrder_min(0)
            .withExchange(0)
            .withWithdraw_min(0)
            .withWithdraw_max(0);
    int userIdMax = getUserIdMax();//cm.getSqlUserHelper().getMaxUserId("select Max(id) from coin4coin_db.users");
    System.out.println("useridMAx " + userIdMax);
    int idNeo = cm.getSqlUserHelper().getIdNeo("SELECT id FROM coin4coin_db.assets where code='neo'");
    int personalFeeActive = cm.getSqlUserHelper()
            .getPersonalFeeActiveFromDb(String.format("select personal_fee_active\n" +
                                                      "from coin4coin_db.users \n" +
                                                      "where id=%s", userIdMax));
    // подготовка теста, установка personal_fee_active=1
    if(personalFeeActive == 0) {
      cm.getSqlUserHelper().setIntValue(String.format("update coin4coin_db.users " +
              "set personal_fee_active=1 where id=%s;", userIdMax));
    }
    // Тело теста
    //app.getSessionHelper().getBaseAdminPage(baseAdminPage);
    app.goTo().usersPage();
    Thread.sleep(9000);
    app.goTo().userInfo();
    Thread.sleep(4000);
    app.goTo().userLimits();
    Thread.sleep(2000);
    app.getUserHelper().setUserNeoEmptyValueLimits(
                                  "1",
                                  "1",
                                  "1",
                                  "1");
    Thread.sleep(1000);
    app.press().saveButtonAtUserLimits();
    Thread.sleep(5000);
    UserLimits userNeoLimitsFromDb = cm.getSqlUserHelper()
            .getUserNeoLimitsFromDb(String.format("SELECT UA.code, UA.name, UF.order_min, UF.exchange, UF.withdraw_min, UF.withdraw_max \n" +
                    "FROM coin4coin_db.user_fees UF\n" +
                    "join coin4coin_db.assets UA on UF.asset_id=UA.id " +
                    "where UF.user_id=%s and UF.asset_id=%s;", userIdMax, idNeo));
    UserLimits userNeoLimitsFromWebAfter = app.getUserHelper().getUserNeoLimitsFromWeb();
    UserLimits userNeoLimitsFromApi = am.getApiUserHelper().getUserNeoLimitsFromApi();
    System.out.println("expectedResult  " + expectedResult);

    assertEquals(userNeoLimitsFromDb, expectedResult);
    assertEquals(userNeoLimitsFromApi, userNeoLimitsFromDb);
    assertEquals(userNeoLimitsFromWebAfter, userNeoLimitsFromApi);
  }
}
