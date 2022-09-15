package ir.newims.ims.business.personnel.fiscal;

import ir.newims.ims.ResponseConstant;
import ir.newims.ims.ResponseConstantMessage;
import ir.newims.ims.business.personnel.fiscal.dto.request.MonthDaysRequest;
import ir.newims.ims.business.personnel.fiscal.dto.response.MonthDaysResponse;
import ir.newims.ims.exception.DataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/personnel/fiscal")
public class FiscalController {

    @Autowired
    private FiscalService fiscalService;

    @PatchMapping("/month")
    public ResponseEntity<?> getMonth(@RequestBody() MonthDaysRequest request) {
        MonthDaysResponse month = fiscalService.getMonth(request);
        return ResponseEntity.ok(new DataResponse(ResponseConstant.SC_OK,
                ResponseConstantMessage.SC_OK,
                month));
    }

}
