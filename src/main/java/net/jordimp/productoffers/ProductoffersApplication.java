package net.jordimp.productoffers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("net.jordimp.productoffers.price.domain.repositories.**")
@ComponentScan(basePackages = { "net.jordimp.productoffers.price.*" })
@EntityScan("net.jordimp.productoffers.price.domain.entities.**")
@SpringBootApplication
public class ProductoffersApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductoffersApplication.class, args);
	}

}
