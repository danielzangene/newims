package ir.newims.ims.business.personnel.dining;

import ir.newims.ims.business.personnel.dining.dto.request.DateMealDiningRequest;
import ir.newims.ims.business.personnel.dining.dto.request.MonthDaysDiningRequest;
import ir.newims.ims.business.personnel.dining.dto.response.DateFoodsResponse;
import ir.newims.ims.business.personnel.dining.dto.response.MonthDaysDiningResponse;
import org.springframework.stereotype.Service;

@Service
public interface DiningService {

    MonthDaysDiningResponse getMonth(MonthDaysDiningRequest requestDto);

    DateFoodsResponse getFoods(DateMealDiningRequest request);

    DateFoodsResponse reserveFood(DateMealDiningRequest request);
}
