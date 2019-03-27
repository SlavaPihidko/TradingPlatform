package tests;

import model.UserLimits;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.sql.SQLException;
import static org.testng.Assert.assertEquals;

public class SetUserNeoLimits extends TestBase {

  @BeforeMethod
  public void setPersonalFeeActiveForUser() throws SQLException {
    // подготовка теста, установка personal_fee_active=1
    if(personalFeeActive == 0) {
      cm.getSqlUserHelper().setIntValue(String.format("update coin4coin_db.users " +
              "set personal_fee_active=1 where id=%s;", userIdMax));
    }
  }

  @Test  (priority = 1)
  public void checkSetNeoValueAtUserLimits_1() throws SQLException, InterruptedException, IOException {
    System.out.println("===checkSetNeoValueAtUserLimits_1====");
    System.out.println("//проверяем что в НЕО вообщем записываються значения, передаются и сохраняются в БД");
    UserLimits expectedResult = new UserLimits()
            .withName("Neo")
            .withOrder_min(1)
            .withExchange(2)
            .withWithdraw_min(3.1)
            .withWithdraw_max(4.22);
    app.getSessionHelper().getBaseAdminPage(baseAdminPage);
    app.goTo().usersPage();
    // проверяем что есть спиннер в usersPage и пропадает, когда данные загружены
    app.getSessionHelper().isElementPresent(By.cssSelector("tr.table_row.with_spiner img.spiner"));
    WebElement spinnerAtUserPage = app.getSessionHelper().isElementPresent2(By.cssSelector("tr.table_row.with_spiner img.spiner"));
    app.getSessionHelper().isElementNotPresent(spinnerAtUserPage);
    app.goTo().userInfo();
    // Проверка спинера в userInfo и пропадает, когда данные загружены
    app.getSessionHelper().isElementPresent(By.cssSelector("div.acc_spiner"));
    WebElement spinnerAtUserInfo = app.getSessionHelper().isElementPresent2(By.cssSelector("div.acc_spiner"));
    app.getSessionHelper().isElementNotPresent(spinnerAtUserInfo);
    app.goTo().userLimits();
    app.getSessionHelper().isElementPresent(By.cssSelector("tr.table_row th:nth-child(1)"));
    UserLimits userNeoLimitsFromWeb = app.getUserHelper()
            .setUserNeoLimits(
                    "1",
                    "2",
                    "3.1",
                    "4.22");
    app.press().saveButtonAtUserLimits();
    // Ждем появление и пропажу спинера на странице userLimits
    app.getSessionHelper().isElementPresent(By.cssSelector("div.acc_spiner"));
    app.getSessionHelper().isElementNotPresent(spinnerAtUserInfo);
    UserLimits userNeoLimitsFromDb = cm.getSqlUserHelper()
            .getUserNeoLimitsFromDb(String.format("SELECT UA.code, UA.name, UF.order_min, UF.exchange, UF.withdraw_min, UF.withdraw_max \n" +
                    "FROM coin4coin_db.user_fees UF\n" +
                    "join coin4coin_db.assets UA on UF.asset_id=UA.id where UF.user_id=%s and UF.asset_id=%s;",userIdMax,idNeo));
    UserLimits userNeoLimitsFromApi = am.getApiUserHelper().getUserNeoLimitsFromApi();

    SoftAssert s = new SoftAssert();
    s.assertEquals(userNeoLimitsFromWeb, expectedResult);
    s.assertEquals(userNeoLimitsFromDb, userNeoLimitsFromWeb);
    s.assertEquals(userNeoLimitsFromApi,userNeoLimitsFromDb);
  }

  @Test  (priority = 2)
  public void checkSetNeoValueAtUserLimits_2() throws SQLException, InterruptedException, IOException {
    System.out.println("===checkSetNeoValueAtUserLimits_2====");
    System.out.println("//проверяем что в НЕО записываюься Минимальные значения, передаются и сохраняются в БД");
    UserLimits expectedResult = new UserLimits()
            .withName("Neo")
            .withOrder_min(0.0000000001)
            .withExchange(0.0000000001)
            .withWithdraw_min(1)
            .withWithdraw_max(1);
    app.getSessionHelper().getBaseAdminPage(baseAdminPage);
    app.goTo().usersPage();
    // проверяем что есть спиннер в usersPage и пропадает, когда данные загружены
    app.getSessionHelper().isElementPresent(By.cssSelector("tr.table_row.with_spiner img.spiner"));
    WebElement spinnerAtUserPage = app.getSessionHelper().isElementPresent2(By.cssSelector("tr.table_row.with_spiner img.spiner"));
    app.getSessionHelper().isElementNotPresent(spinnerAtUserPage);
    app.goTo().userInfo();
    // Проверка спинера в userInfo и пропадает, когда данные загружены
    app.getSessionHelper().isElementPresent(By.cssSelector("div.acc_spiner"));
    WebElement spinnerAtUserInfo = app.getSessionHelper().isElementPresent2(By.cssSelector("div.acc_spiner"));
    app.getSessionHelper().isElementNotPresent(spinnerAtUserInfo);
    app.goTo().userLimits();
    app.getSessionHelper().isElementPresent(By.cssSelector("tr.table_row th:nth-child(1)"));
    UserLimits userNeoLimitsFromWeb = app.getUserHelper()
            .setUserNeoLimits(
                    "0.0000000001",
                    "0.0000000001",
                    "1",
                    "1");
    app.press().saveButtonAtUserLimits();
    // Ждем появление и пропажу спинера на странице userLimits
    app.getSessionHelper().isElementPresent(By.cssSelector("div.acc_spiner"));
    app.getSessionHelper().isElementNotPresent(spinnerAtUserInfo);
    UserLimits userNeoLimitsFromDb = cm.getSqlUserHelper()
            .getUserNeoLimitsFromDb(String.format("SELECT UA.code, UA.name, UF.order_min, UF.exchange, UF.withdraw_min, UF.withdraw_max \n" +
                    "FROM coin4coin_db.user_fees UF\n" +
                    "join coin4coin_db.assets UA on UF.asset_id=UA.id where UF.user_id=%s and UF.asset_id=%s;",userIdMax,idNeo));
    UserLimits userNeoLimitsFromApi = am.getApiUserHelper().getUserNeoLimitsFromApi();

    SoftAssert s = new SoftAssert();
    s.assertEquals(userNeoLimitsFromWeb, expectedResult);
    s.assertEquals(userNeoLimitsFromDb, userNeoLimitsFromWeb);
    s.assertEquals(userNeoLimitsFromApi,userNeoLimitsFromDb);
  }

  @Test  (priority = 3)
  public void checkSetNeoValueAtUserLimits_3() throws SQLException, InterruptedException, IOException {
    System.out.println("===checkSetNeoValueAtUserLimits_3====");
    System.out.println("//проверяем что в НЕО записываюься Максимальные значения, передаются и сохраняются в БД");
    UserLimits expectedResult = new UserLimits()
            .withName("Neo")
            .withOrder_min(10000000000.0)
            .withExchange(10000000000.0)
            .withWithdraw_min(10000000000.0)
            .withWithdraw_max(10000000000.0);
    app.getSessionHelper().getBaseAdminPage(baseAdminPage);
    app.goTo().usersPage();
    // проверяем что есть спиннер в usersPage и пропадает, когда данные загружены
    app.getSessionHelper().isElementPresent(By.cssSelector("tr.table_row.with_spiner img.spiner"));
    WebElement spinnerAtUserPage = app.getSessionHelper().isElementPresent2(By.cssSelector("tr.table_row.with_spiner img.spiner"));
    app.getSessionHelper().isElementNotPresent(spinnerAtUserPage);
    app.goTo().userInfo();
    // Проверка спинера в userInfo и пропадает, когда данные загружены
    app.getSessionHelper().isElementPresent(By.cssSelector("div.acc_spiner"));
    WebElement spinnerAtUserInfo = app.getSessionHelper().isElementPresent2(By.cssSelector("div.acc_spiner"));
    app.getSessionHelper().isElementNotPresent(spinnerAtUserInfo);
    app.goTo().userLimits();
    app.getSessionHelper().isElementPresent(By.cssSelector("tr.table_row th:nth-child(1)"));
    UserLimits userNeoLimitsFromWeb = app.getUserHelper()
            .setUserNeoLimits(
                    "10000000000",
                    "10000000000",
                    "10000000000",
                    "10000000000");
    app.press().saveButtonAtUserLimits();
    // Ждем появление и пропажу спинера на странице userLimits
    app.getSessionHelper().isElementPresent(By.cssSelector("div.acc_spiner"));
    app.getSessionHelper().isElementNotPresent(spinnerAtUserInfo);
    UserLimits userNeoLimitsFromDb = cm.getSqlUserHelper()
            .getUserNeoLimitsFromDb(String.format("SELECT UA.code, UA.name, UF.order_min, UF.exchange, UF.withdraw_min, UF.withdraw_max \n" +
                    "FROM coin4coin_db.user_fees UF\n" +
                    "join coin4coin_db.assets UA on UF.asset_id=UA.id where UF.user_id=%s and UF.asset_id=%s;",userIdMax,idNeo));
    UserLimits userNeoLimitsFromApi = am.getApiUserHelper().getUserNeoLimitsFromApi();

    SoftAssert s = new SoftAssert();
    s.assertEquals(userNeoLimitsFromWeb, expectedResult);
    s.assertEquals(userNeoLimitsFromDb, userNeoLimitsFromWeb);
    s.assertEquals(userNeoLimitsFromApi,userNeoLimitsFromDb);
  }

  @Test  (priority = 4)
  public void checkSetNeoValueAtUserLimits_4() throws SQLException, InterruptedException, IOException {
    System.out.println("===checkSetNeoValueAtUserLimits_4===");
    System.out.println("  //проверяем что в НЕО НЕ записываюься символы кроме цифр и единой точки,\n" +
            "  // только цифры и цифра с точкой передаются и сохраняются в БД");
    UserLimits expectedResult = new UserLimits()
            .withName("Neo")
            .withOrder_min(0)
            .withExchange(0.0001)
            .withWithdraw_min(0)
            .withWithdraw_max(0.0001);
    // тело теста
    app.getSessionHelper().getBaseAdminPage(baseAdminPage);
    app.goTo().usersPage();
    // проверяем что есть спиннер в usersPage и пропадает, когда данные загружены
    app.getSessionHelper().isElementPresent(By.cssSelector("tr.table_row.with_spiner img.spiner"));
    WebElement spinnerAtUserPage = app.getSessionHelper().isElementPresent2(By.cssSelector("tr.table_row.with_spiner img.spiner"));
    app.getSessionHelper().isElementNotPresent(spinnerAtUserPage);
    app.goTo().userInfo();
    // Проверка спинера в userInfo и пропадает, когда данные загружены
    app.getSessionHelper().isElementPresent(By.cssSelector("div.acc_spiner"));
    WebElement spinnerAtUserInfo = app.getSessionHelper().isElementPresent2(By.cssSelector("div.acc_spiner"));
    app.getSessionHelper().isElementNotPresent(spinnerAtUserInfo);
    app.goTo().userLimits();
    app.getSessionHelper().isElementPresent(By.cssSelector("tr.table_row th:nth-child(1)"));
    UserLimits userNeoLimitsFromWebBeforeSaving = app.getUserHelper()
            .setUserNeoLimits(
                    "0.",
                    "abcd!@#$%^&*()0..0001",
                    ".0001",
                    "abcd  -=+0.0001");
    app.press().saveButtonAtUserLimits();
    // Ждем появление и пропажу спинера на странице userLimits
    app.getSessionHelper().isElementPresent(By.cssSelector("div.acc_spiner"));
    app.getSessionHelper().isElementNotPresent(spinnerAtUserInfo);
    UserLimits userNeoLimitsFromDb = cm.getSqlUserHelper()
            .getUserNeoLimitsFromDb(String.format("SELECT UA.code, UA.name, UF.order_min, UF.exchange, UF.withdraw_min, UF.withdraw_max \n" +
                    "FROM coin4coin_db.user_fees UF\n" +
                    "join coin4coin_db.assets UA on UF.asset_id=UA.id where UF.user_id=%s and UF.asset_id=%s;",userIdMax,idNeo));
    UserLimits userNeoLimitsFromWebAfter = app.getUserHelper().getUserNeoLimitsFromWeb();
    UserLimits userNeoLimitsFromApi = am.getApiUserHelper().getUserNeoLimitsFromApi();

    SoftAssert s = new SoftAssert();
    s.assertEquals(userNeoLimitsFromDb, expectedResult);
    s.assertEquals(userNeoLimitsFromWebBeforeSaving, expectedResult);
    s.assertEquals(userNeoLimitsFromDb, userNeoLimitsFromWebBeforeSaving);
    s.assertEquals(userNeoLimitsFromApi, userNeoLimitsFromDb);
    s.assertEquals(userNeoLimitsFromWebAfter, userNeoLimitsFromApi);
  }

  @Test (priority = 5)
  public void checkSetNeoValueAtUserLimits_5() throws SQLException, InterruptedException, IOException {
    System.out.println("===checkSetNeoValueAtUserLimits_5()===");
    System.out.println("  //проверяем что в НЕО кликаем по форме,\n" +
            "  // и это никак не влияет на сохранение.(отправляется пустой массив)");
    // тело теста
    app.getSessionHelper().getBaseAdminPage(baseAdminPage);
    app.goTo().usersPage();
    // проверяем что есть спиннер в usersPage и пропадает, когда данные загружены
    app.getSessionHelper().isElementPresent(By.cssSelector("tr.table_row.with_spiner img.spiner"));
    WebElement spinnerAtUserPage = app.getSessionHelper().isElementPresent2(By.cssSelector("tr.table_row.with_spiner img.spiner"));
    app.getSessionHelper().isElementNotPresent(spinnerAtUserPage);
    app.goTo().userInfo();
    // Проверка спинера в userInfo и пропадает, когда данные загружены
    app.getSessionHelper().isElementPresent(By.cssSelector("div.acc_spiner"));
    WebElement spinnerAtUserInfo = app.getSessionHelper().isElementPresent2(By.cssSelector("div.acc_spiner"));
    app.getSessionHelper().isElementNotPresent(spinnerAtUserInfo);
    app.goTo().userLimits();
    app.getSessionHelper().isElementPresent(By.cssSelector("tr.table_row th:nth-child(1)"));
    UserLimits userNeoLimitsFromWebBefore = app.getUserHelper().getUserNeoLimitsFromWeb();
    // здесь просто прокликали поля, значения в плейсхолдере, значения атрибут value не имеет
    app.getUserHelper().setUserNeoEmptyLimits();
    app.press().saveButtonAtUserLimits();
    // Ждем появление и пропажу спинера на странице userLimits
    app.getSessionHelper().isElementPresent(By.cssSelector("div.acc_spiner"));
    app.getSessionHelper().isElementNotPresent(spinnerAtUserInfo);
    UserLimits userNeoLimitsFromDb = cm.getSqlUserHelper()
            .getUserNeoLimitsFromDb(String.format("SELECT UA.code, UA.name, UF.order_min, UF.exchange, UF.withdraw_min, UF.withdraw_max \n" +
                    "FROM coin4coin_db.user_fees UF\n" +
                    "join coin4coin_db.assets UA on UF.asset_id=UA.id where UF.user_id=%s and UF.asset_id=%s;",userIdMax,idNeo));
    UserLimits userNeoLimitsFromWebAfter = app.getUserHelper().getUserNeoLimitsFromWeb();
    UserLimits userNeoLimitsFromApi = am.getApiUserHelper().getUserNeoLimitsFromApi();

    SoftAssert s = new SoftAssert();
    s.assertEquals(userNeoLimitsFromWebBefore, userNeoLimitsFromWebAfter);
    s.assertEquals(userNeoLimitsFromWebAfter, userNeoLimitsFromApi);
    s.assertEquals(userNeoLimitsFromApi, userNeoLimitsFromDb);
  }

  @Test  (priority = 6)
  public void checkSetNeoValueAtUserLimits_6() throws SQLException, InterruptedException, IOException {
    System.out.println("===checkSetNeoValueAtUserLimits_6===");
    System.out.println(" проверяем что в НЕО записали значение 0\n");
    UserLimits expectedResult = new UserLimits()
            .withName("Neo")
            .withOrder_min(0)
            .withExchange(0)
            .withWithdraw_min(0)
            .withWithdraw_max(0);
    // тело теста
    app.getSessionHelper().getBaseAdminPage(baseAdminPage);
    app.goTo().usersPage();
    // проверяем что есть спиннер в usersPage и пропадает, когда данные загружены
    app.getSessionHelper().isElementPresent(By.cssSelector("tr.table_row.with_spiner img.spiner"));
    WebElement spinnerAtUserPage = app.getSessionHelper().isElementPresent2(By.cssSelector("tr.table_row.with_spiner img.spiner"));
    app.getSessionHelper().isElementNotPresent(spinnerAtUserPage);
    app.goTo().userInfo();
    // Проверка спинера в userInfo и пропадает, когда данные загружены
    app.getSessionHelper().isElementPresent(By.cssSelector("div.acc_spiner"));
    WebElement spinnerAtUserInfo = app.getSessionHelper().isElementPresent2(By.cssSelector("div.acc_spiner"));
    app.getSessionHelper().isElementNotPresent(spinnerAtUserInfo);
    app.goTo().userLimits();
    app.getSessionHelper().isElementPresent(By.cssSelector("tr.table_row th:nth-child(1)"));
    UserLimits userNeoLimitsFromWeb = app.getUserHelper()
            .setUserNeoLimits(
                    "0",
                    "0",
                    "0",
                    "0");
    app.press().saveButtonAtUserLimits();
    // Ждем появление и пропажу спинера на странице userLimits
    app.getSessionHelper().isElementPresent(By.cssSelector("div.acc_spiner"));
    app.getSessionHelper().isElementNotPresent(spinnerAtUserInfo);
    UserLimits userNeoLimitsFromDb = cm.getSqlUserHelper()
            .getUserNeoLimitsFromDb(String.format("SELECT UA.code, UA.name, UF.order_min, UF.exchange, UF.withdraw_min, UF.withdraw_max \n" +
                    "FROM coin4coin_db.user_fees UF\n" +
                    "join coin4coin_db.assets UA on UF.asset_id=UA.id where UF.user_id=%s and UF.asset_id=%s;",userIdMax,idNeo));
    UserLimits userNeoLimitsFromApi = am.getApiUserHelper().getUserNeoLimitsFromApi();

    SoftAssert s = new SoftAssert();
    s.assertEquals(userNeoLimitsFromWeb, expectedResult);
    s.assertEquals(userNeoLimitsFromDb, userNeoLimitsFromWeb);
    s.assertEquals(userNeoLimitsFromApi, userNeoLimitsFromDb);
  }

  @Test  (priority = 7)
  public void checkSetNeoValueAtUserLimits_7() throws SQLException, InterruptedException {
    System.out.println("====checkSetNeoValueAtUserLimits_7===");
    System.out.println("проверяем что в НЕО записали значение больше значения 10000000000," +
            " сейчас сервер возвращает 500 ошибку если значение больше 10000000000\n");
    // тело теста
    app.getSessionHelper().getBaseAdminPage(baseAdminPage);
    app.goTo().usersPage();
    // проверяем что есть спиннер в usersPage и пропадает, когда данные загружены
    app.getSessionHelper().isElementPresent(By.cssSelector("tr.table_row.with_spiner img.spiner"));
    WebElement spinnerAtUserPage = app.getSessionHelper().isElementPresent2(By.cssSelector("tr.table_row.with_spiner img.spiner"));
    app.getSessionHelper().isElementNotPresent(spinnerAtUserPage);
    app.goTo().userInfo();
    // Проверка спинера в userInfo и пропадает, когда данные загружены
    app.getSessionHelper().isElementPresent(By.cssSelector("div.acc_spiner"));
    WebElement spinnerAtUserInfo = app.getSessionHelper().isElementPresent2(By.cssSelector("div.acc_spiner"));
    app.getSessionHelper().isElementNotPresent(spinnerAtUserInfo);
    app.goTo().userLimits();
    app.getSessionHelper().isElementPresent(By.cssSelector("tr.table_row th:nth-child(1)"));
    UserLimits userNeoLimitsFromWeb = app.getUserHelper()
            .setUserNeoLimits(
                    "123456789012",
                    "123456789012",
                    "123456789012",
                    "123456789012");
    app.press().saveButtonAtUserLimits();
    // Ждем появление и пропажу спинера на странице userLimits
    app.getSessionHelper().isElementPresent(By.cssSelector("div.acc_spiner"));
    app.getSessionHelper().isElementNotPresent(spinnerAtUserInfo);
    UserLimits userNeoLimitsFromDb = cm.getSqlUserHelper()
            .getUserNeoLimitsFromDb(String.format("SELECT UA.code, UA.name, UF.order_min, UF.exchange, UF.withdraw_min, UF.withdraw_max \n" +
                    "FROM coin4coin_db.user_fees UF\n" +
                    "join coin4coin_db.assets UA on UF.asset_id=UA.id where UF.user_id=%s and UF.asset_id=%s;", userIdMax,idNeo));

    SoftAssert s = new SoftAssert();
    s.assertEquals(userNeoLimitsFromDb, userNeoLimitsFromWeb);
  }

  @Test  (priority = 8)
  public void checkSetNeoValueAtUserLimits_8() throws SQLException, InterruptedException, IOException {
    System.out.println(" ===checkSetNeoValueAtUserLimits_8===");
    System.out.println("  //проверяем что в НЕО передется максимальное кол символов+1, но последний символ не записывается,\n" +
            "  // так как есть ограничение на Фронтенде\n");
    // подготовка теста

    UserLimits expectedResult = new UserLimits()
            .withName("Neo")
            .withOrder_min(0.0000000001)
            .withExchange(0.0000000001)
            .withWithdraw_min(1)
            .withWithdraw_max(1);
    // тело теста
    app.getSessionHelper().getBaseAdminPage(baseAdminPage);
    app.goTo().usersPage();
    // проверяем что есть спиннер в usersPage и пропадает, когда данные загружены
    app.getSessionHelper().isElementPresent(By.cssSelector("tr.table_row.with_spiner img.spiner"));
    WebElement spinnerAtUserPage = app.getSessionHelper().isElementPresent2(By.cssSelector("tr.table_row.with_spiner img.spiner"));
    app.getSessionHelper().isElementNotPresent(spinnerAtUserPage);
    app.goTo().userInfo();
    // Проверка спинера в userInfo и пропадает, когда данные загружены
    app.getSessionHelper().isElementPresent(By.cssSelector("div.acc_spiner"));
    WebElement spinnerAtUserInfo = app.getSessionHelper().isElementPresent2(By.cssSelector("div.acc_spiner"));
    app.getSessionHelper().isElementNotPresent(spinnerAtUserInfo);
    app.goTo().userLimits();
    app.getSessionHelper().isElementPresent(By.cssSelector("tr.table_row th:nth-child(1)"));
    UserLimits userNeoLimitsFromWeb = app.getUserHelper()
            .setUserNeoLimits(
                    "0.00000000012",
                    "0.00000000012",
                    "1",
                    "1");
    app.press().saveButtonAtUserLimits();
    // Ждем появление и пропажу спинера на странице userLimits
    app.getSessionHelper().isElementPresent(By.cssSelector("div.acc_spiner"));
    app.getSessionHelper().isElementNotPresent(spinnerAtUserInfo);
    UserLimits userNeoLimitsFromDb = cm.getSqlUserHelper()
            .getUserNeoLimitsFromDb(String.format("SELECT UA.code, UA.name, UF.order_min, UF.exchange, UF.withdraw_min, UF.withdraw_max \n" +
                    "FROM coin4coin_db.user_fees UF\n" +
                    "join coin4coin_db.assets UA on UF.asset_id=UA.id where UF.user_id=%s and UF.asset_id=%s;",userIdMax, idNeo));
    UserLimits userNeoLimitsFromApi = am.getApiUserHelper().getUserNeoLimitsFromApi();

    SoftAssert s = new SoftAssert();
    s.assertEquals(userNeoLimitsFromWeb, expectedResult);
    s.assertEquals(userNeoLimitsFromDb, userNeoLimitsFromWeb);
    s.assertEquals(userNeoLimitsFromApi, userNeoLimitsFromDb);
  }

  @Test  (priority = 9)
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

    // Тело теста
    app.getSessionHelper().getBaseAdminPage(baseAdminPage);
    app.goTo().usersPage();
    // проверяем что есть спиннер в usersPage и пропадает, когда данные загружены
    app.getSessionHelper().isElementPresent(By.cssSelector("tr.table_row.with_spiner img.spiner"));
    WebElement spinnerAtUserPage = app.getSessionHelper().isElementPresent2(By.cssSelector("tr.table_row.with_spiner img.spiner"));
    app.getSessionHelper().isElementNotPresent(spinnerAtUserPage);
    app.goTo().userInfo();
    // Проверка спинера в userInfo и пропадает, когда данные загружены
    app.getSessionHelper().isElementPresent(By.cssSelector("div.acc_spiner"));
    WebElement spinnerAtUserInfo = app.getSessionHelper().isElementPresent2(By.cssSelector("div.acc_spiner"));
    app.getSessionHelper().isElementNotPresent(spinnerAtUserInfo);
    app.goTo().userLimits();
    app.getSessionHelper().isElementPresent(By.cssSelector("tr.table_row th:nth-child(1)"));
    app.getUserHelper().setUserNeoEmptyValueLimits(
            "1",
            "1",
            "1",
            "1");
    app.press().saveButtonAtUserLimits();
    // Ждем появление и пропажу спинера на странице userLimits
    app.getSessionHelper().isElementPresent(By.cssSelector("div.acc_spiner"));
    app.getSessionHelper().isElementNotPresent(spinnerAtUserInfo);
    UserLimits userNeoLimitsFromDb = cm.getSqlUserHelper()
            .getUserNeoLimitsFromDb(String.format("SELECT UA.code, UA.name, UF.order_min, UF.exchange, UF.withdraw_min, UF.withdraw_max \n" +
                    "FROM coin4coin_db.user_fees UF\n" +
                    "join coin4coin_db.assets UA on UF.asset_id=UA.id " +
                    "where UF.user_id=%s and UF.asset_id=%s;", userIdMax, idNeo));
    UserLimits userNeoLimitsFromWebAfter = app.getUserHelper().getUserNeoLimitsFromWeb();
    UserLimits userNeoLimitsFromApi = am.getApiUserHelper().getUserNeoLimitsFromApi();

    SoftAssert s = new SoftAssert();
    s.assertEquals(userNeoLimitsFromDb, expectedResult);
    s.assertEquals(userNeoLimitsFromApi, userNeoLimitsFromDb);
    s.assertEquals(userNeoLimitsFromWebAfter, userNeoLimitsFromApi);
  }
}
