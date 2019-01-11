package model;

public class UserAccount {

  String first_name;
  String last_name;
  String dob;
  String country;
  String state;
  String street;
  String post_code;
  String facebook_link;

  public String getFirst_name() {
    return first_name;
  }

  public UserAccount withFirst_name(String first_name) {
    this.first_name = first_name;
    return this;
  }

  public String getLast_name() {
    return last_name;
  }

  public UserAccount withLast_name(String last_name) {
    this.last_name = last_name;
    return this;
  }

  public String getDob() {
    return dob;
  }

  public UserAccount withDob(String dob) {
    this.dob = dob;
    return this;
  }

  public String getCountry() {
    return country;
  }

  public UserAccount withCountry(String country) {
    this.country = country;
    return this;
  }

  public String getState() {
    return state;
  }

  public UserAccount withState(String state) {
    this.state = state;
    return this;
  }

  public String getStreet() {
    return street;
  }

  public UserAccount withStreet(String street) {
    this.street = street;
    return this;
  }

  public String getPost_code() {
    return post_code;
  }

  public UserAccount withPost_code(String post_code) {
    this.post_code = post_code;
    return this;
  }

  public String getFacebook_link() {
    return facebook_link;
  }

  public UserAccount withFacebook_link(String facebook_link) {
    this.facebook_link = facebook_link;
    return this;
  }

  @Override
  public String toString() {
    return "UserAccount{" +
            "first_name='" + first_name + '\'' +
            ", last_name='" + last_name + '\'' +
            ", dob='" + dob + '\'' +
            ", country='" + country + '\'' +
            ", state='" + state + '\'' +
            ", street='" + street + '\'' +
            ", post_code='" + post_code + '\'' +
            ", facebook_link='" + facebook_link + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof UserAccount)) return false;

    UserAccount that = (UserAccount) o;

    if (getFirst_name() != null ? !getFirst_name().equals(that.getFirst_name()) : that.getFirst_name() != null)
      return false;
    if (getLast_name() != null ? !getLast_name().equals(that.getLast_name()) : that.getLast_name() != null)
      return false;
    if (getDob() != null ? !getDob().equals(that.getDob()) : that.getDob() != null) return false;
    if (getCountry() != null ? !getCountry().equals(that.getCountry()) : that.getCountry() != null) return false;
    if (getState() != null ? !getState().equals(that.getState()) : that.getState() != null) return false;
    if (getStreet() != null ? !getStreet().equals(that.getStreet()) : that.getStreet() != null) return false;
    if (getPost_code() != null ? !getPost_code().equals(that.getPost_code()) : that.getPost_code() != null)
      return false;
    return getFacebook_link() != null ? getFacebook_link().equals(that.getFacebook_link()) : that.getFacebook_link() == null;
  }

  @Override
  public int hashCode() {
    int result = getFirst_name() != null ? getFirst_name().hashCode() : 0;
    result = 31 * result + (getLast_name() != null ? getLast_name().hashCode() : 0);
    result = 31 * result + (getDob() != null ? getDob().hashCode() : 0);
    result = 31 * result + (getCountry() != null ? getCountry().hashCode() : 0);
    result = 31 * result + (getState() != null ? getState().hashCode() : 0);
    result = 31 * result + (getStreet() != null ? getStreet().hashCode() : 0);
    result = 31 * result + (getPost_code() != null ? getPost_code().hashCode() : 0);
    result = 31 * result + (getFacebook_link() != null ? getFacebook_link().hashCode() : 0);
    return result;
  }
}
