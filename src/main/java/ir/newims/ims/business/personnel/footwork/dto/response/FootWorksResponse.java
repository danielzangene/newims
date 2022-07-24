package ir.newims.ims.business.personnel.footwork.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import ir.newims.ims.business.personnel.leaverequest.dto.response.ElementResponse;

public class FootWorksResponse {

    @JsonProperty
    private Long id;
    @JsonProperty
    private String time;
    @JsonProperty
    private String date;
    @JsonProperty
    private String desc;
    @JsonProperty
    private ElementResponse status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public ElementResponse getStatus() {
        return status;
    }

    public void setStatus(ElementResponse status) {
        this.status = status;
    }
}
