package ir.newims.ims.business.personnel.footwork.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MonthFootWorkAllDaysResponse {

    @JsonProperty
    private List<MonthFootWorkAllDaysElementResponse> series;

    public MonthFootWorkAllDaysResponse(List<MonthFootWorkAllDaysElementResponse> series) {
        this.series = series;
    }
}
