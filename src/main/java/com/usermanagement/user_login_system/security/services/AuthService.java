package com.usermanagement.user_login_system.security.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import com.usermanagement.user_login_system.security.config.JwtService;

@Service
public class AuthService {
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private JwtService _ServiceJWT;

  public HashMap<String, Object> CheckLogin(String email, String password) throws Exception {
    HashMap<String, Object> map = new HashMap<>();
    try {
      List<String> role = new ArrayList<>();
      role.add("users");
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
      String token = _ServiceJWT.createToken(email, role);
      map.put("token", token);
      map.put("role", role.get(0));
      return map;
    } catch (Exception e) {
      throw new Exception("Wrong password");
    }
  }
}
