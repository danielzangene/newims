package ir.newims.ims.business.personnel.leaverequest;

import ir.newims.ims.models.personnel.footwork.FootWorkLog;
import ir.newims.ims.models.personnel.leaverequest.LeaveRequestLog;
import ir.newims.ims.models.personnel.personnel.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeaveRequestRepo extends JpaRepository<LeaveRequestLog, Long> {

    @Query("SELECT leaveRequestLog FROM LeaveRequestLog leaveRequestLog " +
            "WHERE leaveRequestLog.user = ?2 " +
            "AND leaveRequestLog.from like ?1% " +
            "order by leaveRequestLog.creationDateTime ASC")
    List<LeaveRequestLog> findAllThisMonthLog(@Param("date") String date, @Param("user") User user);

    Page<LeaveRequestLog> findAllByUserOrderByCreationDateTimeDesc(@Param("user") User user ,Pageable pageable);

    Long countAllByUser(User user);

    Optional<LeaveRequestLog> findByIdAndUser(Long aLong, User user);
}
