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
  private Set<UserData> getWallets() throws IOException {
    String json = Request.Get("http://209.182.216.247/api/admin/users")
            .addHeader("Content-Type", "application/json")
            .addHeader("authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjExOCwiaXNzIjoiaHR0cDovLzIwOS4xODIuMjE2LjI0Ny9hcGkvbG9naW4iLCJpYXQiOjE1NDU5NDU1MzcsImV4cCI6MTU0NTk1NjMzNywibmJmIjoxNTQ1OTQ1NTM3LCJqdGkiOiI3NENCa09RRk5DdUdXdzdTIn0.6dv5eZ1CViKOVcxBC6HY_BeUsEEfDcnF6dlDfNDc_Ng")
            .execute().returnContent().asString();

    JsonParser jsonParser = new JsonParser();
    JsonArray parsed  = jsonParser.parse(json).getAsJsonObject().get("data").getAsJsonObject().getAsJsonArray("users");

    System.out.println("Массив обьектов wallets представлен строкой "+ parsed);
    return new Gson().fromJson(parsed, new TypeToken<Set<UserData>>(){}.getType());
  }
}
