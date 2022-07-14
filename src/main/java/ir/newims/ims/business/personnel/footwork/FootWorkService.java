package ir.newims.ims.business.personnel.footwork;

import ir.newims.ims.business.personnel.footwork.dto.request.DeleteFootWorkLogRequest;
import ir.newims.ims.business.personnel.footwork.dto.request.FootWorkDaySheetRequest;
import ir.newims.ims.business.personnel.footwork.dto.request.FootWorkLogRequest;
import ir.newims.ims.business.personnel.footwork.dto.response.DayFootWorksResponse;
import ir.newims.ims.business.personnel.footwork.dto.response.DayFootWorksTotalLogResponse;
import ir.newims.ims.business.personnel.footwork.dto.response.WeekFootWorksResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FootWorkService {

    DayFootWorksResponse saveOrUpdate(FootWorkLogRequest request);

    DayFootWorksTotalLogResponse getCurrentDayTotalLog();

    DayFootWorksResponse addCurrentLog();

    WeekFootWorksResponse getAllLogsOfWeek(List<String> days);

    DayFootWorksResponse getAllLogsByDate(FootWorkDaySheetRequest request);

    DayFootWorksResponse delete(DeleteFootWorkLogRequest request);
}
