package model;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class UserData {

  private String id;
  private String fullName;
  private String firstName;
  private String lastName;
  private String email;
  private String lastLogin;
  private String created;
  private String kyc;
  private String status;

  Set<UserData> userData ;

  public Set<UserData> getUsersData() {
    return userData;
  }

  //, String firstName, String lastName, String email, String lastLogin, String created, String kyc, String status
  public UserData(String id, String fullName, String email, String lastLogin, String created, String kyc, String status) {
    this.id = id;
    this.fullName = fullName;
    this.email = email;
    this.lastLogin = lastLogin;
    this.created = created;
    this.kyc = kyc;
    this.status = status;
  }

  public UserData(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
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
            ", fullName='" + fullName + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", lastLogin='" + lastLogin + '\'' +
            ", created='" + created + '\'' +
            ", kyc='" + kyc + '\'' +
            ", status='" + status + '\'' +
            ", userData=" + userData +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof UserData)) return false;
    UserData userData = (UserData) o;
    return Objects.equals(getId(), userData.getId()) &&
            Objects.equals(getFullName(), userData.getFullName()) &&
            Objects.equals(getEmail(), userData.getEmail()) &&
            Objects.equals(getLastLogin(), userData.getLastLogin()) &&
            Objects.equals(getCreated(), userData.getCreated()) &&
            Objects.equals(getKyc(), userData.getKyc()) &&
            Objects.equals(getStatus(), userData.getStatus());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getFullName(), getEmail(), getLastLogin(), getCreated(), getKyc(), getStatus());
  }
}
