package com.usermanagement.user_login_system.services.users;

import java.time.LocalTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.usermanagement.user_login_system.models.jsontoclass.AuthUser;
import com.usermanagement.user_login_system.models.users.Users;
import com.usermanagement.user_login_system.repository.users.UsersRepository;

@Service
public class UsersService {
  @Autowired
  private UsersRepository _context_user;
  @Autowired
  private PasswordEncoder passwordEncoder;

  public Optional<Users> getUserByEmail(String email) {
    return _context_user.getUsersByEmail(email);
  }

  public Users CreateAccount(AuthUser new_user) throws Exception {
    Optional<Users> users = getUserByEmail(new_user.getEmail());
    if (users.isPresent()) {
      throw new Exception("This email have already an account");
    }
    new_user.PasswordCheked();
    Users save_user = new Users();
    save_user.setUsername(new_user.getEmail());
    save_user.setPassword_hash(passwordEncoder.encode(new_user.getPassword()));
    save_user.setRegistration_date(LocalTime.now());
    return _context_user.save(save_user);
  }
}
