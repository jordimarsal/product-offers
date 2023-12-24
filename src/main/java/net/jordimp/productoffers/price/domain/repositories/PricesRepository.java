package net.jordimp.productoffers.price.domain.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.jordimp.productoffers.price.domain.entities.Prices;

@Repository
public interface PricesRepository extends JpaRepository<Prices, Long> {
    List<Prices> findByBrandIdAndProductId(Long brandId, Long productId);

    @Query("SELECT p FROM Prices p WHERE p.brandId = :brandId AND p.productId = :productId " +
           "AND p.startDate <= :applicationDate AND p.endDate >= :applicationDate " +
           "ORDER BY p.priority DESC")
    Optional<Prices> findTopByBrandIdAndProductIdAndApplicationDateBetweenOrderByPriorityDesc(
        @Param("brandId") Long brandId,
        @Param("productId") Long productId,
        @Param("applicationDate") LocalDateTime applicationDate);

}
