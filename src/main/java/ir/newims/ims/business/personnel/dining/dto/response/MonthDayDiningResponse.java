package ir.newims.ims.business.personnel.dining.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
public class MonthDayDiningResponse {

    @JsonProperty
    private String date;
    @JsonProperty
    private String formattedDate;
    @JsonProperty
    private Boolean enable;

}
