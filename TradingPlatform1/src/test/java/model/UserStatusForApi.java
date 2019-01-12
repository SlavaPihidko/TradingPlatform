package model;

public class UserStatusForApi {
  // это KYC
  String name;


//  UserStatusForApi (String name){
//    this.name=name;
//  }

  public UserStatusForApi withName(String name) {
    this.name = name;
    return this;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return "UserStatusForApi{" +
            "name='" + name + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof UserStatusForApi)) return false;

    UserStatusForApi that = (UserStatusForApi) o;

    return getName() != null ? getName().equals(that.getName()) : that.getName() == null;
  }

  @Override
  public int hashCode() {
    return getName() != null ? getName().hashCode() : 0;
  }
}
