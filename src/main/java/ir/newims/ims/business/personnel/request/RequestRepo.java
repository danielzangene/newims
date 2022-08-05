package ir.newims.ims.business.personnel.request;

import ir.newims.ims.models.personnel.RequestLog;
import ir.newims.ims.models.personnel.personnel.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RequestRepo extends JpaRepository<RequestLog, Long> {

    Page<RequestLog> findAllByUser_SupervisorOrderByCreationDateTimeDesc(@Param("user") User user, Pageable pageable);

    Long countAllByUser_Supervisor(User user);

    Optional<RequestLog> findByIdAndUser_Supervisor(Long aLong, User user);
}
