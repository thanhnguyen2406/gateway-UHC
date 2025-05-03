package aPlus.gateway_UHC;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.io.File;

@SpringBootApplication
public class GatewayUhcApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayUhcApplication.class, args);
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("user_service",
						r -> r.path("/user/**")
								.filters(f -> f
										.rewritePath("/user/(?<segment>.*)", "/${segment}")
										)
								.uri("https://user-domain-1-0-0.onrender.com"))

				.route("appointment_service",
						r -> r.path("/appointment/**")
								.filters(f -> f
										.rewritePath("/user/(?<segment>.*)", "/${segment}")
										)
						.uri("https://appointment-service-e6za.onrender.com"))
				.build();
	}
}
