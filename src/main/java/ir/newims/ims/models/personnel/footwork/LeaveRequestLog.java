package ir.newims.ims.models.personnel.footwork;

import ir.newims.ims.models.BaseEntity;
import ir.newims.ims.models.management.Element;
import ir.newims.ims.models.personnel.personnel.User;

import javax.persistence.*;

@Entity
@Table(name = "t_LeaveRequestLog")
public class LeaveRequestLog extends BaseEntity {

    private String to;
    private String from;
    private String fromTime;
    private String toTime;
    private String reason;
    private Element status;
    private Element type;

    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "c_user")
    public User getUser() {
        return user;
    }

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
    @JoinColumn(name = "c_status")
    public Element getStatus() {
        return status;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "c_type")
    public Element getType() {
        return type;
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

    public void setStatus(Element status) {
        this.status = status;
    }

    public void setType(Element type) {
        this.type = type;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
