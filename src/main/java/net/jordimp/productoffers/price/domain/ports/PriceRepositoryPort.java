package net.jordimp.productoffers.price.domain.ports;

import java.time.LocalDateTime;
import java.util.Optional;
import net.jordimp.productoffers.price.domain.entities.Prices;

public interface PriceRepositoryPort {

    Optional<Prices> findBestPrice(Long brandId, Long productId, LocalDateTime applicationDate);
}
