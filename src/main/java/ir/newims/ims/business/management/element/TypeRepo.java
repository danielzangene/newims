package ir.newims.ims.business.management.element;

import ir.newims.ims.models.management.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypeRepo extends JpaRepository<Type, Long> {
    Optional<Type> findByCode(String code);
}
