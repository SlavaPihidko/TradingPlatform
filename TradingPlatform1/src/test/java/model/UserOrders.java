package model;

public class UserOrders {
  int id;
  String pair;
  String type;
  int user_id;
  double quantity;
  String created_at;
  String status;

  public int getId() {
    return id;
  }

  public UserOrders withId(int id) {
    this.id = id;
    return this;
  }

  public String getPair() {
    return pair;
  }

  public UserOrders withPair(String pair) {
    this.pair = pair;
    return this;
  }

  public String getType() {
    return type;
  }

  public UserOrders withType(String type) {
    this.type = type;
    return this;
  }

  public int getUser_id() {
    return user_id;
  }

  public UserOrders withUser_id(int user_id) {
    this.user_id = user_id;
    return this;
  }

  public double getQuantity() {
    return quantity;
  }

  public UserOrders withQuantity(double quantity) {
    this.quantity = quantity;
    return this;
  }

  public String getCreated_at() {
    return created_at;
  }

  public UserOrders withCreated_at(String created_at) {
    this.created_at = created_at;
    return this;
  }

  public String getStatus() {
    return status;
  }

  public UserOrders withStatus(String status) {
    this.status = status;
    return this;
  }

  @Override
  public String toString() {
    return "UserOrders{" +
            "id=" + id +
            ", pair='" + pair + '\'' +
            ", type='" + type + '\'' +
            ", user_id=" + user_id +
            ", quantity=" + quantity +
            ", created_at='" + created_at + '\'' +
            ", status='" + status + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof UserOrders)) return false;

    UserOrders that = (UserOrders) o;

    if (getId() != that.getId()) return false;
    if (getUser_id() != that.getUser_id()) return false;
    if (Double.compare(that.getQuantity(), getQuantity()) != 0) return false;
    if (getPair() != null ? !getPair().equals(that.getPair()) : that.getPair() != null) return false;
    if (getType() != null ? !getType().equals(that.getType()) : that.getType() != null) return false;
    if (getCreated_at() != null ? !getCreated_at().equals(that.getCreated_at()) : that.getCreated_at() != null)
      return false;
    return getStatus() != null ? getStatus().equals(that.getStatus()) : that.getStatus() == null;
  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    result = getId();
    result = 31 * result + (getPair() != null ? getPair().hashCode() : 0);
    result = 31 * result + (getType() != null ? getType().hashCode() : 0);
    result = 31 * result + getUser_id();
    temp = Double.doubleToLongBits(getQuantity());
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + (getCreated_at() != null ? getCreated_at().hashCode() : 0);
    result = 31 * result + (getStatus() != null ? getStatus().hashCode() : 0);
    return result;
  }
}
