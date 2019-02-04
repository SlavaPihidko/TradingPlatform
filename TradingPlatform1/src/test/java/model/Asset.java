package model;

public class Asset {
  String code;
  String name;

  public String getCode() {
    return code;
  }

  public Asset withCode(String code) {
    this.code = code;
    return this;
  }

  public String getName() {
    return name;
  }

  public Asset withName(String name) {
    this.name = name;
    return this;
  }

  @Override
  public String toString() {
    return "Asset{" +
            "code='" + code + '\'' +
            ", name='" + name + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Asset)) return false;

    Asset asset = (Asset) o;

    if (getCode() != null ? !getCode().equals(asset.getCode()) : asset.getCode() != null) return false;
    return getName() != null ? getName().equals(asset.getName()) : asset.getName() == null;
  }

  @Override
  public int hashCode() {
    int result = getCode() != null ? getCode().hashCode() : 0;
    result = 31 * result + (getName() != null ? getName().hashCode() : 0);
    return result;
  }
}
