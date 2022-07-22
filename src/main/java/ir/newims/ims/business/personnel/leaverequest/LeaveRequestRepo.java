package ir.newims.ims.business.personnel.leaverequest;

import ir.newims.ims.models.personnel.footwork.LeaveRequestLog;
import ir.newims.ims.models.personnel.personnel.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveRequestRepo extends JpaRepository<LeaveRequestLog, Long> {

    Page<LeaveRequestLog> findAllByUserOrderByCreationDateTime(@Param("user") User user ,Pageable pageable);

    Long countAllByUser(User user);

}
