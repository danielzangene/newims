package ir.newims.ims.business.personnel.footwork.dto.request;

import javax.validation.constraints.NotBlank;

public class FootWorkLogRequest {
    private Long id;
    @NotBlank(message = "تاریخ روز مورد نظر را وارد کنید.")
    private String date;
    private Integer hour;
    private Integer minute;

    private String desc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
