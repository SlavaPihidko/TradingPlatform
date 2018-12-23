package tests;

import model.UserData;
import org.testng.annotations.Test;
import java.sql.SQLException;
import java.util.Set;
import static org.testng.Assert.assertEquals;

public class Users extends TestBase {


  @Test
  public void checkUsers() throws InterruptedException {

    app.getNavigationHelper().goToUsers();
    Thread.sleep(10000);
    UserData oneUser = app.getUserHelper().getOneUserFromWeb();
    System.out.println( //Вывод данных по одному пользователю для отладки
            " ID of first user: " + oneUser.getId() + "\n" +
                    " FirstName of first user: " + oneUser.getFirstName() + "\n" +
                    " LastName of first user: " + oneUser.getLastName() + "\n" +
                    " Email of first user: " + oneUser.getEmail() + "\n" +
                    " LastLogin of first user: " + oneUser.getLastLogin() + "\n" +
                    " Created of first user: " + oneUser.getCreated() + "\n" +
                    " KYC of first user: " + oneUser.getKyc() + "\n" +
                    " Status of first user: " + oneUser.getStatus() + "\n");
    System.out.println(oneUser); //Вывод данных по одному пользователю для отладки
//    Set<UserData> users = app.getUserHelper().getUsersFromWeb(); //Получение всех юзеров на странице (их данные)
//    System.out.println(users);

  }

  @Test
  public void checkUserFromWebAndDB() throws SQLException, InterruptedException {

    app.getNavigationHelper().goToUsers();
    Thread.sleep(10000);
    UserData oneUserFromWeb = app.getUserHelper().getOneUserFromWeb();

    cm.getConnection();
    Set<UserData> oneUserFromDB = cm.getSqlUserHelper().makeDbQueryForUsers("SELECT U.id," +
            " concat(UD.first_name, ' ', UD.last_name) as fullName, U.email, U.last_login," +
            " U.created_at, US.name as kyc, UAS.name as status\n" +
            "FROM coin4coin_db.users U  \n" +
            "join coin4coin_db.user_datas UD on U.id = UD.user_id \n" +
            "join coin4coin_db.user_statuses US on U.status_id = US.id\n" +
            "join coin4coin_db.user_account_statuses UAS on U.account_status_id = UAS.id\n" +
            "where U.id=262;");


  assertEquals(oneUserFromWeb,oneUserFromDB);

  }
}
