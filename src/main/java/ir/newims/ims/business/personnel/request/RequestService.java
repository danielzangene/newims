package ir.newims.ims.business.personnel.request;

import ir.newims.ims.business.personnel.request.dto.request.RequestActionRequest;
import ir.newims.ims.business.personnel.request.dto.request.RequestListRequest;
import ir.newims.ims.business.personnel.request.dto.response.RequestListResponse;
import ir.newims.ims.exception.Response;
import org.springframework.stereotype.Service;

@Service
public interface RequestService {
    RequestListResponse getAllLogs(RequestListRequest request);

    Response acceptLog(RequestActionRequest request);

    Response rejectLog(RequestActionRequest request);
}
