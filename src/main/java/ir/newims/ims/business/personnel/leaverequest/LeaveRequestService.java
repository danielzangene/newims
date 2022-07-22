package ir.newims.ims.business.personnel.leaverequest;

import ir.newims.ims.business.personnel.leaverequest.dto.request.DeleteLeaveRequestLogRequest;
import ir.newims.ims.business.personnel.leaverequest.dto.request.LeaveRequestListRequest;
import ir.newims.ims.business.personnel.leaverequest.dto.request.LeaveRequestLogRequest;
import ir.newims.ims.business.personnel.leaverequest.dto.response.LeaveRequestListResponse;
import ir.newims.ims.exception.Response;
import org.springframework.stereotype.Service;

@Service
public interface LeaveRequestService {
    Response saveOrUpdate(LeaveRequestLogRequest request);

    LeaveRequestListResponse getAllLogs(LeaveRequestListRequest request);

    Response deleteRequest(DeleteLeaveRequestLogRequest request);
}
