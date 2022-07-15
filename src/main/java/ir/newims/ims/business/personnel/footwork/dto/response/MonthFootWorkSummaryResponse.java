package ir.newims.ims.business.personnel.footwork.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MonthFootWorkSummaryResponse {

    @JsonProperty
    private String name;
    @JsonProperty
    private String totalMonth;
    @JsonProperty
    private String totalMonthExtera;
    @JsonProperty
    private String totalMonthLess;
    @JsonProperty
    private String totalMonthLeave;

    public MonthFootWorkSummaryResponse(String name, String totalMonth, String totalMonthExtera, String totalMonthLess, String totalMonthLeave) {
        this.name = name;
        this.totalMonth = totalMonth;
        this.totalMonthExtera = totalMonthExtera;
        this.totalMonthLess = totalMonthLess;
        this.totalMonthLeave = totalMonthLeave;
    }
}
