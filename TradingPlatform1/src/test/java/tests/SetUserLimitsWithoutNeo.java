package tests;

import model.UserLimits;
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
  public void beforeUserSetLimitsNeo() throws SQLException {
    // подготовка теста, установка personal_fee_active=1
    if(personalFeeActive == 0) {
      cm.getSqlUserHelper().setIntValue(String.format("update coin4coin_db.users " +
              "set personal_fee_active=1 where id=%s;", userIdMax));
    }
  }

  @Test(priority = 27)
  public void checkSetUserLimitsWithoutNeoFromWebAndDb_9() throws IOException, SQLException, InterruptedException {
    System.out.println("===checkSetUserLimitsWithoutNeoFromWebAndDb_9===");
    System.out.println("//проверяем что в Все ассеты кроме НЕО записали значения, " +
            "сразу же стерли, в форме путота, в запросе передается 0");
    Set<UserLimits> userLimitsExpected = new HashSet<>();
    List<String> listNameAssets =  cm.getSqlUserHelper().getListNameAssetsFromDb("SELECT name FROM coin4coin_db.assets where active=1;");
    for(int i=0; i< listNameAssets.size(); i++) {
      UserLimits expectedResult = new UserLimits()
              .withName(listNameAssets.get(i))
              .withOrder_min(0)
              .withExchange(0)
              .withWithdraw_min(0)
              .withWithdraw_max(0);
      System.out.println("expectedResult " + expectedResult);
      userLimitsExpected.add(expectedResult);
    }
    System.out.println("userLimitsExpected " + userLimitsExpected);
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
    assertEquals(userLimitsFromDb, userLimitsExpected);
    assertEquals(userLimitsSetFromWebAfter, userLimitsFromApi);
  }
}
