package ir.newims.ims.application.utils;

import com.ghasemkiani.util.SimplePersianCalendar;

import java.util.Calendar;
import java.util.Objects;

public class DateUtil {

    public static String getCurrentDate() {
        SimplePersianCalendar persianCalendar = new SimplePersianCalendar();
        Calendar cal = Calendar.getInstance();
        Calendar clonedCal = (Calendar) cal.clone();
        cal.set(Calendar.HOUR_OF_DAY, 6);
        persianCalendar.setTime(cal.getTime());
        int day = persianCalendar.getDateFields().getDay();
        int month = persianCalendar.getDateFields().getMonth() + 1;
        int year = persianCalendar.getDateFields().getYear();
        persianCalendar.setTime(clonedCal.getTime());
        return getDate(year, month, day);
    }

    public static String getCurrentDateTime() {
        SimplePersianCalendar persianCalendar = new SimplePersianCalendar();
        Calendar cal = Calendar.getInstance();
        Calendar clonedCal = (Calendar) cal.clone();
        cal.set(Calendar.HOUR_OF_DAY, 6);
        persianCalendar.setTime(cal.getTime());
        int day = persianCalendar.getDateFields().getDay();
        int month = persianCalendar.getDateFields().getMonth() + 1;
        int year = persianCalendar.getDateFields().getYear();
        persianCalendar.setTime(clonedCal.getTime());
        String persianDate = getDate(year, month, day);

        int hour = persianCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = persianCalendar.get(Calendar.MINUTE);
        int second = persianCalendar.get(Calendar.SECOND);
        int millSecond = persianCalendar.get(Calendar.MILLISECOND);
        String persianTime = getTime(hour, minute, second, millSecond);
        return persianDate + "-" + persianTime;
    }

    private static String getDate(int year, int month, int day) {
        return String.format("%04d/%02d/%02d", year, month, day);
    }

    private static String getTime(int hour, int minute, int second, int millSecond) {
        return String.format("%02d:%02d.%02d.%03d", hour, minute, second, millSecond);
    }

    public static Boolean isAfter(String date, String afterDate) {
        return compareDate(date, afterDate) > 0;
    }

    public static Boolean isBefore(String date, String beforeDate) {
        return compareDate(date, beforeDate) < 0;
    }

    public static Boolean isSame(String date, String otherDate) {
        return compareDate(date, otherDate) == 0;
    }

    public static Boolean isAfterOrSame(String date, String afterOrSameDate) {
        return compareDate(date, afterOrSameDate) >= 0;
    }

    private static int compareDate(String date, String otherDate) {
        if (Objects.nonNull(date) && Objects.nonNull(otherDate) && date.length() == otherDate.length()) {
            return date.compareTo(otherDate);
        } else {
            throw new UnsupportedOperationException();
        }
    }
//1400/12/14-11:24.56.005
}
