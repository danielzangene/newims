package ir.newims.ims.business.personnel.dining;

import ir.newims.ims.models.management.Element;
import ir.newims.ims.models.personnel.dining.DiningItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiningItemRepo extends JpaRepository<DiningItem, Long> {

    List<DiningItem> findAllByDateAndType(String date, Element type);

}
