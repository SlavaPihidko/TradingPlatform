package model;

public class UserAccountHolderDetails {

  int status_id;

  public int getStatus_id() {
    return status_id;
  }

  public UserAccountHolderDetails withStatus_id(int status_id) {
    this.status_id = status_id;
    return this;
  }

  @Override
  public String toString() {
    return "UserAccountHolderDetails{" +
            "status_id=" + status_id +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof UserAccountHolderDetails)) return false;

    UserAccountHolderDetails that = (UserAccountHolderDetails) o;

    return getStatus_id() == that.getStatus_id();
  }

  @Override
  public int hashCode() {
    return getStatus_id();
  }
}
