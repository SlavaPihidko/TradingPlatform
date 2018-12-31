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
import java.util.Set;

public class ApiRequests {
  String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjExOCwiaXNzIjoiaHR0cDovLzIwOS4xODIuMjE2LjI0Ny9hcGkvbG9naW4iLCJpYXQiOjE1NDYyNTg3MDMsImV4cCI6MTU0NjI2OTUwMywibmJmIjoxNTQ2MjU4NzAzLCJqdGkiOiJFMGM5Q3dVa1RKYktHWTI0In0.4PxM1msA0e4mLAxYyI9EBk6FYgeQ-HFNiax6aWfcG6U";

  @Test
  public void testUsersSetFromApi() throws IOException {
    Set<UserDataForApi> usersSetFromRequest =  getUsersFromApi();
    System.out.println("many users from API" +usersSetFromRequest);
    for(UserDataForApi n: usersSetFromRequest) {
      System.out.println("n :" +n);
    }
  }

  @Test
  public void testOneUserFromApi() throws IOException {
    UserData userOneFromRequest =  getOneUserFromApi();
    System.out.println("one user from API: " +userOneFromRequest);
  }



  public Set<UserDataForApi> getUsersFromApi() throws IOException {
    String json = Request.Get("http://209.182.216.247/api/admin/users")
            .addHeader("Content-Type", "application/json")
            .addHeader("authorization", token )
            .execute().returnContent().asString();


    JsonParser jsonParser = new JsonParser();
    JsonArray parsed  = jsonParser.parse(json).getAsJsonObject().get("data").getAsJsonObject().getAsJsonArray("users");

    System.out.println("Массив обьектов wallets представлен строкой "+ parsed);
    return new Gson().fromJson(parsed, new TypeToken<Set<UserDataForApi>>(){}.getType());
  }

// метод для создания обьекта одного юзера с АПИ
  public UserData getOneUserFromApi() throws IOException {
    String json = Request.Get("http://209.182.216.247/api/admin/users")
            .addHeader("Content-Type", "application/json")
            .addHeader("authorization", token)
            .execute().returnContent().asString();

    JsonParser jsonParser = new JsonParser();
    JsonElement parsedFirstPart  = jsonParser.parse(json)
            .getAsJsonObject().get("data").getAsJsonObject().getAsJsonArray("users").get(0);

//получаем обьект userOneFromRequestFirstPart, который пропарсили согласно модели UserDataForApi
    UserDataForApi userOneFromRequestFirstPart
            = new Gson().fromJson(parsedFirstPart, new TypeToken<UserDataForApi>(){}.getType());

    System.out.println("parsedFirstPart "+ parsedFirstPart);
    System.out.println("userOneFromRequestFirstPart " + userOneFromRequestFirstPart);

    //==============================================================================================

    JsonElement userAccountStatuses  = jsonParser.parse(json)
            .getAsJsonObject().get("data").getAsJsonObject().getAsJsonArray("users").get(0)
            .getAsJsonObject().get("user_account_statuses");

    UserAccountStatusesForApi userOneFromRequestSecondPart
            = new Gson().fromJson(userAccountStatuses, new TypeToken<UserAccountStatusesForApi>(){}.getType());

    // Запихиваем результат с секондпарт в первый обьект
    String statusAccount = userOneFromRequestSecondPart.getName();



    System.out.println("parsedSecondPart "+ userAccountStatuses);
    System.out.println("userOneFromRequestSecondPart " +userOneFromRequestSecondPart);


    //=============================================================================================

    JsonElement userStatus = jsonParser.parse(json).getAsJsonObject().get("data")
            .getAsJsonObject().getAsJsonArray("users").get(0)
            .getAsJsonObject().get("status");

    UserStatusForApi userOneFromRequestThirdPart
            = new Gson().fromJson(userStatus, new TypeToken<UserStatusForApi>(){}.getType());

    String status = userOneFromRequestThirdPart.getName();

    // Делаем обьект userOneFromRequestNew с всех трех частей, которые распарсили
    UserData userOneFromRequestNew = new UserData(
                                                  userOneFromRequestFirstPart.getId(),
                                                  userOneFromRequestFirstPart.getUsername(),
                                                  userOneFromRequestFirstPart.getEmail(),
                                                  userOneFromRequestFirstPart.getLast_login(),
                                                  userOneFromRequestFirstPart.getCreated_at(),
                                                  status,
                                                  statusAccount
                                                  );

    System.out.println("parsedSecondPart "+ userStatus);
    System.out.println("userOneFromRequestSecondPart " + userOneFromRequestThirdPart);

    return userOneFromRequestNew;
  }
}
