package ir.newims.ims.business.management.element;

import ir.newims.ims.models.management.Element;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ElementRepo extends JpaRepository<Element, Long> {

    Optional<Element> findByCode(String code);
}
