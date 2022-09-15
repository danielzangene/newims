package ir.newims.ims.business.personnel.fiscal.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
public class MonthDayResponse {

    @JsonProperty
    private String date;
    @JsonProperty
    private Boolean off;
    @JsonProperty
    private String formattedDate;
    @JsonProperty
    private String totalDayLog;
    @JsonProperty
    private String totalDayLeave;

}
