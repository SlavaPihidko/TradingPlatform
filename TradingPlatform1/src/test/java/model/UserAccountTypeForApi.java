package model;

public class UserAccountTypeForApi {
  String name;

  public String getName() {
    return name;
  }

  public UserAccountTypeForApi withName(String name) {
    this.name = name;
    return this;
  }

  @Override
  public String toString() {
    return "UserAccountTypeForApi{" +
            "name='" + name + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof UserAccountTypeForApi)) return false;

    UserAccountTypeForApi that = (UserAccountTypeForApi) o;

    return getName() != null ? getName().equals(that.getName()) : that.getName() == null;
  }

  @Override
  public int hashCode() {
    return getName() != null ? getName().hashCode() : 0;
  }
}
