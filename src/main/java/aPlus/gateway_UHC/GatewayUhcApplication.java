package aPlus.gateway_UHC;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.io.File;

@SpringBootApplication
public class GatewayUhcApplication {

	public static void main(String[] args) {
		File envFile = new File(".env");
		if (envFile.exists() && envFile.isFile()) {
			Dotenv dotenv = Dotenv.configure().load();
			System.setProperty("JWT_SIGNER_KEY", dotenv.get("JWT_SIGNER_KEY", ""));
		}
		SpringApplication.run(GatewayUhcApplication.class, args);
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("user_service",
						r -> r.path("/user/**")
								.filters(f -> f
										.rewritePath("/user/(?<segment>.*)", "/${segment}")
										.addResponseHeader("X-Custom-Header", "DanSON Gateway Service"))
								.uri("http://localhost:8081"))

				.route("appointment_service",
						r -> r.path("/appointment/**")
								.filters(f -> f
										.rewritePath("/user/(?<segment>.*)", "/${segment}")
										.addResponseHeader("X-Custom-Header", "DanSON Gateway Service"))
						.uri("https://appointment-service-e6za.onrender.com"))
				.build();
	}
}
