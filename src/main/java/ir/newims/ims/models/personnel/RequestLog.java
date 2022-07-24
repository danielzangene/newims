package ir.newims.ims.models.personnel;

import ir.newims.ims.models.BaseEntity;
import ir.newims.ims.models.management.Element;
import ir.newims.ims.models.personnel.personnel.User;

import javax.persistence.*;

@Entity
@Table(name = "t_RequestLog")
@Inheritance(strategy = InheritanceType.JOINED)
public class RequestLog extends BaseEntity {

    private Element status;
    private Element type;

    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "c_user")
    public User getUser() {
        return user;
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

    public void setStatus(Element status) {
        this.status = status;
    }

    public void setType(Element type) {
        this.type = type;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String description() {
        return "default description";
    }
}
