package ir.newims.ims.business.personnel.request;

import ir.newims.ims.ResponseConstant;
import ir.newims.ims.ResponseConstantMessage;
import ir.newims.ims.business.management.element.ElementRepo;
import ir.newims.ims.business.personnel.personnel.UserService;
import ir.newims.ims.business.personnel.request.dto.request.RequestActionRequest;
import ir.newims.ims.business.personnel.request.dto.request.RequestListRequest;
import ir.newims.ims.business.personnel.request.dto.response.ElementResponse;
import ir.newims.ims.business.personnel.request.dto.response.RequestListResponse;
import ir.newims.ims.business.personnel.request.dto.response.RequestResponse;
import ir.newims.ims.exception.BusinessException;
import ir.newims.ims.exception.Response;
import ir.newims.ims.models.management.Element;
import ir.newims.ims.models.personnel.RequestLog;
import ir.newims.ims.models.personnel.personnel.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class RequestOperational implements RequestService {

    @Autowired
    UserService userService;
    @Autowired
    RequestRepo requestRepo;
    @Autowired
    ElementRepo elementRepo;
    @Autowired
    RequestCheck check;


    @Override
    public RequestListResponse getAllLogs(RequestListRequest request) {
        User currentUser = userService.getCurrentUser();
        Long count = requestRepo.countAllByUser_Supervisor(currentUser);
        if (request.getPageNum() * request.getPerPage() > count)
            request.setPageNum((int) Math.ceil(count / request.getPerPage()) + 1);
        Page<RequestLog> logPage = requestRepo.findAllByUser_SupervisorOrderByCreationDateTimeDesc(
                currentUser,
                PageRequest.of(
                        request.getPageNum() - 1,
                        request.getPerPage()
                )
        );
        return new RequestListResponse(
                logPage.getTotalElements(),
                request.getPageNum(),
                request.getPerPage(),
                getLeaveRequestResponse(logPage.getContent())
        );
    }

    List<RequestResponse> getLeaveRequestResponse(List<RequestLog> logs) {
        List<RequestResponse> responseLogs = new LinkedList<>();
        for (RequestLog log : logs) {
            responseLogs.add(
                    new RequestResponse(
                            log.getId(),
                            log.getUser().getName(),
                            getElementResponse(log.getType()),
                            getElementResponse(log.getStatus()),
                            log.description(),
                            log.getCreationDateTime(),
                            check.canDoOperation(log)
                    )
            );
        }
        return responseLogs;
    }

    ElementResponse getElementResponse(Element element) {
        return new ElementResponse(
                element.getName(),
                element.getCode()
        );
    }

    @Override
    public Response acceptLog(RequestActionRequest request) {
        RequestLog requestLog = requestRepo.findByIdAndUser_Supervisor(
                request.getId(),
                userService.getCurrentUser()
        ).orElseThrow(() -> {
                    throw new BusinessException(ResponseConstant.INVALID_LEAVE_REQUEST_LOG_ID,
                            ResponseConstantMessage.INVALID_LEAVE_REQUEST_LOG_ID);
                }
        );
        requestLog.setStatus(elementRepo.findByCode(RequestCode.CONFIRMED_REQUEST_STATUS).get());
        requestRepo.save(requestLog);
        return Response.SUCCESS_RESPONSE;
    }

    @Override
    public Response rejectLog(RequestActionRequest request) {
        RequestLog requestLog = requestRepo.findByIdAndUser_Supervisor(
                request.getId(),
                userService.getCurrentUser()
        ).orElseThrow(() -> {
                    throw new BusinessException(ResponseConstant.INVALID_LEAVE_REQUEST_LOG_ID,
                            ResponseConstantMessage.INVALID_LEAVE_REQUEST_LOG_ID);
                }
        );
        requestLog.setStatus(elementRepo.findByCode(RequestCode.REJECTED_REQUEST_STATUS).get());
        requestRepo.save(requestLog);
        return Response.SUCCESS_RESPONSE;    }
}