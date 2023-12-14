package net.jordimp.productoffers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import net.jordimp.productoffers.controllers.ProductOffersController;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProductoffersApplicationTests {

    @Autowired
    private ProductOffersController controller;

	@Test
	void contextLoads() {
        assertThat(controller).isNotNull();
	}

}
