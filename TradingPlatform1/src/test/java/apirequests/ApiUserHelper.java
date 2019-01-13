package apirequests;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import model.*;
import org.apache.http.client.fluent.Request;
import org.testng.annotations.Test;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class ApiUserHelper extends ApiHelperBase {

  //public String token = getToken();

  public ApiUserHelper() throws IOException {
  }

  @Test
  public void testUsersSetFromApi() throws IOException {
    Set<UserDataForApi> usersSetFromRequest =  getUsersFromApi();
    System.out.println("many users from API" +usersSetFromRequest);
    for(UserDataForApi n: usersSetFromRequest) {
      System.out.println("n :" +n);
    }
  }



  public Set<UserDataForApi> getUsersFromApi() throws IOException {
    String header = getPrpsApi().getProperty("api.baseUrl")+"/api/admin/users";
    String json = Request.Get(header)
                          .addHeader("Content-Type", "application/json")
                          .addHeader("authorization", getPrpsApi().getProperty("api.userToken") )
                          .execute().returnContent().asString();


    JsonParser jsonParser = new JsonParser();
    JsonArray parsed  = jsonParser.parse(json).getAsJsonObject().get("data").getAsJsonObject().getAsJsonArray("users");

    //System.out.println("Массив обьектов wallets представлен строкой "+ parsed);
    return new Gson().fromJson(parsed, new TypeToken<Set<UserDataForApi>>(){}.getType());
  }

// метод для создания обьекта одного юзера с АПИ. Берем первого юзера с АПИ
  public Set<UserData> getOneUserFromApi(boolean dateWithDash) throws IOException, ParseException {
    String header = getPrpsApi().getProperty("api.baseUrl")+"/api/admin/users";
    String json = Request.Get(header)
            .addHeader("Content-Type", "application/json")
            .addHeader("authorization", getPrpsApi().getProperty("api.userToken"))
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

  public Set<UserData> getUserAccountInfoFromApi() throws IOException {
    String header = String.format(getPrpsApi()
            .getProperty("api.baseUrl")+"/api/admin/user/%s/account", getPrpsApi().getProperty("api.userID"));
    Set<UserData> users = new HashSet<>();
    String json = Request.Get(header)
                          .addHeader("Content-Type", "application/json")
                          .addHeader("authorization", getPrpsApi().getProperty("api.userToken"))
                          .execute().returnContent().asString();

    JsonParser jsonParser = new JsonParser();
    JsonElement parsedFirstPart  = jsonParser.parse(json).getAsJsonObject().get("data");

    UserDataForApi userFromRequestFirstPart
            = new Gson().fromJson(parsedFirstPart, new TypeToken<UserDataForApi>(){}.getType());

    JsonElement parsedSecondPart = jsonParser.parse(json)
            .getAsJsonObject().get("data").getAsJsonObject().get("data");

    UserDataForApi userFromRequestSecondPart
            = new Gson().fromJson(parsedSecondPart, new TypeToken<UserDataForApi>(){}.getType());

    JsonElement parsedThirdPart = jsonParser.parse(json)
            .getAsJsonObject().get("data").getAsJsonObject().get("account_type");

    UserAccountTypeForApi userFromRequestThirdPart
            = new Gson().fromJson(parsedThirdPart, new TypeToken<UserAccountTypeForApi>(){}.getType());


    UserData user = new UserData()
            .withId(userFromRequestFirstPart.getId())
            .withEmail(userFromRequestFirstPart.getEmail())
            .withFullName(userFromRequestFirstPart.getUsername())
            .withMobileNumber(userFromRequestSecondPart.getPhone())
            .withAccountType(userFromRequestThirdPart.getName());
    users.add(user);
    System.out.println("from api " + user);
    return users;
  }

  public Set<UserAccount> getUserAccountFromApi() throws IOException {
    String state;
    String status;
    String dob;
    String country;
    String street;
    String postCode;
    String FbLink;

    String header = String.format(getPrpsApi()
            .getProperty("api.baseUrl")+"/api/admin/user/%s/account", getPrpsApi().getProperty("api.userID"));

    Set<UserAccount> users = new HashSet<>();

    String json = Request.Get(header)
            .addHeader("Content-Type", "application/json")
            .addHeader("authorization", getPrpsApi().getProperty("api.userToken"))
            .execute().returnContent().asString();

    JsonParser jsonParser = new JsonParser();
    // первая часть
    JsonElement parsedFirstPart = jsonParser.parse(json)
            .getAsJsonObject().get("data").getAsJsonObject().get("data");

    UserAccount userFromRequestFirstPart
            = new Gson().fromJson(parsedFirstPart, new TypeToken<UserAccount>(){}.getType());
    // вторая часть
    JsonElement parsedSecondPart = jsonParser.parse(json)
            .getAsJsonObject().get("data")
            .getAsJsonObject().getAsJsonArray("verifications").get(2)
            .getAsJsonObject().get("pivot");

    UserAccountHolderDetailsForApi userAccountHolderDetails =
            new Gson().fromJson(parsedSecondPart, new TypeToken<UserAccountHolderDetailsForApi>(){}.getType());

    // приводим int к строке
    status = Integer.toString(userAccountHolderDetails.getStatus_id());
    System.out.println("status " + status);

    //  проверка на статус. С апи получаем int значение
    if(status.equals("1")) {
      status = "Verified";
    }
    if(status.equals("2")) {
      status = "Not verified";
    }
    if(status.equals("3")) {
      status = "Waiting";
    }
//Integer x = userAccountHolderDetails.getStatus_id();
//String status = x.toString();
       state = userFromRequestFirstPart.getState();
       dob = userFromRequestFirstPart.getDob();
       country = userFromRequestFirstPart.getCountry();
       street = userFromRequestFirstPart.getStreet();
       postCode = userFromRequestFirstPart.getPost_code();
       FbLink = userFromRequestFirstPart.getFacebook_link();

       // проверка на NULL параметров, которые получаем с АПИ.
      // если NULL присваиваем прочерк
      if( state== null) {
        state = "-";
      }
      if (dob == null) {
        dob = "-";
      }
      if (country == null) {
        country = "-";
      }
      if(street == null){
        street = "-";
      }
      if(postCode == null){
        postCode = "-";
      }
      if(FbLink == null){
        FbLink = "-";
      }

      UserAccount userAccount = new UserAccount()
                  .withtVerificationStatus(status)
                  .withFirst_name(userFromRequestFirstPart.getFirst_name())
                  .withLast_name(userFromRequestFirstPart.getLast_name())
                  .withDob(dob)
                  .withCountry(country)
                  .withState(state)
                  .withStreet(street)
                  .withPost_code(postCode)
                  .withFacebook_link(FbLink);

      users.add(userAccount);
      System.out.println("userAccount from API: " + userAccount);

    return users;
  }
}
