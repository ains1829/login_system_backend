package com.usermanagement.user_login_system.security.config;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.usermanagement.user_login_system.security.services.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomRoleFilter extends OncePerRequestFilter {
  @Autowired
  private JwtService jwtTokenProvider;
  @Autowired
  private CustomUserDetailsService customUserDetailsService;

  // Filtre qui intercepte chaque requête HTTP pour vérifier la présence et la
  // validité d'un token JWT.
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String token = jwtTokenProvider.resolveToken(request);
    if (token != null && jwtTokenProvider.validateToken(token)) {
      String username = jwtTokenProvider.getEmailByToken(token);
      UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
      UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, token,
          userDetails.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(auth);
    }
    filterChain.doFilter(request, response);
  }

}
