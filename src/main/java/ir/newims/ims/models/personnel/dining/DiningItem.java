package ir.newims.ims.models.personnel.dining;

import ir.newims.ims.models.SimpleEntity;
import ir.newims.ims.models.management.Element;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Setter
@Accessors(chain = true)
@Table(name = "t_DiningItem")
public class DiningItem extends SimpleEntity {

    private String date;
    private String name;
    private Element type;

    @Column(name = "c_date")
    public String getDate() {
        return date;
    }

    @Column(name = "c_name")
    public String getName() {
        return name;
    }
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "c_type")
    public Element getType() {
        return type;
    }
}
