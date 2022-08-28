package ir.newims.ims.business.personnel.footwork;

import ir.newims.ims.ResponseConstant;
import ir.newims.ims.ResponseConstantMessage;
import ir.newims.ims.application.utils.DateUtil;
import ir.newims.ims.business.management.calendar.CalendarUtil;
import ir.newims.ims.business.management.element.ElementRepo;
import ir.newims.ims.business.personnel.footwork.dto.request.DeleteFootWorkLogRequest;
import ir.newims.ims.business.personnel.footwork.dto.request.FootWorkDaySheetRequest;
import ir.newims.ims.business.personnel.footwork.dto.request.FootWorkLogRequest;
import ir.newims.ims.business.personnel.footwork.dto.request.FootWorkWeekSheetRequest;
import ir.newims.ims.business.personnel.footwork.dto.response.*;
import ir.newims.ims.business.personnel.personnel.UserService;
import ir.newims.ims.business.personnel.request.RequestCode;
import ir.newims.ims.business.personnel.request.RequestService;
import ir.newims.ims.exception.BusinessException;
import ir.newims.ims.models.management.Calendar;
import ir.newims.ims.models.personnel.footwork.FootWorkLog;
import ir.newims.ims.models.personnel.personnel.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class FootWorkOperational implements FootWorkService {

    @Autowired
    FootWorkCheck footWorkCheck;

    @Autowired
    RequestService requestService;
    @Autowired
    ElementRepo elementRepo;

    @Autowired
    FootWorkRepo footWorkRepo;

    @Autowired
    UserService userService;


    @Override
    public DayFootWorksResponse saveOrUpdate(FootWorkLogRequest request) {
        footWorkCheck.validateFootWorkLogRequest(request);

        FootWorkLog footWorkLog;
        if (Objects.nonNull(request.getId()) && request.getId() != 0)
            footWorkLog = footWorkRepo.findById(request.getId()).orElseThrow(() -> {
                throw new BusinessException(ResponseConstant.INVALID_FOOT_WORK_LOG_ID, ResponseConstantMessage.INVALID_FOOT_WORK_LOG_ID);
            });
        else
            footWorkLog = new FootWorkLog();

        BeanUtils.copyProperties(request, footWorkLog);
        User currentUser = userService.getCurrentUser();
        footWorkLog.setUser(currentUser);
        footWorkLog.setType(elementRepo.findByCode(RequestCode.FOOT_WORK_LOG_TYPE).get());
        footWorkLog.setStatus(elementRepo.findByCode(RequestCode.REGISTERED_REQUEST_STATUS).get());
        footWorkRepo.save(footWorkLog);
        requestService.pushNotifReq(footWorkLog);
        List<FootWorkLog> footWorkLogs = footWorkRepo.findAllByDateAndUserOrderByTime(request.getDate(), currentUser);
        return new DayFootWorksResponse(request.getDate(), getTotalDay(footWorkLogs), null, footWorkLogs);
    }

    private String getTotalDay(List<FootWorkLog> footWorkLogs) {
        int totalMin = 0;
        int totalHour = 0;
        if (footWorkLogs.size() > 1) {
            for (int i = 0; i < footWorkLogs.size() && i + 1 < footWorkLogs.size(); i += 2) {
                totalHour += Math.abs(Integer.valueOf(footWorkLogs.get(i).getTime().substring(0, 2)) - Integer.valueOf(footWorkLogs.get(i + 1).getTime().substring(0, 2)));
                totalMin += Math.abs(Integer.valueOf(footWorkLogs.get(i).getTime().substring(2, 4)) - Integer.valueOf(footWorkLogs.get(i + 1).getTime().substring(2, 4)));
            }
        }
        totalHour += totalMin / 60;
        totalMin %= 60;
        return String.format("%02d%02d", totalHour, totalMin);
    }

    private String getTotaDays(List<String> totalDays) {
        int totalMin = 0;
        int totalHour = 0;
        if (!totalDays.isEmpty()) {
            for (String totalDay : totalDays) {
                totalHour += Math.abs(Integer.valueOf(totalDay.substring(0, 2)));
                totalMin += Math.abs(Integer.valueOf(totalDay.substring(2, 4)));
            }
        }
        totalHour += totalMin / 60;
        totalMin %= 60;
        return String.format("%02d%02d", totalHour, totalMin);
    }

    @Override
    public DayFootWorksTotalLogResponse getCurrentDayTotalLog() {
        String totalDay;
        Boolean isCounting = false;
        String currentDate = DateUtil.getCurrentDate();
        List<FootWorkLog> allLogsByDate = getAllLogsByDate(currentDate);
        if (allLogsByDate.size() % 2 != 0) {
            FootWorkLog footWorkLog = new FootWorkLog();
            String currentTime = DateUtil.getCurrentTime();
            footWorkLog.setDate(currentDate);
            footWorkLog.setTime(currentTime.substring(0, 5).replaceAll(":", ""));
            allLogsByDate.add(footWorkLog);
            totalDay = getTotalDay(allLogsByDate);
            isCounting = true;
        } else {
            totalDay = getTotalDay(allLogsByDate);
        }
        return new DayFootWorksTotalLogResponse(totalDay, isCounting);
    }

    @Override
    public DayFootWorksResponse addCurrentLog() {
        FootWorkLogRequest request = new FootWorkLogRequest();
        String currentDate = DateUtil.getCurrentDate();
        String currentTime = DateUtil.getCurrentTime();
        request.setDate(currentDate);
        request.setTime(currentTime.substring(0, 5).replaceAll(":", ""));
        return saveOrUpdate(request);
    }

    @Override
    public WeekFootWorksResponse getAllLogsOfWeek(FootWorkWeekSheetRequest request) {

        String currentDate = DateUtil.getCurrentDate();
        Calendar date = CalendarUtil.findByDate(currentDate).get();
        List<String> days = CalendarUtil.findAllWeekDates(date.getYear(), date.getWeek() + request.getWeekOfToday());
        User currentUser = userService.getCurrentUser();
        List<FootWorkLog> footWorkLogs = footWorkRepo.findAllByDatesAndUser(days, currentUser);
        Map<String, List<FootWorkLog>> footWorkLogMap = footWorkLogs.stream().collect(Collectors.groupingBy(FootWorkLog::getDate));
        List<DayFootWorksResponse> daysFootWork = new ArrayList<>();
        String formattedWeek = DateUtil.getFormattedWeek(days);
        for (int i = 0; i < days.size(); i++) {
            String key = days.get(i);
            List<FootWorkLog> footWorkLogsOfDay = footWorkLogMap.containsKey(key) ? footWorkLogMap.get(key) : new ArrayList<>();
            daysFootWork.add(new DayFootWorksResponse(key, getTotalDay(footWorkLogsOfDay), DateUtil.getFormattedDate(key, i), footWorkLogsOfDay));
        }

        return new WeekFootWorksResponse(
                formattedWeek,
                DateUtil.getCurrentDate(),
                getTotaDays(daysFootWork.stream().map(DayFootWorksResponse::getTotalDay).collect(Collectors.toList())),
                daysFootWork);

    }

    private List<FootWorkLog> getAllLogsByDate(String date) {
        User currentUser = userService.getCurrentUser();
        List<FootWorkLog> footWorkLogs = footWorkRepo.findAllByDateAndUserOrderByTime(date, currentUser);
        return footWorkLogs;
    }

    @Override
    public DayFootWorksResponse getAllLogsByDate(FootWorkDaySheetRequest request) {
        List<FootWorkLog> allLogsByDate = getAllLogsByDate(request.getDate());
        return new DayFootWorksResponse(request.getDate(), getTotalDay(allLogsByDate), null, allLogsByDate);
    }

    @Override
    public DayFootWorksResponse delete(DeleteFootWorkLogRequest request) {
        User currentUser = userService.getCurrentUser();
        FootWorkLog footWorkLog = footWorkRepo.findByIdAndUser(request.getId(), currentUser).orElseThrow(() -> {
            throw new BusinessException(ResponseConstant.INVALID_FOOT_WORK_LOG_ID, ResponseConstantMessage.INVALID_FOOT_WORK_LOG_ID);
        });
        footWorkRepo.delete(footWorkLog);
        List<FootWorkLog> footWorkLogs = footWorkRepo.findAllByDateAndUserOrderByTime(footWorkLog.getDate(), currentUser);
        return new DayFootWorksResponse(footWorkLog.getDate(), getTotalDay(footWorkLogs), null, footWorkLogs);
    }

    @Override
    public MonthFootWorkSummaryResponse currentMonthSummary() {
        String monthTotalWorkTime = monthTotalWorkTime(DateUtil.getCurrentDate());
        return new MonthFootWorkSummaryResponse(
                DateUtil.getPersianMonth(DateUtil.getCurrentDate()),
                monthTotalWorkTime,
                "0100",
                "0200",
                "0300"
        );
    }

    @Override
    public MonthFootWorkAllDaysResponse currentMonthAllDaysLog() {
        List<String> monthDaysFootWork = getMonthAllWorkTime(DateUtil.getCurrentDate());
        List<String> previousMonthDaysFootWork = monthDaysFootWork.stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());
        previousMonthDaysFootWork.remove(previousMonthDaysFootWork.size() - 1);
        if (monthDaysFootWork.size() > previousMonthDaysFootWork.size()) previousMonthDaysFootWork.add("0000");
        if (monthDaysFootWork.size() < previousMonthDaysFootWork.size()) monthDaysFootWork.add("0000");
        return new MonthFootWorkAllDaysResponse(
                Arrays.asList(
                        new MonthFootWorkAllDaysElementResponse(
                                "ماه جاری",
                                monthDaysFootWork
                        ),
                        new MonthFootWorkAllDaysElementResponse(
                                "ماه قبل",
                                previousMonthDaysFootWork
                        )
                )
        );
    }

    private String monthTotalWorkTime(String dayOfMonth) {
        List<String> daysFootWork = getMonthAllWorkTime(dayOfMonth);
        return getTotaDays(daysFootWork);
    }

    private List<String> getMonthAllWorkTime(String dayOfMonth) {
        List<String> month = CalendarUtil.findAllMonthDates(DateUtil.getCurrentDate());
        String substring = dayOfMonth.substring(0, 8);
        List<FootWorkLog> allThisMonthLog = footWorkRepo.findAllThisMonthLog(substring, userService.getCurrentUser());
        Map<String, List<FootWorkLog>> footWorkLogMap = allThisMonthLog.stream().collect(Collectors.groupingBy(FootWorkLog::getDate));
        List<String> daysFootWork = new ArrayList<>();

        for (String key : month) {
            List<FootWorkLog> footWorkLogsOfDay = footWorkLogMap.containsKey(key) ? footWorkLogMap.get(key) : new ArrayList<>();
            daysFootWork.add(getTotalDay(footWorkLogsOfDay));
        }
        return daysFootWork;
    }

    @Override
    public WeekFootWorksSummaryResponse currentWeekSummary() throws Exception {
        List<String> days = CalendarUtil.findAllWeekDates(DateUtil.getCurrentDate());
        User currentUser = userService.getCurrentUser();
        List<FootWorkLog> footWorkLogs = footWorkRepo.findAllByDatesAndUser(days, currentUser);
        Map<String, List<FootWorkLog>> footWorkLogMap = footWorkLogs.stream().collect(Collectors.groupingBy(FootWorkLog::getDate));
        List<DayFootWorkSummaryResponse> daysFootWork = new ArrayList<>();
        String formattedWeek = DateUtil.getFormattedWeek(days);
        for (int i = 0; i < days.size(); i++) {
            String key = days.get(i);
            List<FootWorkLog> footWorkLogsOfDay = footWorkLogMap.containsKey(key) ? footWorkLogMap.get(key) : new ArrayList<>();
            daysFootWork.add(new DayFootWorkSummaryResponse(
                    key,
                    DateUtil.getFormattedDateWithoutName(key),
                    DateUtil.getPersianDaysOfWeek(i),
                    false,
                    getTotalDay(footWorkLogsOfDay),
                    "0000",
                    footWorkLogsOfDay.size() % 2 != 0,
                    footWorkLogsOfDay.isEmpty()

            ));
        }
        return new WeekFootWorksSummaryResponse(
                formattedWeek,
                DateUtil.getCurrentDate(),
                daysFootWork
        );

    }
}
