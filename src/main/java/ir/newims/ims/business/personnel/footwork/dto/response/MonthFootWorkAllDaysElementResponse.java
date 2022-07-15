package ir.newims.ims.business.personnel.footwork.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MonthFootWorkAllDaysElementResponse {

    @JsonProperty
    private String name;
    @JsonProperty
    private List<String> data;

    public MonthFootWorkAllDaysElementResponse(String name, List<String> data) {
        this.name = name;
        this.data = data;
    }
}
