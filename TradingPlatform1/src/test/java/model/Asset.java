package model;

public class Asset {
  String code;

  public String getCode() {
    return code;
  }

  public Asset withCode(String code) {
    this.code = code;
    return this;
  }

  @Override
  public String toString() {
    return "Asset{" +
            "code='" + code + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Asset)) return false;

    Asset asset = (Asset) o;

    return getCode() != null ? getCode().equals(asset.getCode()) : asset.getCode() == null;
  }

  @Override
  public int hashCode() {
    return getCode() != null ? getCode().hashCode() : 0;
  }
}
