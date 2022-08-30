package ir.newims.ims.business.personnel.footwork.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import ir.newims.ims.application.utils.DateUtil;
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
    private Boolean off;
    @JsonProperty
    private String formattedDate;
    @JsonProperty
    private String totalDay;
    @JsonProperty
    private List<FootWorksResponse> footWorks;

    public DayFootWorksResponse(String date,Boolean off, String totalDay, String formattedDate, List<FootWorkLog> footWorks) {
        this.date = date;
        this.off = off;
        this.formattedDate = formattedDate;
        this.totalDay = totalDay;
        this.footWorks = Objects.nonNull(footWorks) ? footWorks.stream().map(footWorkLog -> {
            return new FootWorksResponse(
                    footWorkLog.getId(),
                    DateUtil.getFormatedTime( footWorkLog.getHour(),footWorkLog.getMinute()),
                    footWorkLog.getDate(),
                    footWorkLog.getDesc(),
                    new ElementResponse(
                            footWorkLog.getStatus().getName(),
                            footWorkLog.getStatus().getCode()
                    )
            );
        }).collect(Collectors.toList()) : null;
    }

    public String getTotalDay() {
        return totalDay;
    }
}
