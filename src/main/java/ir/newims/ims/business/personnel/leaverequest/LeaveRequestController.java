package ir.newims.ims.business.personnel.leaverequest;

import ir.newims.ims.ResponseConstant;
import ir.newims.ims.ResponseConstantMessage;
import ir.newims.ims.business.personnel.leaverequest.dto.request.DeleteLeaveRequestLogRequest;
import ir.newims.ims.business.personnel.leaverequest.dto.request.LeaveRequestListRequest;
import ir.newims.ims.business.personnel.leaverequest.dto.request.LeaveRequestLogRequest;
import ir.newims.ims.business.personnel.leaverequest.dto.response.LeaveRequestListResponse;
import ir.newims.ims.exception.DataResponse;
import ir.newims.ims.exception.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/personnel/leave")
public class LeaveRequestController {

    @Autowired
    LeaveRequestService leaveRequestService;

    @PatchMapping("/all")
    public ResponseEntity<?> getWeekFootWork(@RequestBody() LeaveRequestListRequest request) {
        LeaveRequestListResponse response = leaveRequestService.getAllLogs(request);
        return ResponseEntity.ok(new DataResponse(ResponseConstant.SC_OK,
                ResponseConstantMessage.SC_OK,
                response));
    }

    @PostMapping("/log")
    public ResponseEntity<?> saveOrUpdateFootWorkLog(@Valid @RequestBody LeaveRequestLogRequest request) {
        Response response = leaveRequestService.saveOrUpdate(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/log")
    public ResponseEntity<?> deleteFootWorkLog(@RequestBody DeleteLeaveRequestLogRequest request) {
        Response response = leaveRequestService.deleteRequest(request);
        return ResponseEntity.ok(response);
    }

}
