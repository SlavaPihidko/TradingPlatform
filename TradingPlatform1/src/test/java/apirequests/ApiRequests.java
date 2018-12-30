package apirequests;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import model.UserData;
import model.UserDataForApi;
import model.UserDataForApiSecondPart;
import org.apache.http.client.fluent.Request;
import org.testng.annotations.Test;


import java.io.IOException;
import java.util.Set;

public class ApiRequests {
  String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjExOCwiaXNzIjoiaHR0cDovLzIwOS4xODIuMjE2LjI0Ny9hcGkvbG9naW4iLCJpYXQiOjE1NDYyMDA0MjEsImV4cCI6MTU0NjIxMTIyMSwibmJmIjoxNTQ2MjAwNDIxLCJqdGkiOiJzTUZQcWFIdHNENjRiYVA2In0.It3u0KUytRxYioEGEKqfkIJosDOUvbi4Txb4xO07nFM";

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
    UserDataForApi userOneFromRequest =  getOneUserFromApi();
    System.out.println("one user from API: " +userOneFromRequest);
  }



  private Set<UserDataForApi> getUsersFromApi() throws IOException {
    String json = Request.Get("http://209.182.216.247/api/admin/users")
            .addHeader("Content-Type", "application/json")
            .addHeader("authorization", token )
            .execute().returnContent().asString();


    JsonParser jsonParser = new JsonParser();
    JsonArray parsed  = jsonParser.parse(json).getAsJsonObject().get("data").getAsJsonObject().getAsJsonArray("users");

    System.out.println("Массив обьектов wallets представлен строкой "+ parsed);
    return new Gson().fromJson(parsed, new TypeToken<Set<UserDataForApi>>(){}.getType());
  }

  private UserDataForApi getOneUserFromApi() throws IOException {
    String json = Request.Get("http://209.182.216.247/api/admin/users")
            .addHeader("Content-Type", "application/json")
            .addHeader("authorization", token)
            .execute().returnContent().asString();

    JsonParser jsonParser = new JsonParser();
    JsonElement parsedFirstPart  = jsonParser.parse(json)
            .getAsJsonObject().get("data").getAsJsonObject().getAsJsonArray("users").get(0);

    UserDataForApi userOneFromRequestFirstPart
            = new Gson().fromJson(parsedFirstPart, new TypeToken<UserDataForApi>(){}.getType());



    System.out.println("parsedFirstPart "+ parsedFirstPart);
    System.out.println("userOneFromRequestFirstPart " +userOneFromRequestFirstPart);

    //==============================================================================================

    JsonElement parsedSecondPart  = jsonParser.parse(json)
            .getAsJsonObject().get("data").getAsJsonObject().getAsJsonArray("users").get(0);

    JsonElement userAccountStatuses = parsedSecondPart.getAsJsonObject().get("user_account_statuses");

    UserDataForApiSecondPart userOneFromRequestSecondPart
            = new Gson().fromJson(userAccountStatuses, new TypeToken<UserDataForApiSecondPart>(){}.getType());

    // Запихиваем результат с секондпарт в первый обьект
    String status = userOneFromRequestSecondPart.getName();

    UserDataForApi userOneFromRequestNew = new UserDataForApi(userOneFromRequestFirstPart.getId(),
                                                              userOneFromRequestFirstPart.getType_id(),
                                                              status,
                                                              userOneFromRequestFirstPart.getLast_login(),
                                                              userOneFromRequestFirstPart.getCreated_at(),
                                                              userOneFromRequestFirstPart.getEmail(),
                                                              userOneFromRequestFirstPart.getUsername());

    System.out.println("parsedSecondPart "+ parsedSecondPart);
    System.out.println("userOneFromRequestSecondPart " +userOneFromRequestSecondPart);
    return userOneFromRequestNew;
  }
}
