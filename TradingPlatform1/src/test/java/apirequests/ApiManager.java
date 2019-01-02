package apirequests;

public class ApiManager {

  private ApiUserHelper apiUserRequestsHelper;

  public void dealWithApi() {
    apiUserRequestsHelper = new ApiUserHelper();
  }

  public ApiUserHelper getApiUserHelper() {
    return apiUserRequestsHelper;
  }
}
