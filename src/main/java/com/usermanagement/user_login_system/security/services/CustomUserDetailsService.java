package com.usermanagement.user_login_system.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.usermanagement.user_login_system.models.users.Users;
import com.usermanagement.user_login_system.repository.users.UsersRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
  @Autowired
  private UsersRepository _contextUser;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Users user = _contextUser.getUsersByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("User have not an account"));
    return user;
  }

}
