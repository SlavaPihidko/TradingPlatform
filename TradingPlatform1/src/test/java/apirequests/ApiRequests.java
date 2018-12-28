package apirequests;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import model.UserData;
import org.apache.http.client.fluent.Request;
import org.testng.annotations.Test;


import java.io.IOException;
import java.util.Set;

public class ApiRequests {

  @Test
  public void testUserList() throws IOException {
    Set<UserData> setUsersFromRequest =  getUsers();
    System.out.println("spisok " +setUsersFromRequest);
    for(UserData n: setUsersFromRequest) {
      System.out.println("n :" +n);
    }
  }



  private Set<UserData> getUsers() throws IOException {
    String json = Request.Get("http://209.182.216.247/api/admin/users")
            .addHeader("Content-Type", "application/json")
            .addHeader("authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjExOCwiaXNzIjoiaHR0cDovLzIwOS4xODIuMjE2LjI0Ny9hcGkvbG9naW4iLCJpYXQiOjE1NDYwMzU0MjcsImV4cCI6MTU0NjA0NjIyNywibmJmIjoxNTQ2MDM1NDI3LCJqdGkiOiJSRDdaSHJpTjRCNGlWU0t2In0.h0dOWN_OKQ85pj89Yu7D7bKWCBauY5XgfwT1P0DzhfI")
            .execute().returnContent().asString();


    JsonParser jsonParser = new JsonParser();
    JsonArray parsed  = jsonParser.parse(json).getAsJsonObject().get("data").getAsJsonObject().getAsJsonArray("users");

    System.out.println("Массив обьектов wallets представлен строкой "+ parsed);
    return new Gson().fromJson(parsed, new TypeToken<Set<UserData>>(){}.getType());
  }
}
