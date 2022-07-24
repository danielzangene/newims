package ir.newims.ims.models.personnel.footwork;

import ir.newims.ims.models.personnel.RequestLog;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_FootWorkLog")
public class FootWorkLog extends RequestLog {

    private String time;
    private String date;
    private String desc;


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

    @Override
    public String description() {
        return time + " در " + date + "--" + desc;
    }
}
