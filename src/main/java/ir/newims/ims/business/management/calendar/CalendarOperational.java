package ir.newims.ims.business.management.calendar;

import ir.newims.ims.business.personnel.footwork.FootWorkCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CalendarOperational implements CalendarService {

    @Autowired
    FootWorkCheck footWorkCheck;

    @Autowired
    CalendarRepo calendarRepo;

}
