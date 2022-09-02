package ir.newims.ims.models.personnel.dining;

import ir.newims.ims.models.SimpleEntity;
import ir.newims.ims.models.personnel.personnel.User;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Setter
@Accessors(chain = true)
@Table(name = "t_ReserveItem")
public class ReserveItem extends SimpleEntity {

    private User user;
    private DiningItem diningItem;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "c_user")
    public User getUser() {
        return user;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "c_diningItem")
    public DiningItem getDiningItem() {
        return diningItem;
    }
}
