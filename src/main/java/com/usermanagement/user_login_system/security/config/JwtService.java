package com.usermanagement.user_login_system.security.config;

import java.security.Key;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtService {

  private String secret_key = "8109fdc8316c72a157d1bf2bae5c6f62f5ae178062d4cb42e165c0bf2cfc7897\"";
  @Value("${jwt.expiration}")
  private long tokenValidityInMilliseconds;

  @SuppressWarnings("deprecation")
  public String getEmailByToken(String jwtToken) {
    return Jwts.parser().setSigningKey(secret_key).parseClaimsJws(jwtToken).getBody().getSubject();
  }

  @SuppressWarnings({ "deprecation", "unchecked" })
  public List<String> getRolesByToken(String jwtToken) {
    Claims claims = Jwts.parser()
        .setSigningKey(secret_key)
        .parseClaimsJws(jwtToken)
        .getBody();
    return claims.get("roles", List.class);
  }

  public String createToken(String username, List<String> roles) {
    Claims claims = Jwts.claims().setSubject(username);
    claims.put("roles", roles);
    Date now = new Date();
    Date validity = new Date(now.getTime() + tokenValidityInMilliseconds);
    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(validity)
        .signWith(getSignIngKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  private Key getSignIngKey() {
    byte[] keyByte = Decoders.BASE64.decode(secret_key);
    return Keys.hmacShaKeyFor(keyByte);
  }

  @SuppressWarnings("deprecation")
  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(secret_key).parseClaimsJws(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      throw new RuntimeException("Expired or invalid JWT token");
    }
  }

  public String resolveToken(HttpServletRequest req) {
    String bearerToken = req.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }

}
