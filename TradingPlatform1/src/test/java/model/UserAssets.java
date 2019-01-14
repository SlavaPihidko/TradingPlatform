package model;

public class UserAssets {
  int asset_id;
  int balance;
  String code;

  public int getAsset_id() {
    return asset_id;
  }

  public void setAsset_id(int asset_id) {
    this.asset_id = asset_id;
  }

  public int getBalance() {
    return balance;
  }

  public void setBalance(int balance) {
    this.balance = balance;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  @Override
  public String toString() {
    return "UserAssets{" +
            "asset_id=" + asset_id +
            ", balance=" + balance +
            ", code='" + code + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof UserAssets)) return false;

    UserAssets that = (UserAssets) o;

    if (getBalance() != that.getBalance()) return false;
    return getCode() != null ? getCode().equals(that.getCode()) : that.getCode() == null;
  }

  @Override
  public int hashCode() {
    int result = getBalance();
    result = 31 * result + (getCode() != null ? getCode().hashCode() : 0);
    return result;
  }
}
