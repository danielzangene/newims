package ir.newims.ims.business.personnel.leaverequest;

import ir.newims.ims.business.personnel.PersonnelCode;
import ir.newims.ims.business.personnel.request.RequestCode;
import ir.newims.ims.models.personnel.leaverequest.LeaveRequestLog;
import org.springframework.stereotype.Component;

@Component
public class LeaveRequestCheck {

    Boolean canDelete(LeaveRequestLog leaveRequestLog){
        return leaveRequestLog.getStatus().getCode().equals(RequestCode.REGISTERED_REQUEST_STATUS);
    }

}
