package ir.newims.ims.business.personnel.request.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class RequestListResponse {

    @JsonProperty
    private Long count;
    @JsonProperty
    private Integer current;
    @JsonProperty
    private Integer perPage;
    @JsonProperty
    private List<RequestResponse> requests;

    public RequestListResponse(Long count, Integer current, Integer perPage, List<RequestResponse> requests) {
        this.count = count;
        this.current = current;
        this.perPage = perPage;
        this.requests = requests;
    }
}