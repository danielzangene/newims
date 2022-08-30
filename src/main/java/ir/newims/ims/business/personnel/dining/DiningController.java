package ir.newims.ims.business.personnel.dining;

import ir.newims.ims.ResponseConstant;
import ir.newims.ims.ResponseConstantMessage;
import ir.newims.ims.business.personnel.dining.dto.request.DateMealDiningRequest;
import ir.newims.ims.business.personnel.dining.dto.request.MonthDaysDiningRequest;
import ir.newims.ims.business.personnel.dining.dto.response.DateFoodsResponse;
import ir.newims.ims.business.personnel.dining.dto.response.MonthDaysDiningResponse;
import ir.newims.ims.exception.DataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/personnel/dining")
public class DiningController {

    @Autowired
    private DiningService diningService;

    @PatchMapping("/month")
    public ResponseEntity<?> getWeekFootWork(@RequestBody() MonthDaysDiningRequest request) {
        MonthDaysDiningResponse month = diningService.getMonth(request);
        return ResponseEntity.ok(new DataResponse(ResponseConstant.SC_OK,
                ResponseConstantMessage.SC_OK,
                month));
    }

    @PatchMapping("/food")
    public ResponseEntity<?> getWeekFootWork(@RequestBody() DateMealDiningRequest request) {
        List<DateFoodsResponse> foods = diningService.getFoods(request);
        return ResponseEntity.ok(new DataResponse(ResponseConstant.SC_OK,
                ResponseConstantMessage.SC_OK,
                foods));
    }




}
