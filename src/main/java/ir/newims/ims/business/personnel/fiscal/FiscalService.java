package ir.newims.ims.business.personnel.fiscal;

import ir.newims.ims.business.personnel.fiscal.dto.request.MonthDaysRequest;
import ir.newims.ims.business.personnel.fiscal.dto.response.MonthDaysResponse;
import org.springframework.stereotype.Service;

@Service
public interface FiscalService {

    MonthDaysResponse getMonth(MonthDaysRequest requestDto);
}
