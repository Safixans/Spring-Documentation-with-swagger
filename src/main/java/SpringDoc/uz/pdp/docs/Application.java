package SpringDoc.uz.pdp.docs;

import SpringDoc.uz.pdp.docs.entities.Store;
import SpringDoc.uz.pdp.docs.repository.StoreRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.net.URL;
import java.util.List;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "PDP Online Java(SpringDOC)",
				version = "10",
				contact = @Contact(
						name = "Safixon Abdusattorov",
						email = "safixongg@gmail.com",
						url = "https://github.com/safixans"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://springdoc.org"
				),
				termsOfService = "http://swagger.io/terms/",
				description = "This Document Designed For Teaching SpringDOC Project"
		),
		externalDocs = @ExternalDocumentation(
				description = "SPRING DOC version 2",
				url = "https://springdoc.org/v2"
		),
		servers = {
				@Server(
						url = "http://localhost:8080",
						description = "Production-Server"
				),
				@Server(
						url = "http://localhost:9090",
						description = "Test-Server"
				)
		}
)
@SecurityScheme(
		name = "Bearer Authentication",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		scheme = "bearer"
)
@SecurityScheme(
		name = "basicauth",
		type = SecuritySchemeType.HTTP,
		scheme = "basic"
)
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner runner(ObjectMapper objectMapper,
							 StoreRepository storeRepository
							 ){
		return (args) ->{
			List<Store> stores = objectMapper.readValue(new URL("https://jsonplaceholder.typicode.com/stores"), new TypeReference<>() {
			});
			storeRepository.saveAll(stores);
		};
	}

	@Bean
	public GroupedOpenApi store() {
		return GroupedOpenApi.builder()
				.group("store")
				.pathsToMatch("/api/stores/**")
				.build();
	}



}
