package ir.newims.ims.business.personnel.footwork.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class WeekFootWorksResponse {

    @JsonProperty
    private String fromTo;
    @JsonProperty
    private String toDay;
    @JsonProperty
    private String totalWeek;
    @JsonProperty
    private List<DayFootWorksResponse> timeSheet;

    public WeekFootWorksResponse(String fromTo, String toDay, String totalWeek, List<DayFootWorksResponse> timeSheet) {
        this.fromTo = fromTo;
        this.toDay = toDay;
        this.totalWeek = totalWeek;
        this.timeSheet = timeSheet;
    }
}
