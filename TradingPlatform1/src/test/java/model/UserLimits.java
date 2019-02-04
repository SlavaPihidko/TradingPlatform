package model;

public class UserLimits {

  String name;
  double order_min;
  double exchange;
  double withdraw_min;
  double withdraw_max;
  Asset asset;

  public String getName() {
    return name;
  }

  public UserLimits withName(String name) {
    this.name = name;
    return this;
  }

  public double getOrder_min() {
    return order_min;
  }

  public UserLimits withOrder_min(double order_min) {
    this.order_min = order_min;
    return this;
  }

  public double getExchange() {
    return exchange;
  }

  public UserLimits withExchange(double exchange) {
    this.exchange = exchange;
    return this;
  }

  public double getWithdraw_min() {
    return withdraw_min;
  }

  public UserLimits withWithdraw_min(double withdraw_min) {
    this.withdraw_min = withdraw_min;
    return this;
  }

  public double getWithdraw_max() {
    return withdraw_max;
  }

  public UserLimits withWithdraw_max(double withdraw_max) {
    this.withdraw_max = withdraw_max;
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
    return "UserLimits{" +
            "name='" + name + '\'' +
            ", order_min=" + order_min +
            ", exchange=" + exchange +
            ", withdraw_min=" + withdraw_min +
            ", withdraw_max=" + withdraw_max +
            ", asset=" + asset +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof UserLimits)) return false;

    UserLimits that = (UserLimits) o;

    if (Double.compare(that.getOrder_min(), getOrder_min()) != 0) return false;
    if (Double.compare(that.getExchange(), getExchange()) != 0) return false;
    if (Double.compare(that.getWithdraw_min(), getWithdraw_min()) != 0) return false;
    if (Double.compare(that.getWithdraw_max(), getWithdraw_max()) != 0) return false;
    return getName() != null ? getName().equals(that.getName()) : that.getName() == null;
  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    result = getName() != null ? getName().hashCode() : 0;
    temp = Double.doubleToLongBits(getOrder_min());
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(getExchange());
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(getWithdraw_min());
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(getWithdraw_max());
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    return result;
  }
}
