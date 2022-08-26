package ir.newims.ims.business.personnel.request;

import ir.newims.ims.business.personnel.request.dto.request.RequestActionRequest;
import ir.newims.ims.business.personnel.request.dto.request.RequestListRequest;
import ir.newims.ims.business.personnel.request.dto.response.RequestListResponse;
import ir.newims.ims.business.personnel.request.dto.response.RequestSummaryListResponse;
import ir.newims.ims.exception.Response;
import ir.newims.ims.models.personnel.RequestLog;
import org.springframework.stereotype.Service;

@Service
public interface RequestService {
    RequestSummaryListResponse getAllLogsSummary();

    RequestListResponse getAllLogs(RequestListRequest request);

    Response acceptLog(RequestActionRequest request);

    Response rejectLog(RequestActionRequest request);

    void pushNotifReq(RequestLog requestLog);
}
