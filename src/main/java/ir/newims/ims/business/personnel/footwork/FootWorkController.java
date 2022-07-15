package ir.newims.ims.business.personnel.footwork;

import ir.newims.ims.ResponseConstant;
import ir.newims.ims.ResponseConstantMessage;
import ir.newims.ims.application.utils.DateUtil;
import ir.newims.ims.business.personnel.footwork.dto.request.DeleteFootWorkLogRequest;
import ir.newims.ims.business.personnel.footwork.dto.request.FootWorkDaySheetRequest;
import ir.newims.ims.business.personnel.footwork.dto.request.FootWorkLogRequest;
import ir.newims.ims.business.personnel.footwork.dto.request.FootWorkWeekSheetRequest;
import ir.newims.ims.business.personnel.footwork.dto.response.*;
import ir.newims.ims.exception.DataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/personnel/footwork")
public class FootWorkController {

    @Autowired
    FootWorkService footWorkService;

    @PatchMapping("/week")
    public ResponseEntity<?> getWeekFootWork(@RequestBody() FootWorkWeekSheetRequest request) {
        List<String> week = DateUtil.getWeek(request.getWeekOfToday());
        WeekFootWorksResponse allLogsOfWeek = footWorkService.getAllLogsOfWeek(week);
        return ResponseEntity.ok(new DataResponse(ResponseConstant.SC_OK,
                ResponseConstantMessage.SC_OK,
                allLogsOfWeek));
    }

    @PatchMapping("/day")
    public ResponseEntity<?> getDayFootWork(@RequestBody FootWorkDaySheetRequest request) {
        DayFootWorksResponse dayFootWorksResponse = footWorkService.getAllLogsByDate(request);
        return ResponseEntity.ok(new DataResponse(ResponseConstant.SC_OK,
                ResponseConstantMessage.SC_OK,
                dayFootWorksResponse));
    }

    @PatchMapping("/day/total")
    public ResponseEntity<?> getDayFootWorkStatus() {
        DayFootWorksTotalLogResponse currentDayTotalLog = footWorkService.getCurrentDayTotalLog();
        return ResponseEntity.ok(new DataResponse(ResponseConstant.SC_OK,
                ResponseConstantMessage.SC_OK,
                currentDayTotalLog));
    }

    @PostMapping("/log")
    public ResponseEntity<?> saveOrUpdateFootWorkLog(@Valid @RequestBody FootWorkLogRequest request) {
        DayFootWorksResponse dayFootWorksResponse = footWorkService.saveOrUpdate(request);
        return ResponseEntity.ok(new DataResponse(ResponseConstant.SC_OK,
                ResponseConstantMessage.SC_OK,
                dayFootWorksResponse));
    }

    @DeleteMapping("/log")
    public ResponseEntity<?> deleteFootWorkLog(@RequestBody DeleteFootWorkLogRequest request) {
        DayFootWorksResponse dayFootWorksResponse = footWorkService.delete(request);
        return ResponseEntity.ok(new DataResponse(ResponseConstant.SC_OK,
                ResponseConstantMessage.SC_OK,
                dayFootWorksResponse));
    }

    @PostMapping("/log/current")
    public ResponseEntity<?> logCurrentDateTime() {
        DayFootWorksResponse dayFootWorksResponse = footWorkService.addCurrentLog();
        return ResponseEntity.ok(new DataResponse(ResponseConstant.SC_OK,
                ResponseConstantMessage.SC_OK,
                dayFootWorksResponse));
    }

    @PatchMapping("/log/month/alldays")
    public ResponseEntity<?> monthSummary() {
        MonthFootWorkAllDaysResponse monthAllDaysLog = footWorkService.currentMonthAllDaysLog();
        return ResponseEntity.ok(new DataResponse(ResponseConstant.SC_OK,
                ResponseConstantMessage.SC_OK,
                monthAllDaysLog));
    }

    @PatchMapping("/log/month/summary")
    public ResponseEntity<?> monthAllDay() {
        MonthFootWorkSummaryResponse monthSummary = footWorkService.currentMonthSummary();
        return ResponseEntity.ok(new DataResponse(ResponseConstant.SC_OK,
                ResponseConstantMessage.SC_OK,
                monthSummary));
    }

    @PatchMapping("/log/week/summary")
    public ResponseEntity<?> currentWeekSummary() throws Exception {
        WeekFootWorksSummaryResponse weekSummary = footWorkService.currentWeekSummary();
        return ResponseEntity.ok(new DataResponse(ResponseConstant.SC_OK,
                ResponseConstantMessage.SC_OK,
                weekSummary));
    }

}
