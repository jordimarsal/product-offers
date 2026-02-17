package net.jordimp.productoffers.price.infrastructure.persistence;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.jordimp.productoffers.price.domain.entities.Prices;

@Repository
public interface SpringDataPricesRepository extends JpaRepository<Prices, Long> {
    List<Prices> findByBrandIdAndProductId(Long brandId, Long productId);

    Optional<Prices> findTopByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
        Long brandId, Long productId, LocalDateTime applicationDate);

}