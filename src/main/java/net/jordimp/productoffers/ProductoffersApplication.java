package net.jordimp.productoffers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = {
    "net.jordimp.productoffers.price.application",
    "net.jordimp.productoffers.price.domain",
    "net.jordimp.productoffers.price.infrastructure",
    "net.jordimp.productoffers.shared"
})
@EntityScan("net.jordimp.productoffers.price.domain.entities")
// Keep repository auto-discovery (Spring Boot will auto-configure JPA repositories).
// Component scan limited to application + domain packages to avoid loading
// infrastructure beans during slice tests (WebMvcTest).
@SpringBootApplication
public class ProductoffersApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductoffersApplication.class, args);
	}

}
