package ba.unsa.etf.pnwt.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	@Bean
	@LoadBalanced
	public RouteLocator routes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("userservice", p -> p
						.path("/USERSERVICE/**")
						.uri("lb://userservice"))


				.route("appointmentservice", p -> p
						.path("/APPOINTMENTSERVICE/**")
						.uri("lb://appointmentservice"))

				.route("articlemanagement", p -> p
						.path("/ARTICLEMANAGEMENT/**")
						.uri("lb://articlemanagement"))

				.route("stressreliefmanagement", p -> p
						.path("/STRESSRELIEFMANAGEMENT/**")
						.uri("lb://stressreliefmanagement"))
				.build();



	}

}
