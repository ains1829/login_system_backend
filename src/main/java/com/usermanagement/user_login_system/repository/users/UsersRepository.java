package com.usermanagement.user_login_system.repository.users;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.usermanagement.user_login_system.models.users.Users;

public interface UsersRepository extends JpaRepository<Users, Integer> {
  @Query(value = "select * from users where username =:email_param", nativeQuery = true)
  Optional<Users> getUsersByEmail(@Param("email_param") String email);
}
