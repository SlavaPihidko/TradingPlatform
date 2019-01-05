package apirequests;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import model.UserData;
import model.UserDataForApi;
import model.UserAccountStatusesForApi;
import model.UserStatusForApi;
import org.apache.http.client.fluent.Request;
import org.testng.annotations.Test;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ApiUserHelper extends ApiHelperBase {
  String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjExOCwiaXNzIjoiaHR0cDovLzIwOS4xODIuMjE2LjI0Ny9hcGkvbG9naW4iLCJpYXQiOjE1NDY3MDY0NDgsImV4cCI6MTU0NjcxNzI0OCwibmJmIjoxNTQ2NzA2NDQ4LCJqdGkiOiJYdXFZTUpzYkhHNFZTQ25kIn0.KtBKKa2oYQMWA4rAS6lw1Ex2ugnlXuwGTRWBK_fZGFU";

  @Test
  public void testUsersSetFromApi() throws IOException {
    Set<UserDataForApi> usersSetFromRequest =  getUsersFromApi();
    System.out.println("many users from API" +usersSetFromRequest);
    for(UserDataForApi n: usersSetFromRequest) {
      System.out.println("n :" +n);
    }
  }



  public Set<UserDataForApi> getUsersFromApi() throws IOException {
    String json = Request.Get("http://209.182.216.247/api/admin/users")
            .addHeader("Content-Type", "application/json")
            .addHeader("authorization", token )
            .execute().returnContent().asString();


    JsonParser jsonParser = new JsonParser();
    JsonArray parsed  = jsonParser.parse(json).getAsJsonObject().get("data").getAsJsonObject().getAsJsonArray("users");

    //System.out.println("Массив обьектов wallets представлен строкой "+ parsed);
    return new Gson().fromJson(parsed, new TypeToken<Set<UserDataForApi>>(){}.getType());
  }

// метод для создания обьекта одного юзера с АПИ. Берем первого юзера с АПИ
  public Set<UserData> getOneUserFromApi(boolean dateWithDash) throws IOException, ParseException {
    String json = Request.Get("http://209.182.216.247/api/admin/users")
            .addHeader("Content-Type", "application/json")
            .addHeader("authorization", token)
            .execute().returnContent().asString();

    JsonParser jsonParser = new JsonParser();
    //get(0) означает что берем первый обьект с массива обьектов users
    JsonElement parsedFirstPart  = jsonParser.parse(json)
            .getAsJsonObject().get("data").getAsJsonObject().getAsJsonArray("users").get(0);

//получаем обьект OneUserFromRequestFirstPart, который пропарсили согласно модели UserDataForApi
    UserDataForApi OneUserFromRequestFirstPart
            = new Gson().fromJson(parsedFirstPart, new TypeToken<UserDataForApi>(){}.getType());

    //System.out.println("parsedFirstPart "+ parsedFirstPart);
    //System.out.println("OneUserFromRequestFirstPart " + OneUserFromRequestFirstPart);

    //==============================================================================================

    JsonElement userAccountStatuses  = jsonParser.parse(json)
            .getAsJsonObject().get("data").getAsJsonObject().getAsJsonArray("users").get(0)
            .getAsJsonObject().get("user_account_statuses");
//получаем обьект OneUserFromRequestSecondPart, который пропарсили согласно модели UserAccountStatusesForApi
    UserAccountStatusesForApi OneUserFromRequestSecondPart
            = new Gson().fromJson(userAccountStatuses, new TypeToken<UserAccountStatusesForApi>(){}.getType());

    String statusAccount = OneUserFromRequestSecondPart.getName();
    //System.out.println("parsedSecondPart "+ userAccountStatuses);
    //System.out.println("OneUserFromRequestSecondPart " + OneUserFromRequestSecondPart);

    //=============================================================================================

    JsonElement userStatus = jsonParser.parse(json).getAsJsonObject().get("data")
            .getAsJsonObject().getAsJsonArray("users").get(0)
            .getAsJsonObject().get("status");

    UserStatusForApi OneUserFromRequestThirdPart
            = new Gson().fromJson(userStatus, new TypeToken<UserStatusForApi>(){}.getType());

    String status = OneUserFromRequestThirdPart.getName();

    // Делаем обьект oneUserFromRequestNew с всех трех частей, которые распарсили
    Set<UserData> userSetOneFromRequestNew = new HashSet<>();

    if(dateWithDash == true) {
      //Приведение форматы даты к такой что в БД для userLastLogin
      SimpleDateFormat input = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
      Date dateValue = input.parse(OneUserFromRequestFirstPart.getLast_login());
      SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
      String userLastLoginFormatted = output.format(dateValue);

      //Приведение форматы даты к такой что в БД для created
      Date dateValue2 = input.parse(OneUserFromRequestFirstPart.getCreated_at());
      String createdFormatted = output.format(dateValue2);

      UserData oneUserFromRequestNew = new UserData()
                                      .withId(OneUserFromRequestFirstPart.getId())
                                      .withFullName(OneUserFromRequestFirstPart.getUsername())
                                      .withEmail(OneUserFromRequestFirstPart.getEmail())
                                      .withLastLogin(userLastLoginFormatted)
                                      .withCreated(createdFormatted)
                                      .withKyc(status)
                                      .withStatus(statusAccount);

      System.out.println("User from API equal: " + oneUserFromRequestNew);

      userSetOneFromRequestNew.add(oneUserFromRequestNew);
    }
    else {
      UserData oneUserFromRequestNew = new UserData()
                                      .withId(OneUserFromRequestFirstPart.getId())
                                      .withFullName(OneUserFromRequestFirstPart.getUsername())
                                      .withEmail(OneUserFromRequestFirstPart.getEmail())
                                      .withLastLogin(OneUserFromRequestFirstPart.getLast_login())
                                      .withCreated(OneUserFromRequestFirstPart.getCreated_at())
                                      .withKyc(status)
                                      .withStatus(statusAccount);

      System.out.println("User from API _equal_: " + oneUserFromRequestNew);

      userSetOneFromRequestNew.add(oneUserFromRequestNew);
    }
    //System.out.println("parsedSecondPart "+ userStatus);
    //System.out.println("OneUserFromRequestSecondPart " + OneUserFromRequestThirdPart);

// Возвращаем обьект типа UserData
    return userSetOneFromRequestNew;
  }
}