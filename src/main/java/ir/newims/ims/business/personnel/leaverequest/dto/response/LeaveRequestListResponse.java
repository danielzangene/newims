package ir.newims.ims.business.personnel.leaverequest.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class LeaveRequestListResponse {

    @JsonProperty
    private Long count;
    @JsonProperty
    private Integer current;
    @JsonProperty
    private Integer perPage;
    @JsonProperty
    private List<LeaveRequestResponse> requests;

    public LeaveRequestListResponse(Long count, Integer current, Integer perPage, List<LeaveRequestResponse> requests) {
        this.count = count;
        this.current = current;
        this.perPage = perPage;
        this.requests = requests;
    }
}