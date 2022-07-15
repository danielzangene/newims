package ir.newims.ims.business.personnel.footwork;

import ir.newims.ims.models.personnel.footwork.FootWorkLog;
import ir.newims.ims.models.personnel.personnel.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FootWorkRepo extends JpaRepository<FootWorkLog, Long> {

    @Query("SELECT footWorkLog FROM FootWorkLog footWorkLog " +
            "WHERE footWorkLog.user = ?2 " +
            "AND footWorkLog.date like ?1% " +
            "order by footWorkLog.time ASC")
    List<FootWorkLog> findAllThisMonthLog(@Param("date") String date, @Param("user") User user);

    @Query("SELECT footWorkLog FROM FootWorkLog footWorkLog " +
            "WHERE footWorkLog.user = ?2 " +
            "AND footWorkLog.date in (?1) " +
            "order by footWorkLog.time ASC")
    List<FootWorkLog> findAllByDatesAndUser(@Param("dates") List<String> dates, @Param("user") User user);

    List<FootWorkLog> findAllByDateAndUserOrderByTime(String date, User user);

    Optional<FootWorkLog> findByIdAndUser(Long id, User user);

}
