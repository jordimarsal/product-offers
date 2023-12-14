package net.jordimp.productoffers.repositories;


import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.jordimp.productoffers.entities.Prices;

@Repository
public interface PricesRepository extends CrudRepository<Prices, Long> {
    List<Prices> findByBrandIdAndProductId(Long brandId, Long productId);

}
