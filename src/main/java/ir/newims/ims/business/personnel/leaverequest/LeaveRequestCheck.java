package ir.newims.ims.business.personnel.leaverequest;

import ir.newims.ims.models.personnel.footwork.LeaveRequestLog;
import org.springframework.stereotype.Component;

@Component
public class LeaveRequestCheck {

    Boolean canDelete(LeaveRequestLog leaveRequestLog){
        return leaveRequestLog.getStatus().getCode().equals(LeaveRequestCode.REGISTERED_REQUEST_STATUS);
    }

}
