package net.jordimp.productoffers.price.infrastructure.persistence;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataPricesRepository extends JpaRepository<PriceEntity, Long> {
    List<PriceEntity> findByBrandIdAndProductId(Long brandId, Long productId);

    List<PriceEntity>
            findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                    Long brandId, Long productId, LocalDateTime startDate, LocalDateTime endDate);

    default Optional<PriceEntity>
            findTopByBrandIdAndProductIdAndApplicationDateBetweenOrderByPriorityDesc(
                    Long brandId, Long productId, LocalDateTime applicationDate) {
        return findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                        brandId, productId, applicationDate, applicationDate)
                .stream()
                .findFirst();
    }
}
