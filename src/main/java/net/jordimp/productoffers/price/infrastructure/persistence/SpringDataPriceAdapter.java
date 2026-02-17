package net.jordimp.productoffers.price.infrastructure.persistence;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import net.jordimp.productoffers.price.domain.entities.Prices;
import net.jordimp.productoffers.price.domain.ports.PriceRepositoryPort;

@Component
@RequiredArgsConstructor
public class SpringDataPriceAdapter implements PriceRepositoryPort {

    private final SpringDataPricesRepository repository;

    @Override
    public Optional<Prices> findBestPrice(Long brandId, Long productId, LocalDateTime applicationDate) {
        return repository.findTopByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
            brandId, productId, applicationDate);
    }

} 
