package ir.newims.ims.business.personnel.dining.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DateMealDiningRequest {

    private Long id;
    private String date;
    private int foodType;

}
