package ir.newims.ims.business;

import ir.newims.ims.ResponseConstant;
import ir.newims.ims.ResponseConstantMessage;
import ir.newims.ims.application.utils.DateUtil;
import ir.newims.ims.business.management.calendar.CalendarRepo;
import ir.newims.ims.business.management.element.ElementRepo;
import ir.newims.ims.business.management.element.TypeRepo;
import ir.newims.ims.business.management.element.dto.ElementDto;
import ir.newims.ims.business.management.element.dto.TypeDto;
import ir.newims.ims.business.personnel.dining.DiningCode;
import ir.newims.ims.business.personnel.dining.DiningItemRepo;
import ir.newims.ims.business.personnel.leaverequest.LeaveRequestCode;
import ir.newims.ims.business.personnel.request.RequestCode;
import ir.newims.ims.business.tmp.StaticValues;
import ir.newims.ims.exception.BusinessException;
import ir.newims.ims.filter.filter.AccessFilter;
import ir.newims.ims.models.management.Calendar;
import ir.newims.ims.models.management.Element;
import ir.newims.ims.models.management.Type;
import ir.newims.ims.models.personnel.dining.DiningItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;


@Component
public class Initializer {
    private static final Logger logger = LoggerFactory.getLogger(AccessFilter.class);

    @Autowired
    ElementRepo elementRepo;
    @Autowired
    DiningItemRepo diningRepo;
    @Autowired
    TypeRepo typeRepo;
    @Autowired
    CalendarRepo calendarRepo;

    private List<ElementDto> elements = new LinkedList<>();
    private List<TypeDto> types = new LinkedList<>();


    @PostConstruct
    private void init() {
        addElement();
        addTypes();

        persistType();
        persistElement();

        initCalendar(1400,1);
        initCalendar(1401,2);
        initCalendar(1402,3);

//        initShaHrivarDiningItems();

    }

    private void initShaHrivarDiningItems() {
        long count = diningRepo.count();
        if (count == 0) {
            Map<String, List<String>> mainMeals = new HashMap<>() {{
                put("1401/06/01", Arrays.asList("چلوجوجه کباب", "کتلت گوشت", "سالادماکارانی"));
                put("1401/06/02", Arrays.asList("عدس پلو", "خوراک کباب لقمه", "سالادسزار"));
                put("1401/06/03", Arrays.asList("چلوجوجه کباب"));
                put("1401/06/04", Arrays.asList("چلوکباب لقمه"));
                put("1401/06/05", Arrays.asList("چلوخورشت قیمه بادمجان", "کوکوسیب زمینی", "سالادیونانی"));
                put("1401/06/06", Arrays.asList("کلم پلو", "شنیسل مرغ", "بشقاب سبزیجات"));
                put("1401/06/07", Arrays.asList("چلوخورشت مسمابامرغ", "خوراک جوجه", "سالادسبز"));
                put("1401/06/08", Arrays.asList("چلو کباب لقمه", "میرزاقاسمی", "سالادسزار"));
                put("1401/06/09", Arrays.asList("ماکارانی", "جوجه چینی", "سالادمرغ وسبزیجات"));
                put("1401/06/10", Arrays.asList("زرشک پلو"));
                put("1401/06/11", Arrays.asList("چلوجوجه کباب"));
                put("1401/06/12", Arrays.asList("چلوخورشت قورمه سبزی", "خوراک جوجه", "سالادکینوا"));
                put("1401/06/13", Arrays.asList("چلوکباب لقمه", "دلمه فلفل بادمجان", "سالادسزار"));
                put("1401/06/14", Arrays.asList("چلوجوجه کباب", "خوراک لقمه", "سالادسبز"));
                put("1401/06/15", Arrays.asList("چلوخورشت بامیه", "خوراک جوجه", "سالادیونانی"));
                put("1401/06/16", Arrays.asList("استامبولی", "لازانیای گوشت", "سالادماکارانی"));
                put("1401/06/17", Arrays.asList("چلوکباب لقمه"));
                put("1401/06/18", Arrays.asList("چلوجوجه کباب"));
                put("1401/06/19", Arrays.asList("چلوخورشت کرفس", "سالادالویه", "سالادکلم بروکلی"));
                put("1401/06/20", Arrays.asList("پلویونانی", "خوراک جوجه", "بشقاب سبزیجات"));
                put("1401/06/21", Arrays.asList("چلوجوجه کباب", "خوراک کباب لقمه", "سالادیونانی"));
                put("1401/06/22", Arrays.asList("خورشت قیمه سیب زمینی", "کوکوسیب زمینی", "سالادمرغ وسبزیجات"));
                put("1401/06/23", Arrays.asList("رشته پلوباگوشت", "خوراک کباب لقمه", "سالادسبز"));
                put("1401/06/24", Arrays.asList("سبزی پلوبامرغ"));
                put("1401/06/25", Arrays.asList("چلوکباب لقمه"));
                put("1401/06/26", Arrays.asList("چلوجوجه کباب"));
                put("1401/06/27", Arrays.asList("زرشک پلوبامرغ", "بادمجان شکم پر", "سالادسزار"));
                put("1401/06/28", Arrays.asList("لوبیاپلو", "خوراک کباب لقمه", "سالادکینوا"));
                put("1401/06/29", Arrays.asList("چلوجوجه کباب", "کشک بادمجان", "بشقاب سبزیجات"));
                put("1401/06/30", Arrays.asList("چلوکباب لقمه", "دیزی", "سالادکلم بروکلی"));
                put("1401/06/31", Arrays.asList("باقالی پلوبامرغ"));
            }};

            Element mainMealType = elementRepo.findByCode(DiningCode.MAIN_MEAL_TYPE).get();

            for (String date : mainMeals.keySet()) {
                for (String mealName : mainMeals.get(date)) {
                    diningRepo.save(new DiningItem().setDate(date).setName(mealName).setType(mainMealType));
                }
            }

            Element drinkType = elementRepo.findByCode(DiningCode.DRINK_MEAL_TYPE).get();
            List<String> drinks = Arrays.asList("دلستر", "نوشابه", "دوع");
            for (String date : mainMeals.keySet()) {
                for (String drink : drinks) {
                    diningRepo.save(new DiningItem().setDate(date).setName(drink).setType(drinkType));
                }
            }

            Element dessertType = elementRepo.findByCode(DiningCode.DESSERT_MEAL_TYPE).get();
            List<String> desserts = Arrays.asList("ژله", "ماست", "سالاد");
            for (String date : mainMeals.keySet()) {
                for (String dessert : desserts) {
                    diningRepo.save(new DiningItem().setDate(date).setName(dessert).setType(dessertType));
                }
            }
        }
    }

    private void initCalendar(Integer year, Integer firstDayOfYearWeekNumber) {
        boolean notInitializeYet = calendarRepo.findByDate(DateUtil.getDate(year, 1, 1)).isEmpty();
        if (notInitializeYet) {
            for (int month = 1; month <= 12; month++) {
                for (int day = 1; day <= 31; day++) {
                    if (month > 6 && day == 31) continue;
                    String date = DateUtil.getDate(year, month, day);
                    Integer dayOfWeek = firstDayOfYearWeekNumber % 7;
                    Integer week = (firstDayOfYearWeekNumber / 7) + 1;
                    calendarRepo.save(
                            new Calendar()
                                    .setYear(year)
                                    .setMonth(month)
                                    .setDay(day)
                                    .setDate(date)
                                    .setDayOfWeek(dayOfWeek)
                                    .setWeek(week)
                                    .setOff(dayOfWeek==6)
                    );
                    firstDayOfYearWeekNumber++;
                }
            }
            calendarRepo.delete(calendarRepo.findByDate(DateUtil.getDate(year, 12, 30)).get());
            initOffOfficialDays(StaticValues.offOfficialDays.get(year));
        }
    }

    private void initOffOfficialDays(List<String> days) {
        if (Objects.nonNull(days))
            days.parallelStream().forEach(day ->
                    calendarRepo.findByDate(day).ifPresent(date -> {
                        calendarRepo.save(date.setOff(Boolean.TRUE));
                    })
            );
    }


    private void addTypes() {
        types.add(new TypeDto(LeaveRequestCode.LEAVE_REQUEST_TYPE));
        types.add(new TypeDto(RequestCode.REQUEST_STATUS));
        types.add(new TypeDto(RequestCode.REQUEST_LOG_TYPE));
        types.add(new TypeDto(DiningCode.MEAL_TYPE));
    }

    private void addElement() {
        elements.add(new ElementDto("استحقاقی", LeaveRequestCode.ENTITLEMENT_REQUEST_TYPE, LeaveRequestCode.LEAVE_REQUEST_TYPE));
        elements.add(new ElementDto("جهت اطلاع", LeaveRequestCode.FOR_INFORMATION_REQUEST_TYPE, LeaveRequestCode.LEAVE_REQUEST_TYPE));
        elements.add(new ElementDto("استعلاجی", LeaveRequestCode.ILLNESS_REQUEST_TYPE, LeaveRequestCode.LEAVE_REQUEST_TYPE));

        elements.add(new ElementDto("ثبت شده", RequestCode.REGISTERED_REQUEST_STATUS, RequestCode.REQUEST_STATUS));
        elements.add(new ElementDto("تایید شده", RequestCode.CONFIRMED_REQUEST_STATUS, RequestCode.REQUEST_STATUS));
        elements.add(new ElementDto("رد شده", RequestCode.REJECTED_REQUEST_STATUS, RequestCode.REQUEST_STATUS));

        elements.add(new ElementDto("مرخصی", RequestCode.LEAVE_REQUEST_LOG_TYPE, RequestCode.REQUEST_LOG_TYPE));
        elements.add(new ElementDto("ورود/خروج", RequestCode.FOOT_WORK_LOG_TYPE, RequestCode.REQUEST_LOG_TYPE));

        elements.add(new ElementDto("منو اصلی", DiningCode.MAIN_MEAL_TYPE, DiningCode.MEAL_TYPE));
        elements.add(new ElementDto("دسر", DiningCode.DESSERT_MEAL_TYPE, DiningCode.MEAL_TYPE));
        elements.add(new ElementDto("نوشیدنی", DiningCode.DRINK_MEAL_TYPE, DiningCode.MEAL_TYPE));

    }

    private void persistType() {
        for (TypeDto tp : types) {
            try {
                Optional<Type> typeOptional = typeRepo.findByCode(tp.getCode());
                if (!typeOptional.isPresent()) {
                    Type type = new Type();
                    type.setCode(tp.getCode());
                    typeRepo.save(type);
                    logger.info("TYPE PERSISTS: " + type.getCode());
                }
            } catch (Exception e) {
                logger.error("TYPE NOT PERSISTS: " + tp.getCode());
            }
        }

    }

    private void persistElement() {
        for (ElementDto elm : elements) {
            try {
                Optional<Element> elementOptional = elementRepo.findByCode(elm.getCode());
                if (!elementOptional.isPresent()) {
                    Type type = typeRepo.findByCode(elm.getTypeCode()).orElseThrow(() -> {
                        throw new BusinessException(
                                ResponseConstant.INVALID_FOOT_WORK_LOG_ID,
                                ResponseConstantMessage.INVALID_FOOT_WORK_LOG_ID);
                    });
                    Element element = new Element();
                    element.setType(type);
                    element.setName(elm.getName());
                    element.setCode(elm.getCode());
                    elementRepo.save(element);
                    logger.info("ELEMENT PERSISTS: " + element.getCode());
                }
            } catch (Exception e) {
                logger.error("ELEMENT NOT PERSISTS: " + elm.getCode());
            }
        }
    }

}
