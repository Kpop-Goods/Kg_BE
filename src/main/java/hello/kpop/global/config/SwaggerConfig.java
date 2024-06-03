package hello.kpop.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

	@Value("${app.base-url}")
	private String baseUrl;

	@Bean
	public OpenAPI openAPI() {
		Server server = new Server();
		server.setUrl(baseUrl); // accessible https://

		return new OpenAPI()
				.components(new Components())
				.info(apiInfo())
				.servers(List.of(server));
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