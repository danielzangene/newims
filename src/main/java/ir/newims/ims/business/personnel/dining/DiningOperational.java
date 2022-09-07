package ir.newims.ims.business.personnel.dining;

import ir.newims.ims.application.utils.DateUtil;
import ir.newims.ims.business.management.calendar.CalendarUtil;
import ir.newims.ims.business.management.element.TypeRepo;
import ir.newims.ims.business.personnel.dining.dto.request.DateMealDiningRequest;
import ir.newims.ims.business.personnel.dining.dto.request.MonthDaysDiningRequest;
import ir.newims.ims.business.personnel.dining.dto.response.DateFoodResponse;
import ir.newims.ims.business.personnel.dining.dto.response.DateFoodsResponse;
import ir.newims.ims.business.personnel.dining.dto.response.MonthDayDiningResponse;
import ir.newims.ims.business.personnel.dining.dto.response.MonthDaysDiningResponse;
import ir.newims.ims.business.personnel.personnel.UserService;
import ir.newims.ims.models.management.Calendar;
import ir.newims.ims.models.management.Element;
import ir.newims.ims.models.personnel.dining.DiningItem;
import ir.newims.ims.models.personnel.dining.ReserveItem;
import ir.newims.ims.models.personnel.personnel.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DiningOperational implements DiningService {

    @Autowired
    DiningCheck diningCheck;
    @Autowired
    UserService userService;
    @Autowired
    DiningItemRepo diningRepo;
    @Autowired
    ReserveItemRepo reserveItemRepo;
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
        List<String> reservedDates = reserveItemRepo.findReservedDates(year, month, userService.getCurrentUser());
        return new MonthDaysDiningResponse(DateUtil.getPersianMonth(month), daysDiningResponses, reservedDates);
    }

    @Override
    public DateFoodsResponse getFoods(DateMealDiningRequest request) {
        User currentUser = userService.getCurrentUser();
        Element type = typeRepo.findByCode(DiningCode.MEAL_TYPE).get().getElements().get(request.getFoodType());
        List<DiningItem> foods = diningRepo.findAllByDateAndType(request.getDate(), type);
        List<DateFoodResponse> foodsResponse = foods.stream().map(item -> new DateFoodResponse(item)).collect(Collectors.toList());
        Optional<ReserveItem> reservedItem = reserveItemRepo.findReserveItemByUserAndDiningItemIn(currentUser, foods);
        DateFoodResponse reservedFoodResponse = reservedItem.isPresent() ? new DateFoodResponse(reservedItem.get().getDiningItem()) : null;
        return new DateFoodsResponse(foodsResponse, reservedFoodResponse);
    }

    @Override
    public DateFoodsResponse reserveFood(DateMealDiningRequest request) {
        if (Objects.nonNull(request.getId())) {
            doReserveFood(request);
        } else {
            removeReservedFood(request);
        }
        return getFoods(request);
    }

    @Transactional
    void doReserveFood(DateMealDiningRequest request) {
        Optional<DiningItem> foodItem = diningRepo.findById(request.getId());
        List<ReserveItem> reserveItems = reserveItemRepo.reserveItemByDiningItemDate(foodItem.get().getDate(),
                foodItem.get().getType());
        ReserveItem reserveItem;
        if (reserveItems.isEmpty()) {
            reserveItem = new ReserveItem().setDiningItem(foodItem.get()).setUser(userService.getCurrentUser());
        } else {
            reserveItem = reserveItems.get(0);
            reserveItem.setDiningItem(foodItem.get());
        }
        reserveItemRepo.save(reserveItem);
    }

    @Transactional
    void removeReservedFood(DateMealDiningRequest request) {
        Element type = typeRepo.findByCode(DiningCode.MEAL_TYPE).get().getElements().get(request.getFoodType());
        List<ReserveItem> reserveItems = reserveItemRepo.reserveItemByDiningItemDate(request.getDate(), type);
        reserveItemRepo.deleteAll(reserveItems);
    }
}
