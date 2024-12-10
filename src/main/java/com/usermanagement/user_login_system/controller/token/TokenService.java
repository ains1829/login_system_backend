package com.usermanagement.user_login_system.controller.token;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.usermanagement.user_login_system.models.users.Users;
import com.usermanagement.user_login_system.security.config.JwtService;
import com.usermanagement.user_login_system.services.users.UsersService;
import org.springframework.security.core.Authentication;

@Service
public class TokenService {
  @Autowired
  private JwtService jwt;

  @Autowired
  private UsersService _serviceUser;

  public String getEmailUserByToken() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String jwtToken = (String) authentication.getCredentials();
    String email = jwt.getEmailByToken(jwtToken);
    return email;
  }

  public Users getUsersByToken() {
    String email = getEmailUserByToken();
    Optional<Users> user = _serviceUser.getUserByEmail(email);
    return user.get();
  }

}
