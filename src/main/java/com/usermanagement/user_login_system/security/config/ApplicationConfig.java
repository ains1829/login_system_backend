package com.usermanagement.user_login_system.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.usermanagement.user_login_system.security.services.CustomUserDetailsService;

@Configuration
public class ApplicationConfig {
  @Autowired
  private CustomUserDetailsService customuser;

  // Fournit un AuthenticationProvider pour gérer l'authentification des
  // utilisateurs.
  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(customuser);
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

  // Fournit un AuthenticationManager pour gérer l'authentification dans
  // l'application.
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  // Fournit un PasswordEncoder pour encoder et vérifier les mots de passe.
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
