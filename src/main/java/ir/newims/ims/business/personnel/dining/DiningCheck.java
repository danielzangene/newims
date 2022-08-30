package ir.newims.ims.business.personnel.dining;

import ir.newims.ims.ResponseConstant;
import ir.newims.ims.ResponseConstantMessage;
import ir.newims.ims.business.personnel.dining.dto.request.MonthDaysDiningRequest;
import ir.newims.ims.exception.BusinessException;
import org.springframework.stereotype.Component;

@Component
public class DiningCheck {

    public void validateMonthThreshold(MonthDaysDiningRequest requestDto) {
        if (requestDto.getMonthFromNow() < 0) {
            throw new BusinessException(
                    ResponseConstant.INVALID_DINING_MONTH_FROM_NOW,
                    ResponseConstantMessage.INVALID_DINING_MONTH_FROM_NOW
            );
        }
    }

}
