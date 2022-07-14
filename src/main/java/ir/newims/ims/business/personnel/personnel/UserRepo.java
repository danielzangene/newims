package ir.newims.ims.business.personnel.personnel;

import ir.newims.ims.models.personnel.personnel.User;
import ir.newims.ims.models.personnel.systemaccess.SystemAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> getByUsername(String username);

}
