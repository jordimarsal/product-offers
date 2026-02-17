package net.jordimp.productoffers.price.infrastructure.persistence;

import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import net.jordimp.productoffers.price.domain.entities.Price;
import net.jordimp.productoffers.price.domain.ports.PriceRepositoryPort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpringDataPriceAdapter implements PriceRepositoryPort {

    private final SpringDataPricesRepository repository;

    @Override
    public Optional<Price> findBestPrice(
            Long brandId, Long productId, LocalDateTime applicationDate) {
        // prefer repository-provided top-by-priority method to avoid redundant stream operations
        return repository
                .findTopByBrandIdAndProductIdAndApplicationDateBetweenOrderByPriorityDesc(
                        brandId, productId, applicationDate)
                .map(PriceEntityMapper::toDomain);
    }
}
