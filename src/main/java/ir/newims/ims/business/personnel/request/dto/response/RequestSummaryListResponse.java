package ir.newims.ims.business.personnel.request.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RequestSummaryListResponse {

    @JsonProperty
    private Long count;
    @JsonProperty
    private List<RequestSummaryResponse> requests;

    public RequestSummaryListResponse(Long count, List<RequestSummaryResponse> requests) {
        this.count = count;
        this.requests = requests;
    }
}