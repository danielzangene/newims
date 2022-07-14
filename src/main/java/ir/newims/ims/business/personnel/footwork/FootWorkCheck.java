package ir.newims.ims.business.personnel.footwork;

import ir.newims.ims.ResponseConstant;
import ir.newims.ims.ResponseConstantMessage;
import ir.newims.ims.business.personnel.footwork.dto.request.FootWorkLogRequest;
import ir.newims.ims.exception.BusinessException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class FootWorkCheck {

    public void validateFootWorkLogRequest(FootWorkLogRequest request) {
        String logTime = request.getTime();
        if (!StringUtils.hasText(logTime) || !logTime.trim().matches("[0-9]+") || logTime.trim().length() != 4) {
            throw new BusinessException(ResponseConstant.INVALID_FOOT_WORK_LOG_DATA,ResponseConstantMessage.INVALID_FOOT_WORK_LOG_DATA);
        }
    }
}
