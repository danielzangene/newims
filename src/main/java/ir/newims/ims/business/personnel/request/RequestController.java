package ir.newims.ims.business.personnel.request;

import ir.newims.ims.ResponseConstant;
import ir.newims.ims.ResponseConstantMessage;
import ir.newims.ims.business.personnel.request.dto.request.RequestActionRequest;
import ir.newims.ims.business.personnel.request.dto.request.RequestListRequest;
import ir.newims.ims.business.personnel.request.dto.response.RequestListResponse;
import ir.newims.ims.business.personnel.request.dto.response.RequestSummaryListResponse;
import ir.newims.ims.exception.DataResponse;
import ir.newims.ims.exception.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/personnel/request")
public class RequestController {

    @Autowired
    RequestService requestService;

    @PatchMapping("/all")
    public ResponseEntity<?> getAll(@RequestBody() RequestListRequest request) {
        RequestListResponse response = requestService.getAllLogs(request);
        return ResponseEntity.ok(new DataResponse(ResponseConstant.SC_OK,
                ResponseConstantMessage.SC_OK,
                response));
    }

    @PatchMapping("/all/summary")
    public ResponseEntity<?> getAllSummery() {
        RequestSummaryListResponse allLogsSummary = requestService.getAllLogsSummary();
        return ResponseEntity.ok(new DataResponse(ResponseConstant.SC_OK,
                ResponseConstantMessage.SC_OK,
                allLogsSummary));
    }

    @PostMapping("/accept")
    public ResponseEntity<?> accept(@RequestBody() RequestActionRequest request) {
        Response response = requestService.acceptLog(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reject")
    public ResponseEntity<?> reject(@RequestBody() RequestActionRequest request) {
        Response response = requestService.rejectLog(request);
        return ResponseEntity.ok(response);
    }

}
