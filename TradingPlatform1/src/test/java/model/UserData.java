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
