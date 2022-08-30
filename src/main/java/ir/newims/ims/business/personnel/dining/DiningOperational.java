package ir.newims.ims.business.personnel.dining;

import ir.newims.ims.application.utils.DateUtil;
import ir.newims.ims.business.management.calendar.CalendarUtil;
import ir.newims.ims.business.management.element.TypeRepo;
import ir.newims.ims.business.management.element.dto.ElementDto;
import ir.newims.ims.business.personnel.dining.dto.request.DateMealDiningRequest;
import ir.newims.ims.business.personnel.dining.dto.request.MonthDaysDiningRequest;
import ir.newims.ims.business.personnel.dining.dto.response.DateFoodsResponse;
import ir.newims.ims.business.personnel.dining.dto.response.MonthDayDiningResponse;
import ir.newims.ims.business.personnel.dining.dto.response.MonthDaysDiningResponse;
import ir.newims.ims.models.management.Calendar;
import ir.newims.ims.models.management.Element;
import ir.newims.ims.models.personnel.dining.DiningItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DiningOperational implements DiningService {

    @Autowired
    DiningCheck diningCheck;
    @Autowired
    DiningItemRepo diningRepo;
    @Autowired
    TypeRepo typeRepo;

    @Override
    public MonthDaysDiningResponse getMonth(MonthDaysDiningRequest requestDto) {
        diningCheck.validateMonthThreshold(requestDto);
        Calendar currentDate = CalendarUtil.getCurrentDate();
        Integer year = currentDate.getYear() + (currentDate.getMonth() + requestDto.getMonthFromNow()) / 12;
        Integer month = (currentDate.getMonth() + requestDto.getMonthFromNow()) % 12;
        String currentTime = DateUtil.getCurrentTime();
//        int i = currentTime.compareTo("12:00.00.000")>0:1:0;
        if (month == 0) month++;
        List<Calendar> allByYearAndMonth = CalendarUtil.findAllByYearAndMonth(year, month);
        List<MonthDayDiningResponse> daysDiningResponses = allByYearAndMonth.stream()
                .map(d -> new MonthDayDiningResponse(d.getDate(),
                        DateUtil.getFormattedDate(d.getDate(), d.getDayOfWeek()),
                        DateUtil.isBefore(DateUtil.getCurrentDate(currentTime.compareTo("12:00.00.000") > 0 ? 1 : 0), d.getDate())))
                .collect(Collectors.toList());
        return new MonthDaysDiningResponse(DateUtil.getPersianMonth(month), daysDiningResponses);
    }

    @Override
    public List<DateFoodsResponse> getFoods(DateMealDiningRequest request) {
        Element type = typeRepo.findByCode(DiningCode.MEAL_TYPE).get().getElements().get(request.getFoodType());
        List<DiningItem> foods = diningRepo.findAllByDateAndType(request.getDate(), type);
        return foods.stream().map(item -> new DateFoodsResponse(
                item.getId(),
                item.getName(),
                item.getDate(),
                new ElementDto(item.getType())
        )).collect(Collectors.toList());
    }
}
