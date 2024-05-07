package hello.kpop.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
				.components(new Components())
				.info(apiInfo());
	}

	private Info apiInfo() {
		return new Info()
				.title("APIs of K-Pop Toy Project")
				.description("K-Pop APIs Tester by Swagger/SpringDoc")
				.contact(new Contact()
						.name("Tony - Taehoon Park")
						.email("anthony.park@yahoo.com"))
				.version("1.0.0");
	}
}