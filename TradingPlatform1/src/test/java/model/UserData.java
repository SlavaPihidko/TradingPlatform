package model;

public class UserData {

  private String id;
  private String firstName;

  //, String firstName, String lastName, String email, String lastLogin, String created, String kyc, String status
  public UserData(String id) {
    this.id = id;
   /* this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.lastLogin = lastLogin;
    this.created = created;
    this.kyc = kyc;
    this.status = status;*/
  }

  private String lastName;
  private String email;
  private String lastLogin;
  private String created;
  private String kyc;
  private String status;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getLastLogin() {
    return lastLogin;
  }

  public void setLastLogin(String lastLogin) {
    this.lastLogin = lastLogin;
  }

  public String getCreated() {
    return created;
  }

  public void setCreated(String created) {
    this.created = created;
  }

  public String getKyc() {
    return kyc;
  }

  public void setKyc(String kyc) {
    this.kyc = kyc;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }


}
