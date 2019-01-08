package apirequests;

import java.io.IOException;

public class ApiManager {

  private ApiUserHelper apiUserRequestsHelper;

  public void dealWithApi() throws IOException {
    apiUserRequestsHelper = new ApiUserHelper();
  }

  public ApiUserHelper getApiUserHelper() {
    return apiUserRequestsHelper;
  }
}
