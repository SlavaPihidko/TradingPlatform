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

import static org.openqa.selenium.support.ui.ExpectedConditions.textToBe;
import static org.testng.Assert.assertEquals;

public class SetUserLimitsWithoutNeo extends TestBase {

  @BeforeMethod
  public void beforeUserSetLimitsNeo() throws SQLException {
    // подготовка теста, установка personal_fee_active=1
    if(personalFeeActive == 0) {
      cm.getSqlUserHelper().setIntValue(String.format("update coin4coin_db.users " +
              "set personal_fee_active=1 where id=%s;", userIdMax));
    }
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
    //Thread.sleep(9000);
    app.getSessionHelper().isElementPresent(By.cssSelector("tr.table_row > th:nth-child(3)"), "bgzglrqcc@emltmp.com");
    app.goTo().userInfo();
    app.getSessionHelper().isElementPresent(By.cssSelector("div.information > p.user_info:nth-child(5)"), "Email: bgzglrqcc@emltmp.com");
    //Thread.sleep(4000);
    app.goTo().userLimits();
    app.getSessionHelper().isElementPresent(By.cssSelector("tr.table_row th:nth-child(1)"), "Bitcoin Test");
    //Thread.sleep(4000);
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
