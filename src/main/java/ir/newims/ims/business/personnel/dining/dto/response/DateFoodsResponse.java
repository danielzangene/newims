package ir.newims.ims.business.personnel.dining.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import ir.newims.ims.business.management.element.dto.ElementDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
public class DateFoodsResponse {

    @JsonProperty
    private Long id;
    @JsonProperty
    private String name;
    @JsonProperty
    private String date;
    @JsonProperty
    private ElementDto type;

}
