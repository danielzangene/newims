package ir.newims.ims.business.management.calendar;

import ir.newims.ims.models.management.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarRepo extends JpaRepository<Calendar, Long> {

}
