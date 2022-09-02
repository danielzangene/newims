package ir.newims.ims.business.personnel.dining.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import ir.newims.ims.business.management.element.dto.ElementDto;
import ir.newims.ims.models.personnel.dining.DiningItem;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class DateFoodResponse {

    @JsonProperty
    private Long id;
    @JsonProperty
    private String name;
    @JsonProperty
    private String date;
    @JsonProperty
    private ElementDto type;

    public DateFoodResponse(DiningItem diningItem) {
        this.id = diningItem.getId();
        this.name = diningItem.getName();
        this.date = diningItem.getDate();
        this.type = new ElementDto(diningItem.getType());
    }
}
