package ir.newims.ims.business.personnel.request.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestResponse {

    @JsonProperty
    private Long id;
    @JsonProperty
    private String userName;
    @JsonProperty
    private ElementResponse type;
    @JsonProperty
    private ElementResponse status;
    @JsonProperty
    private String description;
    @JsonProperty
    private String requestDate;
    @JsonProperty
    private Boolean canDoOperation;

    public RequestResponse(Long id,
                           String userName,
                           ElementResponse type,
                           ElementResponse status,
                           String description,
                           String requestDate,
                           Boolean canDoOperation) {
        this.id = id;
        this.userName = userName;
        this.type = type;
        this.status = status;
        this.description = description;
        this.requestDate = requestDate;
        this.canDoOperation = canDoOperation;
    }
}
