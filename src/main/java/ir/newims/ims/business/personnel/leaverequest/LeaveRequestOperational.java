package ir.newims.ims.business.personnel.leaverequest;

import ir.newims.ims.ResponseConstant;
import ir.newims.ims.ResponseConstantMessage;
import ir.newims.ims.business.management.element.ElementRepo;
import ir.newims.ims.business.personnel.PersonnelCode;
import ir.newims.ims.business.personnel.leaverequest.dto.request.DeleteLeaveRequestLogRequest;
import ir.newims.ims.business.personnel.leaverequest.dto.request.LeaveRequestListRequest;
import ir.newims.ims.business.personnel.leaverequest.dto.request.LeaveRequestLogRequest;
import ir.newims.ims.business.personnel.leaverequest.dto.response.ElementResponse;
import ir.newims.ims.business.personnel.leaverequest.dto.response.LeaveRequestListResponse;
import ir.newims.ims.business.personnel.leaverequest.dto.response.LeaveRequestResponse;
import ir.newims.ims.business.personnel.personnel.UserService;
import ir.newims.ims.business.personnel.request.RequestCode;
import ir.newims.ims.business.personnel.request.RequestService;
import ir.newims.ims.exception.BusinessException;
import ir.newims.ims.exception.Response;
import ir.newims.ims.models.management.Element;
import ir.newims.ims.models.personnel.leaverequest.LeaveRequestLog;
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
    RequestService requestService;
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
        leaveRequestLog.setLeaveType(elementRepo.findAll().get(request.getType()));
        leaveRequestLog.setType(elementRepo.findByCode(RequestCode.LEAVE_REQUEST_LOG_TYPE).get());
        leaveRequestLog.setStatus(elementRepo.findByCode(RequestCode.REGISTERED_REQUEST_STATUS).get());
        leaveRequestRepo.save(leaveRequestLog);
        requestService.pushNotifReq(leaveRequestLog);
        return Response.SUCCESS_RESPONSE;

    }

    public Response deleteRequest(DeleteLeaveRequestLogRequest request) {
        LeaveRequestLog leaveRequestLog = leaveRequestRepo.findByIdAndUser(
                request.getId(),
                userService.getCurrentUser()
        ).orElseThrow(() -> {
                    throw new BusinessException(ResponseConstant.INVALID_LEAVE_REQUEST_LOG_ID,
                            ResponseConstantMessage.INVALID_LEAVE_REQUEST_LOG_ID);
                }
        );
        leaveRequestRepo.delete(leaveRequestLog);
        return Response.SUCCESS_RESPONSE;
    }

    @Override
    public LeaveRequestListResponse getAllLogs(LeaveRequestListRequest request) {
        User currentUser = userService.getCurrentUser();
        Long count = leaveRequestRepo.countAllByUser(currentUser);
        if (request.getPageNum() * request.getPerPage() > count)
            request.setPageNum((int) Math.ceil(count / request.getPerPage()) + 1);
        Page<LeaveRequestLog> logPage = leaveRequestRepo.findAllByUserOrderByCreationDateTimeDesc(
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
                            getLeaveRequestResponse(log.getLeaveType()),
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
