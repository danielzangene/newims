package ir.newims.ims.business.personnel.footwork.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DayFootWorksTotalLogResponse {

    @JsonProperty
    private String totalDay;
    @JsonProperty
    private Boolean isCounting;

    public DayFootWorksTotalLogResponse(String totalDay, Boolean isCounting) {
        this.totalDay = totalDay;
        this.isCounting = isCounting;
    }
}
