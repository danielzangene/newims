package ir.newims.ims.business.personnel.footwork.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DayFootWorkSummaryResponse {

    @JsonProperty
    private String date;
    @JsonProperty
    private String formattedDate;
    @JsonProperty
    private String name;
    @JsonProperty
    private Boolean off;
    @JsonProperty
    private String totalDay;
    @JsonProperty
    private String totalLessDay;

    @JsonProperty
    private Boolean isCounting;

    @JsonProperty
    private Boolean withoutLog;

    public DayFootWorkSummaryResponse(String date,String formattedDate, String name,
                                      Boolean off, String totalDay, String totalLessDay,
                                      Boolean isCounting, Boolean withoutLog) {
        this.date = date;
        this.formattedDate = formattedDate;
        this.name = name;
        this.totalDay = totalDay;
        this.totalLessDay = totalLessDay;
        this.isCounting = isCounting;
        this.off = off;
        this.withoutLog = withoutLog;
    }
}
