package com.usermanagement.user_login_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.usermanagement.user_login_system.controller.other.Map;
import com.usermanagement.user_login_system.models.jsontoclass.AuthUser;
import com.usermanagement.user_login_system.security.services.AuthService;
import com.usermanagement.user_login_system.services.users.UsersService;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class MainController {
  @Autowired
  private UsersService UserService;
  @Autowired
  private AuthService _serviceAuth;

  @PostMapping("/createaccount")
  public ResponseEntity<?> CreateAccount(@RequestBody AuthUser user) {
    try {
      return ResponseEntity.ok(new Map(200, UserService.CreateAccount(user)));
    } catch (Exception e) {
      return ResponseEntity.ok(new Map(404, e.getMessage())); // bad_request
    }
  }

  @PostMapping("/authentification")
  public ResponseEntity<?> Login(@RequestBody AuthUser user) {
    try {
      return ResponseEntity.ok(new Map(200, _serviceAuth.CheckLogin(user.getEmail(), user.getPassword())));
    } catch (Exception e) {
      return ResponseEntity.ok(new Map(401, e.getMessage())); // unauthorized
    }
  }
}
