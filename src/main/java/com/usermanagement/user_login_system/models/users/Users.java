package com.usermanagement.user_login_system.models.users;

import java.time.LocalTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Users implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  int user_id;
  String username;
  String password_hash;
  LocalTime registration_date;

  public Users() {
  }

  public Users(int user_id, String username, String password_hash, LocalTime registration_date) {
    this.user_id = user_id;
    this.username = username;
    this.password_hash = password_hash;
    this.registration_date = registration_date;
  }

  public int getUser_id() {
    return user_id;
  }

  public void setUser_id(int user_id) {
    this.user_id = user_id;
  }

  public String getPassword_hash() {
    return password_hash;
  }

  public void setPassword_hash(String password_hash) {
    this.password_hash = password_hash;
  }

  public LocalTime getRegistration_date() {
    return registration_date;
  }

  public void setRegistration_date(LocalTime registration_date) {
    this.registration_date = registration_date;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Set<GrantedAuthority> authorities = new HashSet<>();
    authorities.add(new SimpleGrantedAuthority("ROLE_users"));
    return authorities;
  }

  @Override
  public String getPassword() {
    return password_hash;
  }
}
