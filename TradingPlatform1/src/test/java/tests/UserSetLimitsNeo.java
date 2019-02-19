package tests;

import model.UserLimits;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.IOException;
import java.sql.SQLException;
import static org.testng.Assert.assertEquals;

public class UserSetLimitsNeo extends TestBase {

  @BeforeMethod
  public void beforeUserSetLimitsNeo() throws SQLException {
    // подготовка теста, установка personal_fee_active=1
    if(personalFeeActive == 0) {
      cm.getSqlUserHelper().setIntValue(String.format("update coin4coin_db.users " +
              "set personal_fee_active=1 where id=%s;", userIdMax));
    }
  }

  @Test  (priority = 30)
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
                    "10000000000");
    Thread.sleep(5000);
    app.press().saveButtonAtUserLimits();
    Thread.sleep(5000);
    UserLimits userNeoLimitsFromDb = cm.getSqlUserHelper()
            .getUserNeoLimitsFromDb(String.format("SELECT UA.code, UA.name, UF.order_min, UF.exchange, UF.withdraw_min, UF.withdraw_max \n" +
                    "FROM coin4coin_db.user_fees UF\n" +
                    "join coin4coin_db.assets UA on UF.asset_id=UA.id where UF.user_id=%s and UF.asset_id=%s;",userIdMax,idNeo));
    UserLimits userNeoLimitsFromApi = am.getApiUserHelper().getUserNeoLimitsFromApi();
    assertEquals(userNeoLimitsFromWeb, expectedResult);
    assertEquals(userNeoLimitsFromDb, userNeoLimitsFromWeb);
    assertEquals(userNeoLimitsFromApi,userNeoLimitsFromDb);
  }

  @Test  (priority = 31)
  public void checkSetNeoValueAtUserLimits_4() throws SQLException, InterruptedException, IOException {
    System.out.println("===checkSetNeoValueAtUserLimits_4===");
    System.out.println("  //проверяем что в НЕО НЕ записываюься символы кроме цифр и единой точки,\n" +
            "  // только цифры и цифра с точкой передаются и сохраняются в БД");
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
                    "abcd  -=+0.0001");
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
                    "0");
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

    // Тело теста
    app.getSessionHelper().getBaseAdminPage(baseAdminPage);
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
    assertEquals(userNeoLimitsFromDb, expectedResult);
    assertEquals(userNeoLimitsFromApi, userNeoLimitsFromDb);
    assertEquals(userNeoLimitsFromWebAfter, userNeoLimitsFromApi);
  }
}
