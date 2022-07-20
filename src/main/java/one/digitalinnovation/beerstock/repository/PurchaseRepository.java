package one.digitalinnovation.beerstock.repository;

import one.digitalinnovation.beerstock.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    List<Purchase> findByBeerId(Long beerId);
}
