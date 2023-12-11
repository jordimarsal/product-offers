package net.jordimp.productoffers;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        // if WAR is used, this method is needed in Tomcat
		return application.sources(ProductoffersApplication.class);
	}

}
