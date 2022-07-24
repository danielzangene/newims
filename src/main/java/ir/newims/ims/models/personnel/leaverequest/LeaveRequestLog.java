package ir.newims.ims.models.personnel.leaverequest;

import ir.newims.ims.models.management.Element;
import ir.newims.ims.models.personnel.RequestLog;

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
        return "از" + from + " تا " + to + "--" + reason;
    }

}
