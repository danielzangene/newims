package ir.newims.ims.business.personnel.footwork.dto.request;

import javax.validation.constraints.NotBlank;

public class FootWorkLogRequest {
    private Long id;
    @NotBlank(message = "تاریخ روز مورد نظر را وارد کنید.")
    private String date;
    @NotBlank(message = "ساعت ورود/خروج را وارد کنید.")
    private String time;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
