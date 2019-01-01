package apirequests;

public class ApiManager {

  private ApiUserRequestsHelper apiUserRequestsHelper;

  public void dealWithApi() {
    apiUserRequestsHelper = new ApiUserRequestsHelper();
  }

  public ApiUserRequestsHelper getApiUserRequestsHelper() {
    return apiUserRequestsHelper;
  }
}
