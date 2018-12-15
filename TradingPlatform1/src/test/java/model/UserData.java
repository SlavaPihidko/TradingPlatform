package model;

import java.util.List;
import java.util.Objects;

public class UserData {

  private String id;
  private String firstName;
  private String lastName;
  private String email;
  private String lastLogin;
  private String created;
  private String kyc;
  private String status;

  List<UserData> userData ;

  public List<UserData> getUsersData() {
    return userData;
  }

  //, String firstName, String lastName, String email, String lastLogin, String created, String kyc, String status
  public UserData(String id, String firstName, String lastName, String email, String lastLogin, String created, String kyc, String status) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.lastLogin = lastLogin;
    this.created = created;
    this.kyc = kyc;
    this.status = status;
  }

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


  @Override
  public String toString() {
    return "UserData{" +
            "id='" + id + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", lastLogin='" + lastLogin + '\'' +
            ", created='" + created + '\'' +
            ", kyc='" + kyc + '\'' +
            ", status='" + status + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof UserData)) return false;
    UserData userData = (UserData) o;
    return getId().equals(userData.getId()) &&
            getFirstName().equals(userData.getFirstName()) &&
            getLastName().equals(userData.getLastName()) &&
            getEmail().equals(userData.getEmail()) &&
            getLastLogin().equals(userData.getLastLogin()) &&
            getCreated().equals(userData.getCreated()) &&
            Objects.equals(getKyc(), userData.getKyc()) &&
            Objects.equals(getStatus(), userData.getStatus());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getFirstName(), getLastName(), getEmail(), getLastLogin(), getCreated(), getKyc(), getStatus());
  }
}
