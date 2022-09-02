package ir.newims.ims.business.personnel.dining;

import ir.newims.ims.models.management.Element;
import ir.newims.ims.models.personnel.dining.DiningItem;
import ir.newims.ims.models.personnel.dining.ReserveItem;
import ir.newims.ims.models.personnel.personnel.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReserveItemRepo extends JpaRepository<ReserveItem, Long> {

    Optional<ReserveItem> findReserveItemByUserAndDiningItemIn(User user, List<DiningItem> DiningItems);
    Optional<ReserveItem> findReserveItemByDiningItem_Id(Long diningItemId);

    @Query("SELECT reserverd.diningItem.date FROM ReserveItem as reserverd " +
            "INNER JOIN Calendar cal on cal.date = reserverd.diningItem.date " +
            "WHERE cal.year = ?1 " +
            "AND  cal.month = ?2 "+
            "AND  reserverd.user = ?3 ")
    List<String> findReservedDates(@Param("year") Integer year,
                                   @Param("month") Integer month,
                                   @Param("user") User user);

    @Query("SELECT reserverd FROM ReserveItem as reserverd " +
            "WHERE reserverd.diningItem.date = ?1 " +
            "AND reserverd.diningItem.type = ?2")
    List<ReserveItem> reserveItemByDiningItemDate(@Param("date") String date, @Param("type")Element type);
}
