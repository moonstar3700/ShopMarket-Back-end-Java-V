package shop.domain.service;

import jdk.dynalink.linker.LinkerServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import shop.domain.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
     List<User> findAll();

     boolean existsByEmail(String email);
     boolean existsByUsername(String username);


     @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM User u WHERE u.username = :username AND u.id <> :id")
     boolean existsByUsernameAndExcludeId(@Param("username") String username, @Param("id") Long id);

     @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM User u WHERE u.email = :email AND u.id <> :id")
     boolean existsByEmailAndExcludeId(@Param("email") String email, @Param("id") Long id);
}
