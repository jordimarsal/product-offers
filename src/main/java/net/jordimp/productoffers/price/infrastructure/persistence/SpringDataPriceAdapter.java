package net.jordimp.productoffers.price.infrastructure.persistence;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Component;

import java.util.Comparator;

import lombok.RequiredArgsConstructor;
import net.jordimp.productoffers.price.domain.entities.Prices;
import net.jordimp.productoffers.price.domain.ports.PriceRepositoryPort;

@Component
@RequiredArgsConstructor
public class SpringDataPriceAdapter implements PriceRepositoryPort {

    private final SpringDataPricesRepository repository;

    @Override
    public Optional<Prices> findBestPrice(Long brandId, Long productId, LocalDateTime applicationDate) {
        // defensive: use repository list but pick the highest priority explicitly
        return repository.findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
            brandId, productId, applicationDate, applicationDate).stream()
            .max(Comparator.comparing(Prices::getPriority));
    }

} 
