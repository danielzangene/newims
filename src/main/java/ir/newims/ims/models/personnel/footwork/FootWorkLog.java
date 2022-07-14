package ir.newims.ims.models.personnel.footwork;

import ir.newims.ims.models.BaseEntity;
import ir.newims.ims.models.personnel.personnel.User;

import javax.persistence.*;

@Entity
@Table(name = "t_FootWorkLog")
public class FootWorkLog extends BaseEntity {

    private User user;
    private String time;
    private String date;
    private String desc;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "c_user")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "c_time")
    public String getTime() {
        return time;
    }

    public void setTime(String log) {
        this.time = log;
    }

    @Column(name = "c_date")
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Column(name = "c_desc")
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
