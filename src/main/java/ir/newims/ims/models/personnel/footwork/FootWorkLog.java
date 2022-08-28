package ir.newims.ims.models.personnel.footwork;

import ir.newims.ims.models.personnel.RequestLog;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_FootWorkLog")
public class FootWorkLog extends RequestLog {

    private Integer hour;
    private Integer minute;
    private String date;
    private String desc;

    @Column(name = "c_hour")
    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }
    @Column(name = "c_minute")
    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
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
        StringBuilder builder = new StringBuilder();
        builder.append(hour);
        builder.append(":");
        builder.append(minute);
        builder.append(" ");
        builder.append(date);
        if (StringUtils.hasText(desc)) {
            builder.append(" ");
            builder.append(desc);
        }
        return builder.toString();
    }
}
