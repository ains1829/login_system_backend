package com.usermanagement.user_login_system.controller.other;

public class Map {
  int status;
  Object data;

  public Map() {
  }

  public Map(int status, Object data) {
    this.status = status;
    this.data = data;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }
}
