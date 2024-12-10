package com.usermanagement.user_login_system.repository.users;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.usermanagement.user_login_system.models.users.Users;

// Interface de dépôt (repository) pour la gestion des entités 'Users' dans la base de données.
public interface UsersRepository extends JpaRepository<Users, Integer> {
  @Query(value = "select * from users where email =:email_param", nativeQuery = true)
  Optional<Users> getUsersByEmail(@Param("email_param") String email);
}
