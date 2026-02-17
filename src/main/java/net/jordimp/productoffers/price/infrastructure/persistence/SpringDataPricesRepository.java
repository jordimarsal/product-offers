package net.jordimp.productoffers.price.infrastructure.persistence;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import net.jordimp.productoffers.price.domain.entities.Prices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataPricesRepository extends JpaRepository<Prices, Long> {
    List<Prices> findByBrandIdAndProductId(Long brandId, Long productId);

    List<Prices>
            findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                    Long brandId, Long productId, LocalDateTime startDate, LocalDateTime endDate);

    default Optional<Prices>
            findTopByBrandIdAndProductIdAndApplicationDateBetweenOrderByPriorityDesc(
                    Long brandId, Long productId, LocalDateTime applicationDate) {
        return findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                        brandId, productId, applicationDate, applicationDate)
                .stream()
                .findFirst();
    }
}
