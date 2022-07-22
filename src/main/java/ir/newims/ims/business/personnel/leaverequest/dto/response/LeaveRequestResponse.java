package ir.newims.ims.business.personnel.leaverequest.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import ir.newims.ims.business.personnel.leaverequest.dto.response.ElementResponse;

import javax.validation.constraints.NotBlank;

public class LeaveRequestResponse {

    @JsonProperty
    private Long id;
    @JsonProperty
    private String to;
    @JsonProperty
    private String from;
    @JsonProperty
    private String fromTime;
    @JsonProperty
    private String toTime;

    @JsonProperty
    private String reason;

    @JsonProperty
    private ElementResponse status;

    @JsonProperty
    private ElementResponse type;

    @JsonProperty
    private Boolean canDelete;

    public LeaveRequestResponse(Long id,
                                String to,
                                String from,
                                String fromTime,
                                String toTime,
                                String reason,
                                ElementResponse status,
                                ElementResponse type,
                                Boolean canDelete) {
        this.id = id;
        this.to = to;
        this.from = from;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.reason = reason;
        this.status = status;
        this.type = type;
        this.canDelete = canDelete;
    }
}
