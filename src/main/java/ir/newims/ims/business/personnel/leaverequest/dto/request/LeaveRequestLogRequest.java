package ir.newims.ims.business.personnel.leaverequest.dto.request;

import javax.validation.constraints.NotBlank;

public class LeaveRequestLogRequest {
    private Long id;
    @NotBlank(message = "تاریخ انتها را وارد کنید.")
    private String to;
    @NotBlank(message = "تاریخ ابتدا را وارد کنید.")
    private String from;
    private String fromTime;
    private String toTime;
    private String reason;
    private Integer type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
