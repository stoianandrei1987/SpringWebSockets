package ro.andreistoian.SpringWebSockets.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.andreistoian.SpringWebSockets.models.User;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, String> {

//    @Query(value = "SELECT * FROM users u WHERE u.username:=name LIMIT 1", nativeQuery = true)
    Optional<User> findByUserName(@Param("name") String s);
}
