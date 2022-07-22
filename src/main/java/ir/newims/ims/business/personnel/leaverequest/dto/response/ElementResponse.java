package ir.newims.ims.business.personnel.leaverequest.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ElementResponse {
    @JsonProperty
    private String name;
    @JsonProperty
    private String code;

    public ElementResponse(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
