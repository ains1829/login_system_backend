package com.usermanagement.user_login_system;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.usermanagement.user_login_system.models.jsontoclass.AuthUser;
import com.usermanagement.user_login_system.models.users.Users;
import com.usermanagement.user_login_system.repository.users.UsersRepository;
import com.usermanagement.user_login_system.security.config.JwtService;
import com.usermanagement.user_login_system.security.services.AuthService;
import com.usermanagement.user_login_system.services.users.UsersService;

@SpringBootTest
class UserLoginSystemApplicationTests {
	@Mock
	private AuthenticationManager authenticationManager;
	@Mock
	private JwtService _jwt_Service;
	@InjectMocks
	private AuthService authService;
	@InjectMocks
	private UsersService usersService;
	@Mock
	private UsersRepository userrepository;
	@Mock
	private PasswordEncoder passwordEncoder;

	public UserLoginSystemApplicationTests() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void contextLoads() {
	}

	@Test
	void testCheckLogin_Success() throws Exception {
		// Arrange
		String email = "test@example.com";
		String password = "password123";
		String role = "users";
		String token = "mocked_jwt_token";
		// Simulez l'authentification réussie
		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
				.thenReturn(null);
		when(_jwt_Service.createToken(email, role)).thenReturn(token);
		// Act
		HashMap<String, Object> result = authService.CheckLogin(email, password);
		// Assert
		assertNotNull(result);
		assertEquals(token, result.get("token"));
		assertEquals(role, result.get("role"));
		verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
		verify(_jwt_Service, times(1)).createToken(email, role); // verifie pour une 1fois d'appel
	}

	@Test
	void testCheckLogin_Failure() {
		// Arrange
		String email = "test@example.com";
		String password = "wrongpassword";
		doThrow(new BadCredentialsException("Bad credentials")).when(authenticationManager)
				.authenticate(any(UsernamePasswordAuthenticationToken.class));
		Exception exception = assertThrows(Exception.class, () -> {
			authService.CheckLogin(email, password);
		});
		assertEquals("Wrong password", exception.getMessage());
		verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
		verifyNoInteractions(_jwt_Service); // Aucun appel
	}

	@Test
	void testCreateAccount_Success() throws Exception {
		AuthUser new_user = new AuthUser("test@example.com", "password123", "password123");
		Users savedUser = new Users();
		savedUser.setEmail(new_user.getEmail());
		savedUser.setPassword_hash("encoded_password");
		savedUser.setRegistration_date(Timestamp.valueOf(LocalDateTime.now()));
		when(userrepository.getUsersByEmail(new_user.getEmail())).thenReturn(Optional.empty()); // Aucun utilisateur
		when(passwordEncoder.encode(new_user.getPassword())).thenReturn("encoded_password"); // Mot de passe encodé
		when(userrepository.save(any(Users.class))).thenReturn(savedUser); // Simule l'enregistrement
		Users result = usersService.CreateAccount(new_user);
		assertNotNull(result);
		assertEquals(new_user.getEmail(), result.getEmail());
		assertEquals("encoded_password", result.getPassword_hash());
		verify(userrepository, times(1)).getUsersByEmail(new_user.getEmail());
		verify(passwordEncoder, times(1)).encode(new_user.getPassword());
		verify(userrepository, times(1)).save(any(Users.class));
	}

	@Test
	void testCreateAccount_EmailAlreadyExists() {
		AuthUser new_user = new AuthUser("test@example.com", "password123", "password123");
		Users existingUser = new Users();
		existingUser.setEmail(new_user.getEmail());
		when(userrepository.getUsersByEmail(new_user.getEmail())).thenReturn(Optional.of(existingUser));
		Exception exception = assertThrows(Exception.class, () -> usersService.CreateAccount(new_user));
		assertEquals("This email have already an account", exception.getMessage());
		verify(passwordEncoder, never()).encode(anyString());
		verify(userrepository, never()).save(any(Users.class));
	}
}
