package apirequests;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import model.*;
import org.apache.http.client.fluent.Request;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class ApiUserHelper extends ApiHelperBase {

  //public String token = getToken();

  public ApiUserHelper() throws IOException {
  }

//  @Test
//  public void testUsersSetFromApi() throws IOException {
//    Set<UserDataForApi> usersSetFromRequest =  getUsersFromApi();
//    System.out.println("many users from API" +usersSetFromRequest);
//    for(UserDataForApi n: usersSetFromRequest) {
//      System.out.println("n :" +n);
//    }
//  }



  public Set<UserData> getUsersFromApi() throws IOException {
    String header = getPrpsApi().getProperty("api.baseUrl")+"/api/admin/users";
    String json = Request.Get(header)
                          .addHeader("Content-Type", "application/json")
                          .addHeader("authorization", getPrpsApi().getProperty("api.userToken") )
                          .execute().returnContent().asString();


    JsonParser jsonParser = new JsonParser();
    JsonArray parsed  = jsonParser.parse(json).getAsJsonObject().get("data")
            .getAsJsonObject().getAsJsonArray("users");

    //System.out.println("Массив обьектов wallets представлен строкой "+ parsed);
    List<UserDataForApi> usersList0 =
            new Gson().fromJson(parsed, new TypeToken<List<UserDataForApi>>(){}.getType());

    Set<UserData> usersList = new HashSet<>();

    for(UserDataForApi a: usersList0) {
      System.out.println("kyc " + a.getStatus().getName());
      UserData usersList1 = new UserData()
              .withId(a.getId())
              .withFullName(a.getUsername())
              .withEmail(a.getEmail())
              .withLastLogin(a.getLast_login())
              .withCreated(a.getCreated_at())
              .withStatus(a.getUserStatus().getName())
              .withKyc(a.getStatus().getName());
      usersList.add(usersList1);
    }

    return usersList;
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
    String phone;
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

    phone = userFromRequestSecondPart.getPhone();
    if (phone == null){
      phone = "-";
    }

    UserData user = new UserData()
            .withId(userFromRequestFirstPart.getId())
            .withEmail(userFromRequestFirstPart.getEmail())
            .withFullName(userFromRequestFirstPart.getUsername())
            .withMobileNumber(phone)
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
    String fbLink;

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
    //вместо Waiting на фронтенде показывается Not verified и 2 кнопки
    if(status.equals("3")) {
      status = "Not verified";
    }
//Integer x = userAccountHolderDetails.getStatus_id();
//String status = x.toString();
       state = userFromRequestFirstPart.getState();
       dob = userFromRequestFirstPart.getDob();
       country = userFromRequestFirstPart.getCountry();
       street = userFromRequestFirstPart.getStreet();
       postCode = userFromRequestFirstPart.getPost_code();
       fbLink = userFromRequestFirstPart.getFacebook_link();

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
      if(fbLink == null){
        fbLink = "-";
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
                  .withFacebook_link(fbLink);

      users.add(userAccount);
      System.out.println("userAccount from API: " + userAccount);

    return users;
  }

  public UserAccount getStatusAtUserAccountFromApi() throws IOException {
    String header = String.format(getPrpsApi()
            .getProperty("api.baseUrl")+"/api/admin/user/%s/account", getPrpsApi().getProperty("api.userID"));

    String json = Request.Get(header)
            .addHeader("Content-Type", "application/json")
            .addHeader("authorization", getPrpsApi().getProperty("api.userToken"))
            .execute().returnContent().asString();

    JsonParser jsonParser = new JsonParser();
    // первая часть
    JsonElement parsedSecondPart = jsonParser.parse(json)
            .getAsJsonObject().get("data")
            .getAsJsonObject().getAsJsonArray("verifications").get(2)
            .getAsJsonObject().get("pivot");

    UserAccountHolderDetailsForApi userAccountHolderDetails =
            new Gson().fromJson(parsedSecondPart, new TypeToken<UserAccountHolderDetailsForApi>(){}.getType());

    String status = Integer.toString(userAccountHolderDetails.getStatus_id());
    if(status.equals("3")) {
      status = "Not verified";
    }
    if(status.equals("2")) {
      status = "Not verified";
    }
    if(status.equals("1")) {
      status = "Verified";
    }

    UserAccount status2 = new UserAccount().withtVerificationStatus(status);
    System.out.println("status from API: " + status2);
    return status2;
  }

  public Set<UserAssets> getUserBalancesFromApi() throws IOException {
    Set<UserAssets> userAssets = new HashSet<>();
    String header = String.format(getPrpsApi()
            .getProperty("api.baseUrl")+"/api/admin/user/%s/balances", getPrpsApi().getProperty("api.userID"));

    String json = Request.Get(header)
            .addHeader("Content-Type", "application/json")
            .addHeader("authorization", getPrpsApi().getProperty("api.userToken"))
            .execute().returnContent().asString();

    JsonParser jsonParser = new JsonParser();
    /*JsonElement parsedFirstPart = jsonParser.parse(json)
            .getAsJsonObject().getAsJsonArray("data");

    Set<UserAssets> userAssetsOnlyBalance =
            new Gson().fromJson(parsedFirstPart, new TypeToken<Set<UserAssets>>(){}.getType());
//userAssets.add(userAssetsOnlyBalance);

    for(UserAssets userAssets1: userAssetsOnlyBalance){
      double balance = userAssets1.getBalance();
      int asset_id = userAssets1.getAsset_id();
      UserAssets userAssets2 = new UserAssets().withBalance(balance).withAsset_id(asset_id);
      userAssets.add(userAssets2);
      System.out.println("userAssets from API firstPart" + userAssets);
    }*/
//??????????????????????????????????????????????????????????????
    JsonArray parsedSecondPart =  jsonParser.parse(json)
            .getAsJsonObject().getAsJsonArray("data");

    List<UserAssets> userAssetsOnlyCode =
            new Gson().fromJson(parsedSecondPart, new TypeToken<List<UserAssets>>(){}.getType());
    //System.out.println( "userAssetsOnlyCode "+userAssetsOnlyCode);
    for(UserAssets userAssets1: userAssetsOnlyCode){
     String code = userAssets1.getAsset().getCode();
     double balance = userAssets1.getBalance();
      UserAssets userAssets2 = new UserAssets().withCode(code).withBalance(balance);
      //System.out.println("userAssets2 " +userAssets2);
      userAssets.add(userAssets2);
      //System.out.println("userAssets from API secondPart" + userAssets);
    }
    //??????????????????????????????????????????????????????????
    System.out.println("usersAssets from API " + userAssets);
    return userAssets;
  }

  public Set<UserTxes> getUserTransactionsFromApi() throws IOException {
    Set<UserTxes> userTxes = new HashSet<>();
    String header = String.format(getPrpsApi()
            .getProperty("api.baseUrl")+"/api/admin/user/%s/transactions", getPrpsApi().getProperty("api.userID"));

    String json = Request.Get(header)
            .addHeader("Content-Type", "application/json")
            .addHeader("authorization", getPrpsApi().getProperty("api.userToken"))
            .execute().returnContent().asString();

    JsonParser jsonParser = new JsonParser();
    JsonArray parsed = jsonParser.parse(json)
            .getAsJsonObject().get("data")
            .getAsJsonObject().getAsJsonArray("transactions");

    List<UserTxes> userTxesList = new Gson().fromJson(parsed, new TypeToken<List<UserTxes>>(){}.getType());

    for (UserTxes userTxes1: userTxesList) {
      String code = userTxes1.getAsset().getCode();
      int id = userTxes1.getId();
      String transaction_type = userTxes1.getTransaction_type();

      if (transaction_type.equals("input")) {
         id = userTxes1.getPosition();
         transaction_type = "Deposit";
      }

      if(transaction_type.equals("output")) {
        transaction_type = "Withdraw";
      }

      double amount = userTxes1.getAmount();
      String status = userTxes1.getStatus();
      String created_at = userTxes1.getCreated_at();
      int user_id = userTxes1.getUser_id();

      UserTxes userTxes2 = new UserTxes()
              .withId(id)
              .withTransaction_type(transaction_type)
              .withUser_id(user_id)
              .withCode(code)
              .withAmount(amount).withStatus(status).withCreated_at(created_at);
      userTxes.add(userTxes2);
    }
    System.out.println("userTxes from API " + userTxes);
    return userTxes;
  }

  public Set<UserOrders> getUserOrdersFromApi() throws IOException {
    Set<UserOrders> userOrders = new HashSet<>();

    String header = String.format(getPrpsApi()
            .getProperty("api.baseUrl")+"/api/admin/user/%s/orders", getPrpsApi().getProperty("api.userID"));

    String json = Request.Get(header)
            .addHeader("Content-Type", "application/json")
            .addHeader("authorization", getPrpsApi().getProperty("api.userToken"))
            .execute().returnContent().asString();

    JsonParser jsonParser = new JsonParser();
    JsonArray parsed = jsonParser.parse(json)
            .getAsJsonObject().get("data")
            .getAsJsonObject().getAsJsonArray("orders");

    List<UserOrders> userOrdersList = new Gson().fromJson(parsed, new TypeToken<List<UserOrders>>(){}.getType());

    for (UserOrders userOrders1: userOrdersList) {
      int id = userOrders1.getId();
      String pair = userOrders1.getPair().toUpperCase().replace("_","/");
      String type = userOrders1.getType().substring(0,1).toUpperCase() + userOrders1.getType().substring(1);
      int user_id = userOrders1.getUser_id();
      double quantity = userOrders1.getQuantity();
      String created_at = userOrders1.getCreated_at();
      // делаем первую букву заглавной
      String status = userOrders1.getStatus().substring(0,1).toUpperCase() + userOrders1.getStatus().substring(1);

      if (type.equals("Market_sell")) {
        type = "Sell";
      }

      if(type.equals("Market_buy")) {
        type = "Buy";
      }

      UserOrders userOrders2 = new UserOrders()
              .withId(id)
              .withPair(pair)
              .withType(type)
              .withUser_id(user_id)
              .withQuantity(quantity)
              .withCreated_at(created_at)
              .withStatus(status);

      userOrders.add(userOrders2);
    }

    System.out.println("userOrders from API: " + userOrders);
    return userOrders;
  }

  public Set<UserLimits> getUserLimitsWithoutNeoFromApi() throws IOException {
    Set<UserLimits> userLimits = new HashSet<>();

    String header = String.format(getPrpsApi()
            .getProperty("api.baseUrl")+"/api/admin/user/%s/account", getPrpsApi().getProperty("api.userID"));

    String json = Request.Get(header)
            .addHeader("Content-Type", "application/json")
            .addHeader("authorization", getPrpsApi().getProperty("api.userToken"))
            .execute().returnContent().asString();

    JsonParser jsonParser = new JsonParser();
    JsonArray parsed = jsonParser.parse(json)
            .getAsJsonObject().get("data")
            .getAsJsonObject().getAsJsonArray("personal_limitations");

    List<UserLimits> userLimitsList = new Gson().fromJson(parsed, new TypeToken<List<UserLimits>>(){}.getType());
    for (UserLimits userLimits1: userLimitsList) {
      // Делаем обьект без монеты НЕО
      if(!userLimits1.getAsset().getName().equals("Neo")) {
        String name = userLimits1.getAsset().getName();
        double order_min = userLimits1.getOrder_min();
        double exchange = userLimits1.getExchange();
        double withdraw_min = userLimits1.getWithdraw_min();
        double withdraw_max = userLimits1.getWithdraw_max();

        UserLimits userLimits2 = new UserLimits()
                .withName(name)
                .withOrder_min(order_min)
                .withExchange(exchange)
                .withWithdraw_min(withdraw_min)
                .withWithdraw_max(withdraw_max);

        System.out.println("userLimits2 :" + userLimits2);
        userLimits.add(userLimits2);
      }
    }
    System.out.println("userLimits from API: " + userLimits);
    return userLimits;
  }

  public UserLimits getUserNeoLimitsFromApi() throws IOException {
    UserLimits userLimits = null;

    String header = String.format(getPrpsApi()
            .getProperty("api.baseUrl")+"/api/admin/user/%s/account", getPrpsApi().getProperty("api.userID"));

    String json = Request.Get(header)
            .addHeader("Content-Type", "application/json")
            .addHeader("authorization", getPrpsApi().getProperty("api.userToken"))
            .execute().returnContent().asString();

    JsonParser jsonParser = new JsonParser();
    JsonArray parsed = jsonParser.parse(json)
            .getAsJsonObject().get("data")
            .getAsJsonObject().getAsJsonArray("personal_limitations");

    List<UserLimits> userLimitsList = new Gson().fromJson(parsed, new TypeToken<List<UserLimits>>(){}.getType());
    for (UserLimits userLimits1: userLimitsList) {
      String name = userLimits1.getAsset().getName();
      double order_min = userLimits1.getOrder_min();
      double exchange = userLimits1.getExchange();
      double withdraw_min = userLimits1.getWithdraw_min();
      double withdraw_max = userLimits1.getWithdraw_max();

      if ("Neo".equals(userLimits1.getAsset().getName())) {
        userLimits = new UserLimits()
                .withName(name)
                .withOrder_min(order_min)
                .withExchange(exchange)
                .withWithdraw_min(withdraw_min)
                .withWithdraw_max(withdraw_max);
      }

    }
    System.out.println("userLimits from API: " + userLimits);
    return userLimits;
  }


}
