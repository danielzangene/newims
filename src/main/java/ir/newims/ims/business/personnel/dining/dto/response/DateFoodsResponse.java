package ir.newims.ims.business.personnel.dining.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class DateFoodsResponse {

    @JsonProperty
    List<DateFoodResponse> foods;
    @JsonProperty
    DateFoodResponse reservedItem;

}
