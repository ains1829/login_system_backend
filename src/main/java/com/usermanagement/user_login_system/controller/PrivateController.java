package com.usermanagement.user_login_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.usermanagement.user_login_system.controller.token.TokenService;

@RequestMapping("/private_acces")
@RestController
@CrossOrigin("*")
public class PrivateController {
  @Autowired
  private TokenService _serviceToken;

  @GetMapping("/userbytoken")
  @PreAuthorize("hasRole('users')")
  public ResponseEntity<?> Board() {
    try {
      return ResponseEntity.status(200).body(_serviceToken.getUsersByToken());
    } catch (Exception e) {
      return ResponseEntity.status(403).body("UnAuthorized");
    }
  }
}
