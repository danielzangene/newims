package ir.newims.ims.business.personnel.leaverequest;

import ir.newims.ims.ResponseConstant;
import ir.newims.ims.ResponseConstantMessage;
import ir.newims.ims.business.management.element.ElementRepo;
import ir.newims.ims.business.personnel.leaverequest.dto.request.DeleteLeaveRequestLogRequest;
import ir.newims.ims.business.personnel.leaverequest.dto.request.LeaveRequestListRequest;
import ir.newims.ims.business.personnel.leaverequest.dto.request.LeaveRequestLogRequest;
import ir.newims.ims.business.personnel.leaverequest.dto.response.ElementResponse;
import ir.newims.ims.business.personnel.leaverequest.dto.response.LeaveRequestListResponse;
import ir.newims.ims.business.personnel.leaverequest.dto.response.LeaveRequestResponse;
import ir.newims.ims.business.personnel.personnel.UserService;
import ir.newims.ims.exception.BusinessException;
import ir.newims.ims.exception.Response;
import ir.newims.ims.models.management.Element;
import ir.newims.ims.models.personnel.footwork.LeaveRequestLog;
import ir.newims.ims.models.personnel.personnel.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class LeaveRequestOperational implements LeaveRequestService {

    @Autowired
    ElementRepo elementRepo;
    @Autowired
    UserService userService;
    @Autowired
    LeaveRequestRepo leaveRequestRepo;
    @Autowired
    LeaveRequestCheck check;

    @Override
    public Response saveOrUpdate(LeaveRequestLogRequest request) {

        LeaveRequestLog leaveRequestLog = new LeaveRequestLog();

        BeanUtils.copyProperties(request, leaveRequestLog);
        User currentUser = userService.getCurrentUser();
        leaveRequestLog.setUser(currentUser);
        leaveRequestLog.setType(elementRepo.findAll().get(request.getType()));
        leaveRequestLog.setStatus(elementRepo.findByCode(LeaveRequestCode.REGISTERED_REQUEST_STATUS).get());
        leaveRequestRepo.save(leaveRequestLog);
        return Response.SUCCESS_RESPONSE;

    }

    public Response deleteRequest(DeleteLeaveRequestLogRequest request) {
        LeaveRequestLog leaveRequestLog = leaveRequestRepo.findById(request.getId()).orElseThrow(() -> {
            throw new BusinessException(ResponseConstant.INVALID_LEAVE_REQUEST_LOG_ID, ResponseConstantMessage.INVALID_LEAVE_REQUEST_LOG_ID);
        });
        leaveRequestRepo.delete(leaveRequestLog);
        return Response.SUCCESS_RESPONSE;
    }

    @Override
    public LeaveRequestListResponse getAllLogs(LeaveRequestListRequest request) {
        User currentUser = userService.getCurrentUser();
        Page<LeaveRequestLog> logPage = leaveRequestRepo.findAllByUserOrderByCreationDateTime(
                currentUser,
                PageRequest.of(
                        request.getPageNum() - 1,
                        request.getPerPage()
                )
        );
        return new LeaveRequestListResponse(
                logPage.getTotalElements(),
                request.getPageNum(),
                request.getPerPage(),
                getLeaveRequestResponse(logPage.getContent())
        );
    }

    List<LeaveRequestResponse> getLeaveRequestResponse(List<LeaveRequestLog> logs) {
        List<LeaveRequestResponse> responseLogs = new LinkedList<>();
        for (LeaveRequestLog log : logs) {
            responseLogs.add(
                    new LeaveRequestResponse(
                            log.getId(),
                            log.getTo(),
                            log.getFrom(),
                            log.getFromTime(),
                            log.getToTime(),
                            log.getReason(),
                            getLeaveRequestResponse(log.getStatus()),
                            getLeaveRequestResponse(log.getType()),
                            check.canDelete(log)
                    )
            );
        }
        return responseLogs;
    }

    ElementResponse getLeaveRequestResponse(Element element) {
        return new ElementResponse(
                element.getName(),
                element.getCode()
        );
    }
}
