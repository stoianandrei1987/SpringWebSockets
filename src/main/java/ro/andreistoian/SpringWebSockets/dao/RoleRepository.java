package ro.andreistoian.SpringWebSockets.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.andreistoian.SpringWebSockets.models.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query(value = "select * from roles r where r.role_name=:role_name limit 1", nativeQuery = true)
    public Role findRoleByRoleName(@Param("role_name") String roleName);

}
