package net.jordimp.productoffers.price.domain.ports;

import java.time.LocalDateTime;
import java.util.Optional;
import net.jordimp.productoffers.price.domain.entities.Price;

public interface PriceRepositoryPort {

    Optional<Price> findBestPrice(Long brandId, Long productId, LocalDateTime applicationDate);
}
