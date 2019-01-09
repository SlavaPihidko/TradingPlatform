package model;

public class UserDataForApi {

  private String id;
  private String type_id;
  private String status_id;
  private String last_login;
  private String created_at;
  private String email;
  private String username;
  private String phone;


//  public UserDataForApi(String id, String type_id, String status_id,
//                        String last_login, String created_at, String email, String username) {
//    this.id = id;
//    this.type_id = type_id;
//    this.status_id = status_id;
//    this.last_login = last_login;
//    this.created_at = created_at;
//    this.email = email;
//    this.username = username;
//  }

  public String getId() {
    return id;
  }

  public UserDataForApi withId(String id) {
    this.id = id;
    return this;
  }

  public String getType_id() {
    return type_id;
  }

  public UserDataForApi withType_id(String type_id) {
    this.type_id = type_id;
    return this;
  }

  public String getStatus_id() {
    return status_id;
  }

  public UserDataForApi withStatus_id(String status_id) {
    this.status_id = status_id;
    return this;
  }

  public String getLast_login() {
    return last_login;
  }

  public UserDataForApi withLast_login(String last_login) {
    this.last_login = last_login;
    return this;
  }

  public String getCreated_at() {
    return created_at;
  }

  public UserDataForApi withCreated_at(String created_at) {
    this.created_at = created_at;
    return this;
  }

  public String getEmail() {
    return email;
  }

  public UserDataForApi withEmail(String email) {
    this.email = email;
    return this;
  }

  public String getUsername() {
    return username;
  }

  public UserDataForApi withUsername(String username) {
    this.username = username;
    return this;
  }

  @Override
  public String toString() {
    return "UserDataForApi{" +
            "id='" + id + '\'' +
            ", type_id='" + type_id + '\'' +
            ", status_id='" + status_id + '\'' +
            ", last_login='" + last_login + '\'' +
            ", created_at='" + created_at + '\'' +
            ", email='" + email + '\'' +
            ", username='" + username + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof UserDataForApi)) return false;

    UserDataForApi that = (UserDataForApi) o;

    if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
    if (getType_id() != null ? !getType_id().equals(that.getType_id()) : that.getType_id() != null) return false;
    if (getStatus_id() != null ? !getStatus_id().equals(that.getStatus_id()) : that.getStatus_id() != null)
      return false;
    if (getLast_login() != null ? !getLast_login().equals(that.getLast_login()) : that.getLast_login() != null)
      return false;
    if (getCreated_at() != null ? !getCreated_at().equals(that.getCreated_at()) : that.getCreated_at() != null)
      return false;
    if (getEmail() != null ? !getEmail().equals(that.getEmail()) : that.getEmail() != null) return false;
    return getUsername() != null ? getUsername().equals(that.getUsername()) : that.getUsername() == null;
  }

  @Override
  public int hashCode() {
    int result = getId() != null ? getId().hashCode() : 0;
    result = 31 * result + (getType_id() != null ? getType_id().hashCode() : 0);
    result = 31 * result + (getStatus_id() != null ? getStatus_id().hashCode() : 0);
    result = 31 * result + (getLast_login() != null ? getLast_login().hashCode() : 0);
    result = 31 * result + (getCreated_at() != null ? getCreated_at().hashCode() : 0);
    result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
    result = 31 * result + (getUsername() != null ? getUsername().hashCode() : 0);
    return result;
  }
}
