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


  //, String firstName, String lastName, String email, String lastLogin, String created, String kyc, String status
//  public UserData(String id, String fullName, String email, String lastLogin, String created, String kyc, String status) {
//    this.id = id;
//    this.fullName = fullName;
//    this.email = email;
//    this.lastLogin = lastLogin;
//    this.created = created;
//    this.kyc = kyc;
//    this.status = status;
//  }

//  public UserData(String id) {
//    this.id = id;
//  }

  public String getId() {
    return id;
  }

  public UserData withId(String id) {
    this.id = id;
    return this;
  }

  public String getFullName() {
    return fullName;
  }

  public UserData withFullName(String fullName) {
    this.fullName = fullName;
    return this;
  }

  public String getFirstName() {
    return firstName;
  }

  public UserData withFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public String getLastName() {
    return lastName;
  }

  public UserData withLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public String getEmail() {
    return email;
  }

  public UserData withEmail(String email) {
    this.email = email;
    return this;
  }

  public String getLastLogin() {
    return lastLogin;
  }

  public UserData withLastLogin(String lastLogin) {
    this.lastLogin = lastLogin;
    return this;
  }

  public String getCreated() {
    return created;
  }

  public UserData withCreated(String created) {
    this.created = created;
    return this;
  }

  public String getKyc() {
    return kyc;
  }

  public UserData withKyc(String kyc) {
    this.kyc = kyc;
    return this;
  }

  public String getStatus() {
    return status;
  }

  public UserData withStatus(String status) {
    this.status = status;
    return this;
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
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof UserData)) return false;

    UserData userData = (UserData) o;

    if (getId() != null ? !getId().equals(userData.getId()) : userData.getId() != null) return false;
    if (getFullName() != null ? !getFullName().equals(userData.getFullName()) : userData.getFullName() != null)
      return false;
    if (getFirstName() != null ? !getFirstName().equals(userData.getFirstName()) : userData.getFirstName() != null)
      return false;
    if (getLastName() != null ? !getLastName().equals(userData.getLastName()) : userData.getLastName() != null)
      return false;
    if (getEmail() != null ? !getEmail().equals(userData.getEmail()) : userData.getEmail() != null) return false;
    if (getLastLogin() != null ? !getLastLogin().equals(userData.getLastLogin()) : userData.getLastLogin() != null)
      return false;
    if (getCreated() != null ? !getCreated().equals(userData.getCreated()) : userData.getCreated() != null)
      return false;
    if (getKyc() != null ? !getKyc().equals(userData.getKyc()) : userData.getKyc() != null) return false;
    return getStatus() != null ? getStatus().equals(userData.getStatus()) : userData.getStatus() == null;
  }

  @Override
  public int hashCode() {
    int result = getId() != null ? getId().hashCode() : 0;
    result = 31 * result + (getFullName() != null ? getFullName().hashCode() : 0);
    result = 31 * result + (getFirstName() != null ? getFirstName().hashCode() : 0);
    result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
    result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
    result = 31 * result + (getLastLogin() != null ? getLastLogin().hashCode() : 0);
    result = 31 * result + (getCreated() != null ? getCreated().hashCode() : 0);
    result = 31 * result + (getKyc() != null ? getKyc().hashCode() : 0);
    result = 31 * result + (getStatus() != null ? getStatus().hashCode() : 0);
    return result;
  }
}
