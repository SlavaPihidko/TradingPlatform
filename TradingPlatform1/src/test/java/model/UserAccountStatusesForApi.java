package model;

public class UserAccountStatusesForApi {
  String name;

  UserAccountStatusesForApi(String name) {
    this.name=name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "UserAccountStatusesForApi{" +
            "name='" + name + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof UserAccountStatusesForApi)) return false;

    UserAccountStatusesForApi that = (UserAccountStatusesForApi) o;

    return getName() != null ? getName().equals(that.getName()) : that.getName() == null;
  }

  @Override
  public int hashCode() {
    return getName() != null ? getName().hashCode() : 0;
  }
}
