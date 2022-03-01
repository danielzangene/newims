package ir.newims.ims.security.repository;

import ir.newims.ims.security.models.SystemAccess;
import ir.newims.ims.security.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SystemAccessRepository extends JpaRepository<SystemAccess, Long> {
    @Query("SELECT a FROM User u " +
            "INNER JOIN u.accessList AS a " +
            "WHERE u = ?1 AND a = ?2 " +
            "")
    List<SystemAccess> userHasAccess(@Param("user") User user, @Param("systemAccess") SystemAccess systemAccess);
    Optional<SystemAccess> findByMethodAndRequestUri(String systemMethod, String systemRequestUri);
}
