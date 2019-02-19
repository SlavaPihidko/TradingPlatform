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
    System.out.println("BeforeMethod run");
    if(personalFeeActive == 0) {
      cm.getSqlUserHelper().setIntValue(String.format("update coin4coin_db.users " +
              "set personal_fee_active=1 where id=%s;", userIdMax));
    }
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
