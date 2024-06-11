package ba.unsa.etf.pnwt.apigateway;

import ba.unsa.etf.pnwt.apigateway.authentication.AuthPrefilter;
import ba.unsa.etf.pnwt.apigateway.authentication.UserRoles;
import org.apache.catalina.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	@Bean
	@LoadBalanced
	public RouteLocator routes(RouteLocatorBuilder builder, AuthPrefilter authFilter) {
		return builder.routes()
				.route("userservice", p -> p
						.path("/userservice/**")
						.filters(f -> f
								.filter(authFilter.apply(
										new AuthPrefilter.Config(Arrays.asList(UserRoles.administrator, UserRoles.psychologist, UserRoles.patient)))
								)
                                .stripPrefix(1))
						.uri("lb://userservice"))


				.route("appointmentservice", p -> p
						.path("/appointmentservice/**")
						.filters(f -> f
								.filter(authFilter.apply(
										new AuthPrefilter.Config(Arrays.asList(UserRoles.patient, UserRoles.psychologist)))
								)
								.stripPrefix(1))
						.uri("lb://appointmentservice"))

				.route("articleservice", p -> p
						.path("/articleservice/**")
						.filters(f -> f
								.filter(authFilter.apply(
										new AuthPrefilter.Config(
												Arrays.asList(
														UserRoles.patient,
														UserRoles.psychologist,
														UserRoles.administrator
												)
										))
								)
								.stripPrefix(1))
						.uri("lb://articleservice"))

				.route("stressreliefservice/stressrelief/actionlogs", p -> p
						.path("/stressreliefservice/stressrelief/actionlogs")
						.filters(f -> f
								.filter(
										authFilter.apply(
												new AuthPrefilter.Config(Arrays.asList(UserRoles.patient, UserRoles.psychologist)))
								)
								.stripPrefix(1))
						.uri("lb://stressreliefservice/stressrelief/actionlogs"))

				.route("stressreliefservice", p -> p
						.path("/stressreliefservice/**")
						.filters(f -> f
								.filter(
										authFilter.apply(
												new AuthPrefilter.Config(Arrays.asList(UserRoles.patient)))
								)
								.stripPrefix(1))
						.uri("lb://stressreliefservice"))
				.build();
	}

}
