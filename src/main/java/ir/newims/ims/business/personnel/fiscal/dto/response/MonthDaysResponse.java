package ir.newims.ims.business.personnel.fiscal.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
public class MonthDaysResponse {

    @JsonProperty
    private String month;
    @JsonProperty
    private String standardWorkTime;
    @JsonProperty
    private String totalMonthHours;
    @JsonProperty
    private String totalLeaveRequests;
    @JsonProperty
    private Integer totalMonthDays;
    @JsonProperty
    private List<MonthDayResponse> days;

}
