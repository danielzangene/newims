package ir.newims.ims.business.personnel.footwork.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class WeekFootWorksSummaryResponse {

    @JsonProperty
    private String fromTo;
    @JsonProperty
    private String toDay;
    @JsonProperty
    private List<DayFootWorkSummaryResponse> daySummary;

    public WeekFootWorksSummaryResponse(String fromTo, String toDay, List<DayFootWorkSummaryResponse> daySummary) {
        this.fromTo = fromTo;
        this.toDay = toDay;
        this.daySummary = daySummary;
    }
}
