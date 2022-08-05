package ir.newims.ims.models.personnel.leaverequest;

import ir.newims.ims.models.management.Element;
import ir.newims.ims.models.personnel.RequestLog;
import org.springframework.util.StringUtils;

import javax.persistence.*;

@Entity
@Table(name = "t_LeaveRequestLog")
public class LeaveRequestLog extends RequestLog {

    private String to;
    private String from;
    private String fromTime;
    private String toTime;
    private String reason;
    private Element leaveType;

    @Column(name = "c_to")
    public String getTo() {
        return to;
    }

    @Column(name = "c_from")
    public String getFrom() {
        return from;
    }

    @Column(name = "c_fromTime")
    public String getFromTime() {
        return fromTime;
    }

    @Column(name = "c_toTime")
    public String getToTime() {
        return toTime;
    }

    @Column(name = "c_reason")
    public String getReason() {
        return reason;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "c_leaveType")
    public Element getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(Element leaveType) {
        this.leaveType = leaveType;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String description() {
        StringBuilder builder = new StringBuilder();
        builder.append(" از ");
        if (StringUtils.hasText(fromTime))
            builder.append(fromTime.substring(0, 2) + ":" + fromTime.substring(2, 4) + " ");
        builder.append(from + " تا ");
        if (StringUtils.hasText(toTime)) builder.append(toTime.substring(0, 2) + ":" + toTime.substring(2, 4) + " ");
        builder.append(to);
        if (StringUtils.hasText(reason)) {
            builder.append(" به علت: ");
            builder.append(reason);
        }
        return builder.toString();
    }

}
