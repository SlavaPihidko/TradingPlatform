package apirequests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;


public class ApiHelperBase {
  private Properties propertiesApi;

  public String getToken () throws IOException {
    propertiesApi = new Properties();
    String targetApi = System.getProperty("targetApi", "localApi");
    propertiesApi.load(new FileReader(new File(String.format("src/test/resources/%s.properties", targetApi))));
    String token = propertiesApi.getProperty("api.userToken");
    return token;
  }
}
