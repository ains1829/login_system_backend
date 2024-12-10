package com.usermanagement.user_login_system.models.users;

import java.sql.Timestamp;
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
// implémentant l'interface UserDetails pour intégrer avec le framework Spring
// Security
public class Users implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  int user_id;
  String email;
  String password_hash;
  Timestamp registration_date;

  public Users() {
  }

  public Users(int user_id, String email, String password_hash, Timestamp registration_date) {
    this.user_id = user_id;
    this.email = email;
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

  public Timestamp getRegistration_date() {
    return registration_date;
  }

  public void setRegistration_date(Timestamp registration_date) {
    this.registration_date = registration_date;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String getUsername() {
    return email;
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
