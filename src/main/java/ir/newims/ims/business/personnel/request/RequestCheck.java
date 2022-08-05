package ir.newims.ims.business.personnel.request;

import ir.newims.ims.models.personnel.RequestLog;
import org.springframework.stereotype.Component;

@Component
public class RequestCheck {

    Boolean canDoOperation(RequestLog requestLog){
        return requestLog.getStatus().getCode().equals(RequestCode.REGISTERED_REQUEST_STATUS);
    }

}
