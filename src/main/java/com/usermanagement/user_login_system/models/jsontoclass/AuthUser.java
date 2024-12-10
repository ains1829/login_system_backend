package com.usermanagement.user_login_system.models.jsontoclass;

public class AuthUser {
  private String email;
  private String password;
  private String confirm_password;

  public AuthUser() {
  }

  public AuthUser(String email, String password, String confim_password) {
    this.email = email;
    this.password = password;
    this.confirm_password = confim_password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getConfirm_password() {
    return confirm_password;
  }

  public void setConfirm_password(String confirm_password) {
    this.confirm_password = confirm_password;
  }

  public boolean PasswordCheked() throws Exception {
    if (password.equals(confirm_password)) {
      return true;
    }
    throw new Exception("Password is not equal");
  }

}
