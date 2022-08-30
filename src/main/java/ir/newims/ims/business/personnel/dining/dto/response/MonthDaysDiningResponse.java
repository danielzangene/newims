package ir.newims.ims.business.personnel.dining.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import ir.newims.ims.application.utils.DateUtil;
import ir.newims.ims.business.personnel.leaverequest.dto.response.ElementResponse;
import ir.newims.ims.models.personnel.footwork.FootWorkLog;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@AllArgsConstructor
@NoArgsConstructor
public class MonthDaysDiningResponse {

    @JsonProperty
    private String month;
    @JsonProperty
    private List<MonthDayDiningResponse> days;

}
