package tests;

import model.UserLimits;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.testng.Assert.assertEquals;

public class SetUserLimitsWithoutNeo extends TestBase {

  @BeforeMethod
  public void setPersonalFeeActiveForUser() throws SQLException {
    // подготовка теста, установка personal_fee_active=1
    if(personalFeeActive == 0) {
      cm.getSqlUserHelper().setIntValue(String.format("update coin4coin_db.users " +
              "set personal_fee_active=1 where id=%s;", userIdMax));
    }
  }


  @Test(priority = 22)
  public void checkSetUserLimitsWithoutNeo_4() throws IOException, SQLException, InterruptedException {
    System.out.println("===checkSetUserLimitsWithoutNeo_4===");
    System.out.println("  //проверяем что в Все ассеты кроме Нео НЕ записываюься символы кроме цифр и единой точки,\n" +
            "  // только цифры и цифра с точкой передаются и сохраняются в БД");
    Set<UserLimits> userLimitsWithoutNeoExpected = new HashSet<>();
    // этот список будет без НЕО
    List<String> listNameAssets =  cm.getSqlUserHelper()
            .getListNameAssetsFromDb("SELECT name FROM coin4coin_db.assets where active=1 and name!='Neo';;");
    for(int i=0; i< listNameAssets.size(); i++) {
      UserLimits expectedResult = new UserLimits()
              .withName(listNameAssets.get(i))
              .withOrder_min(0)
              .withExchange(0.0001)
              .withWithdraw_min(0)
              .withWithdraw_max(0.001);
      userLimitsWithoutNeoExpected.add(expectedResult);
    }
    System.out.println("userLimitsWithoutNeoExpected " + userLimitsWithoutNeoExpected);
    app.getSessionHelper().getBaseAdminPage(baseAdminPage);
    app.goTo().usersPage();
    app.getSessionHelper().isElementPresent(By.cssSelector(String.format("a[href='/users/%s']", userIdMax)));
    app.goTo().userInfo();
    app.getSessionHelper().isElementPresentTextToBe(By.cssSelector("div.information > p.user_info:nth-child(1)"),  String.format("ID: %s", userIdMax));
    app.goTo().userLimits();
    app.getSessionHelper().isElementPresent(By.cssSelector("tr.table_row th:nth-child(1)"));
    Set<UserLimits> userLimitsSetFromWebBefore = app.getUserHelper()
            .setUserLimitsWithoutNeo(
                    "0.",
                    "abcd!@#$%^&*()0..0001",
                    ".0001",
                    "abcd  -=+0.001");
    app.press().saveButtonAtUserLimits();
    Thread.sleep(5000);
    Set<UserLimits> userLimitsFromApi = am.getApiUserHelper().getUserLimitsWithoutNeoFromApi();
    Set<UserLimits> userLimitsSetFromWebAfter = app.getUserHelper().getUserLimitsWithoutNeoFromWeb();
    Set<UserLimits> userLimitsFromDb = cm.getSqlUserHelper()
            .getUserLimitsFromDbWithoutNeo(String.format("SELECT UA.code, UA.name, UF.order_min, UF.exchange, UF.withdraw_min, UF.withdraw_max \n" +
                    "FROM coin4coin_db.user_fees UF\n" +
                    "join coin4coin_db.assets UA on UF.asset_id=UA.id " +
                    "where UF.user_id=%s and UA.active=1 and UA.name!='Neo'", userIdMax));
    assertEquals(userLimitsFromDb, userLimitsWithoutNeoExpected);
    assertEquals(userLimitsFromApi,userLimitsFromDb);
    assertEquals(userLimitsSetFromWebAfter, userLimitsFromApi);
  }


  @Test(priority = 23)
  public void checkSetUserLimitsWithoutNeo_5() throws IOException, SQLException, InterruptedException {
    System.out.println("===checkSetUserLimitsWithoutNeo_5===");
    System.out.println("проверяем что в Все ассеты кроме  НЕО кликаем по форме,\n" +
            "  // и это никак не влияет на сохранение.(отправляется пустой массив)");
    app.getSessionHelper().getBaseAdminPage(baseAdminPage);
    app.goTo().usersPage();
    app.getSessionHelper().isElementPresent(By.cssSelector(String.format("a[href='/users/%s']", userIdMax)));
    app.goTo().userInfo();
    app.getSessionHelper().isElementPresentTextToBe(By.cssSelector("div.information > p.user_info:nth-child(1)"),  String.format("ID: %s", userIdMax));
    app.goTo().userLimits();
    app.getSessionHelper().isElementPresent(By.cssSelector("tr.table_row th:nth-child(1)"));
    Set<UserLimits> userLimitsSetFromWebBefore = app.getUserHelper().getUserLimitsWithoutNeoFromWeb();
    app.getUserHelper().setUserEmptyLimitsWithoutNeo();
    app.press().saveButtonAtUserLimits();
    Thread.sleep(5000);
    Set<UserLimits> userLimitsFromApi = am.getApiUserHelper().getUserLimitsWithoutNeoFromApi();
    Set<UserLimits> userLimitsSetFromWebAfter = app.getUserHelper().getUserLimitsWithoutNeoFromWeb();
    Set<UserLimits> userLimitsFromDb = cm.getSqlUserHelper()
            .getUserLimitsFromDbWithoutNeo(String.format("SELECT UA.code, UA.name, UF.order_min, UF.exchange, UF.withdraw_min, UF.withdraw_max \n" +
                    "FROM coin4coin_db.user_fees UF\n" +
                    "join coin4coin_db.assets UA on UF.asset_id=UA.id " +
                    "where UF.user_id=%s and UA.active=1 and UA.name!='Neo'", userIdMax));
    assertEquals(userLimitsFromDb, userLimitsSetFromWebBefore);
    assertEquals(userLimitsFromApi,userLimitsFromDb);
    assertEquals(userLimitsSetFromWebAfter, userLimitsFromApi);
  }


  @Test(priority = 24)
  public void checkSetUserLimitsWithoutNeo_6() throws IOException, SQLException, InterruptedException {
    System.out.println("===checkSetUserLimitsWithoutNeo_6===");
    System.out.println("//проверяем что в Все ассеты кроме Нео записали 0");
    Set<UserLimits> userLimitsWithoutNeoExpected = new HashSet<>();
    // этот список будет без НЕО
    List<String> listNameAssets =  cm.getSqlUserHelper()
            .getListNameAssetsFromDb("SELECT name FROM coin4coin_db.assets where active=1 and name!='Neo';;");
    for(int i=0; i< listNameAssets.size(); i++) {
      UserLimits expectedResult = new UserLimits()
              .withName(listNameAssets.get(i))
              .withOrder_min(0)
              .withExchange(0)
              .withWithdraw_min(0)
              .withWithdraw_max(0);
      userLimitsWithoutNeoExpected.add(expectedResult);
    }
    System.out.println("userLimitsWithoutNeoExpected " + userLimitsWithoutNeoExpected);
    app.getSessionHelper().getBaseAdminPage(baseAdminPage);
    app.goTo().usersPage();
    app.getSessionHelper().isElementPresent(By.cssSelector(String.format("a[href='/users/%s']", userIdMax)));
    app.goTo().userInfo();
    app.getSessionHelper().isElementPresentTextToBe(By.cssSelector("div.information > p.user_info:nth-child(1)"),  String.format("ID: %s", userIdMax));
    app.goTo().userLimits();
    app.getSessionHelper().isElementPresent(By.cssSelector("tr.table_row th:nth-child(1)"));
    Set<UserLimits> userLimitsSetFromWebBefore = app.getUserHelper()
            .setUserLimitsWithoutNeo(
                    "0",
                    "0",
                    "0",
                    "0");
    app.press().saveButtonAtUserLimits();
    Thread.sleep(5000);
    Set<UserLimits> userLimitsFromApi = am.getApiUserHelper().getUserLimitsWithoutNeoFromApi();
    Set<UserLimits> userLimitsSetFromWebAfter = app.getUserHelper().getUserLimitsWithoutNeoFromWeb();
    Set<UserLimits> userLimitsFromDb = cm.getSqlUserHelper()
            .getUserLimitsFromDbWithoutNeo(String.format("SELECT UA.code, UA.name, UF.order_min, UF.exchange, UF.withdraw_min, UF.withdraw_max \n" +
                    "FROM coin4coin_db.user_fees UF\n" +
                    "join coin4coin_db.assets UA on UF.asset_id=UA.id " +
                    "where UF.user_id=%s and UA.active=1 and UA.name!='Neo'", userIdMax));
    assertEquals(userLimitsFromDb, userLimitsWithoutNeoExpected);
    assertEquals(userLimitsFromApi,userLimitsFromDb);
    assertEquals(userLimitsSetFromWebAfter, userLimitsFromApi);
  }

  @Test(priority = 25)
  public void checkSetUserLimitsWithoutNeo_7() throws IOException, SQLException, InterruptedException {
    System.out.println("===checkSetUserLimitsWithoutNeo_7===");
    System.out.println("//проверяем что в Все ассеты кроме НЕО записали значение больше значения 10000000000,\n" +
            "  // сейчас сервер возвращает 500 ошибку если значение больше 10000000000");
    Set<UserLimits> userLimitsWithoutNeoExpected = new HashSet<>();
    // этот список будет без НЕО
    List<String> listNameAssets =  cm.getSqlUserHelper()
            .getListNameAssetsFromDb("SELECT name FROM coin4coin_db.assets where active=1 and name!='Neo';;");
    for(int i=0; i< listNameAssets.size(); i++) {
      UserLimits expectedResult = new UserLimits()
              .withName(listNameAssets.get(i))
              .withOrder_min(123456789012.0)
              .withExchange(123456789012.0)
              .withWithdraw_min(123456789012.0)
              .withWithdraw_max(123456789012.0);
      userLimitsWithoutNeoExpected.add(expectedResult);
    }
    System.out.println("userLimitsWithoutNeoExpected " + userLimitsWithoutNeoExpected);
    app.getSessionHelper().getBaseAdminPage(baseAdminPage);
    app.goTo().usersPage();
    app.getSessionHelper().isElementPresent(By.cssSelector(String.format("a[href='/users/%s']", userIdMax)));
    app.goTo().userInfo();
    app.getSessionHelper().isElementPresentTextToBe(By.cssSelector("div.information > p.user_info:nth-child(1)"),  String.format("ID: %s", userIdMax));
    app.goTo().userLimits();
    app.getSessionHelper().isElementPresent(By.cssSelector("tr.table_row th:nth-child(1)"));
    Set<UserLimits> userLimitsSetFromWebBefore = app.getUserHelper()
            .setUserLimitsWithoutNeo(
                    "123456789012",
                    "123456789012",
                    "123456789012",
                    "123456789012");
    app.press().saveButtonAtUserLimits();
    Thread.sleep(5000);
    Set<UserLimits> userLimitsFromApi = am.getApiUserHelper().getUserLimitsWithoutNeoFromApi();
    Set<UserLimits> userLimitsSetFromWebAfter = app.getUserHelper().getUserLimitsWithoutNeoFromWeb();
    Set<UserLimits> userLimitsFromDb = cm.getSqlUserHelper()
            .getUserLimitsFromDbWithoutNeo(String.format("SELECT UA.code, UA.name, UF.order_min, UF.exchange, UF.withdraw_min, UF.withdraw_max \n" +
                    "FROM coin4coin_db.user_fees UF\n" +
                    "join coin4coin_db.assets UA on UF.asset_id=UA.id " +
                    "where UF.user_id=%s and UA.active=1 and UA.name!='Neo'", userIdMax));
    assertEquals(userLimitsFromDb, userLimitsWithoutNeoExpected);
    assertEquals(userLimitsFromApi,userLimitsFromDb);
    assertEquals(userLimitsSetFromWebAfter, userLimitsFromApi);
  }


  @Test(priority = 26)
  public void checkSetUserLimitsWithoutNeo_8() throws IOException, SQLException, InterruptedException {
    System.out.println("===checkSetUserLimitsWithoutNeo_8===");
    System.out.println("  //проверяем что в Все ассеты кроме НЕО передется максимальное кол символов+1, " +
            "но последний символ не записывается,\n" +
            "  // так как есть ограничение на Фронтенде");
    Set<UserLimits> userLimitsWithoutNeoExpected = new HashSet<>();
    // этот список будет без НЕО
    List<String> listNameAssets =  cm.getSqlUserHelper()
            .getListNameAssetsFromDb("SELECT name FROM coin4coin_db.assets where active=1 and name!='Neo';;");
    for(int i=0; i< listNameAssets.size(); i++) {
      UserLimits expectedResult = new UserLimits()
              .withName(listNameAssets.get(i))
              .withOrder_min(0.0000000001)
              .withExchange(0.0000000001)
              .withWithdraw_min(0.0000000001)
              .withWithdraw_max(0.0000000001);
      userLimitsWithoutNeoExpected.add(expectedResult);
    }
    System.out.println("userLimitsWithoutNeoExpected " + userLimitsWithoutNeoExpected);
    app.getSessionHelper().getBaseAdminPage(baseAdminPage);
    app.goTo().usersPage();
    app.getSessionHelper().isElementPresent(By.cssSelector(String.format("a[href='/users/%s']", userIdMax)));
    app.goTo().userInfo();
    app.getSessionHelper().isElementPresentTextToBe(By.cssSelector("div.information > p.user_info:nth-child(1)"),  String.format("ID: %s", userIdMax));
    app.goTo().userLimits();
    app.getSessionHelper().isElementPresent(By.cssSelector("tr.table_row th:nth-child(1)"));
    Set<UserLimits> userLimitsSetFromWebBefore = app.getUserHelper()
            .setUserLimitsWithoutNeo(
                    "0.00000000012",
                    "0.00000000012",
                    "0.00000000012",
                    "0.00000000012");
    app.press().saveButtonAtUserLimits();
    Thread.sleep(5000);
    Set<UserLimits> userLimitsFromApi = am.getApiUserHelper().getUserLimitsWithoutNeoFromApi();
    Set<UserLimits> userLimitsSetFromWebAfter = app.getUserHelper().getUserLimitsWithoutNeoFromWeb();
    Set<UserLimits> userLimitsFromDb = cm.getSqlUserHelper()
            .getUserLimitsFromDbWithoutNeo(String.format("SELECT UA.code, UA.name, UF.order_min, UF.exchange, UF.withdraw_min, UF.withdraw_max \n" +
                    "FROM coin4coin_db.user_fees UF\n" +
                    "join coin4coin_db.assets UA on UF.asset_id=UA.id " +
                    "where UF.user_id=%s and UA.active=1 and UA.name!='Neo'", userIdMax));
    assertEquals(userLimitsFromDb, userLimitsWithoutNeoExpected);
    assertEquals(userLimitsFromApi,userLimitsFromDb);
    assertEquals(userLimitsSetFromWebAfter, userLimitsFromApi);
  }


  @Test(priority = 27)
  public void checkSetUserLimitsWithoutNeo_9() throws IOException, SQLException, InterruptedException {
    System.out.println("===checkSetUserLimitsWithoutNeo_9===");
    System.out.println("//проверяем что в Все ассеты кроме НЕО записали значения, " +
            "сразу же стерли, в форме путота, в запросе передается 0");
    Set<UserLimits> userLimitsWithoutNeoExpected = new HashSet<>();
    // этот список будет без НЕО
    List<String> listNameAssets =  cm.getSqlUserHelper()
            .getListNameAssetsFromDb("SELECT name FROM coin4coin_db.assets where active=1 and name!='Neo';;");
    for(int i=0; i< listNameAssets.size(); i++) {
      UserLimits expectedResult = new UserLimits()
              .withName(listNameAssets.get(i))
              .withOrder_min(0)
              .withExchange(0)
              .withWithdraw_min(0)
              .withWithdraw_max(0);
      userLimitsWithoutNeoExpected.add(expectedResult);
    }
    System.out.println("userLimitsWithoutNeoExpected " + userLimitsWithoutNeoExpected);
    app.getSessionHelper().getBaseAdminPage(baseAdminPage);
    app.goTo().usersPage();
    app.getSessionHelper().isElementPresent(By.cssSelector(String.format("a[href='/users/%s']", userIdMax)));
    app.goTo().userInfo();
    app.getSessionHelper().isElementPresentTextToBe(By.cssSelector("div.information > p.user_info:nth-child(1)"),  String.format("ID: %s", userIdMax));
    app.goTo().userLimits();
    app.getSessionHelper().isElementPresent(By.cssSelector("tr.table_row th:nth-child(1)"));
    Set<UserLimits> userLimitsSetFromWebBefore = app.getUserHelper()
            .setUserZeroLimitsWithoutNeo(
                    "1",
                    "1",
                    "1",
                    "1");
    app.press().saveButtonAtUserLimits();
    Thread.sleep(5000);
    Set<UserLimits> userLimitsFromApi = am.getApiUserHelper().getUserLimitsWithoutNeoFromApi();
    Set<UserLimits> userLimitsSetFromWebAfter = app.getUserHelper().getUserLimitsWithoutNeoFromWeb();
    Set<UserLimits> userLimitsFromDb = cm.getSqlUserHelper()
            .getUserLimitsFromDbWithoutNeo(String.format("SELECT UA.code, UA.name, UF.order_min, UF.exchange, UF.withdraw_min, UF.withdraw_max \n" +
            "FROM coin4coin_db.user_fees UF\n" +
                    "join coin4coin_db.assets UA on UF.asset_id=UA.id " +
                    "where UF.user_id=%s and UA.active=1 and UA.name!='Neo'", userIdMax));
    assertEquals(userLimitsFromDb, userLimitsWithoutNeoExpected);
    assertEquals(userLimitsFromApi,userLimitsFromDb);
    assertEquals(userLimitsSetFromWebAfter, userLimitsFromApi);
  }
}
