package ir.newims.ims.business.personnel.fiscal;

import ir.newims.ims.application.utils.DateUtil;
import ir.newims.ims.business.management.calendar.CalendarUtil;
import ir.newims.ims.business.personnel.fiscal.dto.request.MonthDaysRequest;
import ir.newims.ims.business.personnel.fiscal.dto.response.MonthDayResponse;
import ir.newims.ims.business.personnel.fiscal.dto.response.MonthDaysResponse;
import ir.newims.ims.business.personnel.footwork.FootWorkRepo;
import ir.newims.ims.business.personnel.leaverequest.LeaveRequestRepo;
import ir.newims.ims.business.personnel.personnel.UserService;
import ir.newims.ims.models.management.Calendar;
import ir.newims.ims.models.personnel.footwork.FootWorkLog;
import ir.newims.ims.models.personnel.leaverequest.LeaveRequestLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class FiscalOperational implements FiscalService {

    @Autowired
    FootWorkRepo footWorkRepo;

    @Autowired
    LeaveRequestRepo leaveRequestRepo;

    @Autowired
    UserService userService;

    @Override
    public MonthDaysResponse getMonth(MonthDaysRequest requestDto) {
        List<Calendar> days = getDays(requestDto.getMonthFromNow());
        Calendar startDate  = days.get(0);
        Map<String, Integer> footWorkLogMap = getFootWorkLogMap(startDate);
        Map<String, Integer> leaveRequestLogMap = getLeaveRequestLogMap(startDate);

        Integer monthTotalDay = 0;
        Integer monthTotalLeave = 0;
        Integer standardWorkTime = CalendarUtil.getMonthWorkDaysNumber(startDate.getYear(), startDate.getMonth()) * 528;
        List<MonthDayResponse> daysDiningResponses = new ArrayList<>();
        for (Calendar date : days) {
            Integer totalDay = Objects.nonNull(footWorkLogMap.get(date.getDate())) ? footWorkLogMap.get(date.getDate()) : 0;
            Integer totalLeave = Objects.nonNull(leaveRequestLogMap.get(date.getDate())) ? leaveRequestLogMap.get(date.getDate()) : 0;
            monthTotalDay += totalDay;
            monthTotalLeave += totalLeave;
            MonthDayResponse monthDayResponse = new MonthDayResponse(
                    date.getDate(),
                    date.getOff(),
                    DateUtil.getFormattedDate(date.getDate(), date.getDayOfWeek()),
                    getFormattedTime(totalDay),
                    getFormattedTime(totalLeave)
            );
            daysDiningResponses.add(monthDayResponse);
        }
        return new MonthDaysResponse(
                DateUtil.getPersianMonth(startDate.getDate()),
                getFormattedTime(standardWorkTime),
                getFormattedTime(monthTotalDay),
                getFormattedTime(monthTotalLeave),
                getTotalMonthDays(days.size(), monthTotalDay, monthTotalLeave, standardWorkTime),
                daysDiningResponses
        );
    }

    private List<Calendar> getDays(Integer monthFromNow ) {
        Calendar currentDate = CalendarUtil.getCurrentDate();
        Integer year = currentDate.getYear() + (currentDate.getMonth() + monthFromNow) / 12;
        Integer month = (currentDate.getMonth() + monthFromNow) % 12;
        List<Calendar> allByYearAndMonth = CalendarUtil.findAllByYearAndMonth(year, month);
        return allByYearAndMonth;
    }

    private int getTotalMonthDays(Integer totalMonthDays, Integer monthTotalDay, Integer monthTotalLeave, Integer standardWorkTime) {
        Integer ceil = (int) Math.round(((monthTotalDay + monthTotalLeave) / (double) standardWorkTime) * totalMonthDays);
        return ceil < totalMonthDays ? ceil : totalMonthDays;
    }

    private Map<String, Integer> getFootWorkLogMap(Calendar currentDate) {
        List<FootWorkLog> allThisMonthLog = footWorkRepo.findAllThisMonthLog(
                currentDate.getDate().substring(0, 8),
                userService.getCurrentUser()
        );
        Map<String, List<FootWorkLog>> footWorkLogMap = allThisMonthLog.stream().collect(Collectors.groupingBy(FootWorkLog::getDate));
        Map<String, Integer> footWorkLogTotalMap = new HashMap<>();
        for (String date : footWorkLogMap.keySet()) {
            footWorkLogTotalMap.put(date, getTotalDay(footWorkLogMap.get(date)));
        }
        return footWorkLogTotalMap;
    }

    private Map<String, Integer> getLeaveRequestLogMap(Calendar currentDate) {
        List<LeaveRequestLog> allThisMonthLog = leaveRequestRepo.findAllThisMonthLog(
                currentDate.getDate().substring(0, 8),
                userService.getCurrentUser()
        );
        Map<String, List<LeaveRequestLog>> leaveReqLogMap = allThisMonthLog.stream().collect(Collectors.groupingBy(LeaveRequestLog::getFrom));
        Map<String, Integer> leaveReqTotalMap = new HashMap<>();
        for (String date : leaveReqLogMap.keySet()) {
            List<LeaveRequestLog> leaveRequestLogs = leaveReqLogMap.get(date);
            Integer totalDayRequests = getTotalLeaveReq(leaveRequestLogs);
            leaveReqTotalMap.put(date, totalDayRequests);
        }

        return leaveReqTotalMap;
    }

    private Integer getTotalDay(List<FootWorkLog> footWorkLogs) {
        Integer totalMin = 0;
        if (Objects.nonNull(footWorkLogs) && footWorkLogs.size() > 1) {
            for (int i = 0; i < footWorkLogs.size() && i + 1 < footWorkLogs.size(); i += 2) {
                Integer firstHour = footWorkLogs.get(i).getHour();
                Integer secondHour = footWorkLogs.get(i + 1).getHour();
                Integer firstMin = footWorkLogs.get(i).getMinute();
                Integer secondMin = footWorkLogs.get(i + 1).getMinute();
                totalMin += (secondHour * 60 + secondMin) - (firstHour * 60 + firstMin);

            }
        }
        return totalMin;
    }

    private Integer getTotalLeaveReq(List<LeaveRequestLog> leaveRequestLogs) {
        Integer totalMin = 0;
        if (Objects.nonNull(leaveRequestLogs) && !leaveRequestLogs.isEmpty()) {
            for (LeaveRequestLog leaveRequestLog : leaveRequestLogs) {
                if (!StringUtils.hasText(leaveRequestLog.getFromTime())) {
                    return 528;
                } else {
                    return calculateTotalLeaveReq(leaveRequestLogs);
                }
            }
        }
        return totalMin;
    }

    private Integer calculateTotalLeaveReq(List<LeaveRequestLog> leaveRequestLogs) {
        Integer totalMin = 0;
        if (Objects.nonNull(leaveRequestLogs) && !leaveRequestLogs.isEmpty()) {
            for (LeaveRequestLog leaveRequestLog : leaveRequestLogs) {
                Integer firstHour = Integer.parseInt(leaveRequestLog.getFromTime().substring(0, 2));
                Integer firstMin = Integer.parseInt(leaveRequestLog.getFromTime().substring(2, 4));
                Integer secondHour = Integer.parseInt(leaveRequestLog.getToTime().substring(0, 2));
                Integer secondMin = Integer.parseInt(leaveRequestLog.getToTime().substring(2, 4));
                totalMin += (secondHour * 60 + secondMin) - (firstHour * 60 + firstMin);
            }
        }
        return totalMin;
    }


    String getFormattedTime(Integer hour, Integer minute) {
        return String.format("%02d:%02d", hour, minute);
    }

    String getFormattedTime(Integer totalMin) {
        return getFormattedTime(totalMin / 60, totalMin % 60);
    }

}
