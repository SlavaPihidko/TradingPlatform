package model;

public class UserAssets {
  int asset_id;
  double balance;
  String code;

  public int getAsset_id() {
    return asset_id;
  }

  public UserAssets withAsset_id(int asset_id) {
    this.asset_id = asset_id;
    return this;
  }

  public double getBalance() {
    return balance;
  }

  public UserAssets withBalance(double balance) {
    this.balance = balance;
    return this;
  }

  public String getCode() {
    return code;
  }

  public UserAssets withCode(String code) {
    this.code = code;
    return this;
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

    if (getAsset_id() != that.getAsset_id()) return false;
    if (Double.compare(that.getBalance(), getBalance()) != 0) return false;
    return getCode() != null ? getCode().equals(that.getCode()) : that.getCode() == null;
  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    result = getAsset_id();
    temp = Double.doubleToLongBits(getBalance());
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + (getCode() != null ? getCode().hashCode() : 0);
    return result;
  }
}
