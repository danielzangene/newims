package ir.newims.ims.business.personnel.footwork.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import ir.newims.ims.business.personnel.leaverequest.dto.response.ElementResponse;
import ir.newims.ims.models.personnel.footwork.FootWorkLog;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DayFootWorksResponse {

    @JsonProperty
    private String date;
    @JsonProperty
    private String formattedDate;
    @JsonProperty
    private String totalDay;
    @JsonProperty
    private List<FootWorksResponse> footWorks;

    public DayFootWorksResponse(String date, String totalDay, String formattedDate, List<FootWorkLog> footWorks) {
        this.date = date;
        this.formattedDate = formattedDate;
        this.totalDay = totalDay;
        this.footWorks = Objects.nonNull(footWorks) ? footWorks.stream().map(footWorkLog -> {
            FootWorksResponse footWorksResponse = new FootWorksResponse();
            BeanUtils.copyProperties(footWorkLog, footWorksResponse);
            footWorksResponse.setStatus(
                    new ElementResponse(
                            footWorkLog.getStatus().getName(),
                            footWorkLog.getStatus().getCode()
                    )
            );
            return footWorksResponse;
        }).collect(Collectors.toList()) : null;
    }

    public String getTotalDay() {
        return totalDay;
    }
}
