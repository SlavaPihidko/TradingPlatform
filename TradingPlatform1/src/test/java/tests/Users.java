package tests;

import model.UserData;
import org.testng.annotations.Test;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Set;
import static org.testng.Assert.assertEquals;

public class Users extends TestBase {

// Тест берет первого юзера с API и этого же юзера ID 262 с БД и сравнивает эти обьекты по всем полям.
  @Test
  public void checkOneUserFromApiAndDb() throws SQLException, IOException, ParseException {

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
            "where U.id=262;");
// oneUserFromDB - expected result
  assertEquals(oneUserFromRequest, oneUserFromDb);
  }

  // Тест берет первого юзера с WEB морды и этого же юзера (первого юзера) с АПИ и сравнивает эти обьекты по всем полям.
  @Test
  public void checkOneUserFromApiAndWeb() throws IOException, InterruptedException, ParseException {
    app.getNavigationHelper().goToUsers();
    Thread.sleep(7000);
    Set<UserData> oneUserFromWeb = app.getUserHelper().getOneUserFromWeb(0);
    Set<UserData> userOneFromRequest =  am.getApiUserHelper().getOneUserFromApi(false);

    assertEquals(oneUserFromWeb, userOneFromRequest);
  }

}
