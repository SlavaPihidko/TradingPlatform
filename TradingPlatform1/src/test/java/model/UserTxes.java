package model;

public class UserTxes {
  int id;
  String transaction_type;
  int user_id;
  String code;
  double amount;
  String status;
  String created_at;
  Asset asset;
  int position;


  public int getId() {
    return id;
  }

  public UserTxes withId(int id) {
    this.id = id;
    return this;
  }

  public String getTransaction_type() {
    return transaction_type;
  }

  public UserTxes withTransaction_type(String transaction_type) {
    this.transaction_type = transaction_type;
    return this;
  }

  public int getUser_id() {
    return user_id;
  }

  public UserTxes withUser_id(int user_id) {
    this.user_id = user_id;
    return this;
  }

  public double getAmount() {
    return amount;
  }

  public UserTxes withAmount(double amount) {
    this.amount = amount;
    return this;
  }

  public String getStatus() {
    return status;
  }

  public UserTxes withStatus(String status) {
    this.status = status;
    return this;
  }

  public String getCreated_at() {
    return created_at;
  }

  public UserTxes withCreated_at(String created_at) {
    this.created_at = created_at;
    return this;
  }

  public String getCode() {
    return code;
  }

  public UserTxes withCode(String code) {
    this.code = code;
    return this;
  }

  public int getPosition() {
    return position;
  }

  public UserTxes withPosition(int position) {
    this.position = position;
    return this;
  }

  public Asset getAsset() {
    return asset;
  }

  public void setAsset(Asset asset) {
    this.asset = asset;
  }

  @Override
  public String toString() {
    return "UserTxes{" +
            "id=" + id +
            ", transaction_type='" + transaction_type + '\'' +
            ", user_id=" + user_id +
            ", code='" + code + '\'' +
            ", amount=" + amount +
            ", status='" + status + '\'' +
            ", created_at='" + created_at + '\'' +
            ", asset=" + asset +
            ", position=" + position +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof UserTxes)) return false;

    UserTxes userTxes = (UserTxes) o;

    if (getId() != userTxes.getId()) return false;
    if (getUser_id() != userTxes.getUser_id()) return false;
    if (Double.compare(userTxes.getAmount(), getAmount()) != 0) return false;
    if (getTransaction_type() != null ? !getTransaction_type().equals(userTxes.getTransaction_type()) : userTxes.getTransaction_type() != null)
      return false;
    if (getCode() != null ? !getCode().equals(userTxes.getCode()) : userTxes.getCode() != null) return false;
    if (getStatus() != null ? !getStatus().equals(userTxes.getStatus()) : userTxes.getStatus() != null) return false;
    if (getCreated_at() != null ? !getCreated_at().equals(userTxes.getCreated_at()) : userTxes.getCreated_at() != null)
      return false;
    return getAsset() != null ? getAsset().equals(userTxes.getAsset()) : userTxes.getAsset() == null;
  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    result = getId();
    result = 31 * result + (getTransaction_type() != null ? getTransaction_type().hashCode() : 0);
    result = 31 * result + getUser_id();
    result = 31 * result + (getCode() != null ? getCode().hashCode() : 0);
    temp = Double.doubleToLongBits(getAmount());
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + (getStatus() != null ? getStatus().hashCode() : 0);
    result = 31 * result + (getCreated_at() != null ? getCreated_at().hashCode() : 0);
    return result;
  }
}
