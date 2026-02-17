package net.jordimp.productoffers;

import static org.assertj.core.api.Assertions.assertThat;

import net.jordimp.productoffers.price.application.controllers.ProductOffersController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductoffersApplicationTests {

    @Autowired private ProductOffersController controller;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }
}
