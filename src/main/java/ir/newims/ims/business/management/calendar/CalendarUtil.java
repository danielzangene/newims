package ir.newims.ims.business.management.calendar;

import ir.newims.ims.models.management.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CalendarUtil {
    private static CalendarRepo calendarRepo;

    @Autowired
    private CalendarUtil(CalendarRepo calendarRepo) {
        CalendarUtil.calendarRepo = calendarRepo;
    }

    public static Optional<Calendar> findByDate(String date) {
        return calendarRepo.findByDate(date);
    }

    public static List<Calendar> findAllByYearAndWeek(Integer year, Integer week) {
        return calendarRepo.findAllByYearAndWeek(year, week);
    }

    public static List<String> findAllWeekDates(Integer year, Integer week) {
        return calendarRepo.findAllWeekDates(year, week);
    }

    public static List<String> findAllMonthDates(Integer year, Integer month) {
        return calendarRepo.findAllMonthDates(year, month);
    }
    public static Long getMonthWorkDaysNumber(Integer year, Integer month) {
        return calendarRepo.getMonthWorkDaysNumber(year, month);
    }

    public static List<String> findAllMonthDates(String date) {
        Calendar calDate = CalendarUtil.findByDate(date).get();
        return calendarRepo.findAllMonthDates(calDate.getYear(), calDate.getMonth());
    }
    public static Long getMonthWorkDaysNumber(String date) {
        Calendar calDate = CalendarUtil.findByDate(date).get();
        return calendarRepo.getMonthWorkDaysNumber(calDate.getYear(), calDate.getMonth());
    }

    public static List<String> findAllWeekDates(String date) {
        Calendar calDate = CalendarUtil.findByDate(date).get();
        return CalendarUtil.findAllWeekDates(calDate.getYear(), calDate.getWeek());
    }

    public static Calendar findAllWeekDates(Calendar calendar) {
        return calendarRepo.save(calendar);
    }


}
