package com.usermanagement.user_login_system.security.services;

import java.util.HashMap;
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

  // Méthode qui vérifie les informations de connexion d'un utilisateur (email et
  // mot de passe).
  // Elle tente d'authentifier l'utilisateur à l'aide de
  // l'AuthenticationManager.
  public HashMap<String, Object> CheckLogin(String email, String password) throws Exception {
    HashMap<String, Object> map = new HashMap<>();
    try {
      String role = "users";
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
      String token = _ServiceJWT.createToken(email, role);
      map.put("token", token);
      map.put("role", role);
      return map;
    } catch (Exception e) {
      throw new Exception("Wrong password");
    }
  }
}
