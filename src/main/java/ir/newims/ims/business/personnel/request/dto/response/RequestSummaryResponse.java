package ir.newims.ims.business.personnel.request.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestSummaryResponse {

    @JsonProperty
    private Long id;
    @JsonProperty
    private String userName;
    @JsonProperty
    private ElementResponse type;
    @JsonProperty
    private String description;

    public RequestSummaryResponse(Long id, String userName, ElementResponse type, String description) {
        this.id = id;
        this.userName = userName;
        this.type = type;
        this.description = description;
    }
}
