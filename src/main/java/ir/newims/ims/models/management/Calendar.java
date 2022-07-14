package ir.newims.ims.models.management;

import ir.newims.ims.models.SimpleEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_Calendar")
public class Calendar extends SimpleEntity {
    private String date;
    private Boolean off;
    private String persianName;

    @Column(name = "c_date")
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Column(name = "c_off")
    public Boolean getOff() {
        return off;
    }

    public void setOff(Boolean off) {
        this.off = off;
    }

    @Column(name = "c_persianName")
    public String getPersianName() {
        return persianName;
    }

    public void setPersianName(String persianName) {
        this.persianName = persianName;
    }
}
