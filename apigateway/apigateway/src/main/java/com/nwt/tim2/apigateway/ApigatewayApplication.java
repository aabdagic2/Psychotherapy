package com.nwt.tim2.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
@EnableDiscoveryClient(autoRegister = true)
public class ApigatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApigatewayApplication.class, args);
	}

	@Bean
	@LoadBalanced
	public RouteLocator routes(RouteLocatorBuilder builder, AuthenticationPrefilter authFilter) {
		return builder.routes()
				.route("userservice", p -> p
						.path("/user-service/**")
						.filters(f ->
								f.filter(authFilter.apply(
										new AuthenticationPrefilter.Config())))
						.uri("lb://userservice"))
				.route("appointmentmanagement", p -> p
						.path("/appointmentmanagement/**")
						.filters(f ->
								f.filter(authFilter.apply(
										new AuthenticationPrefilter.Config())))
						.uri("lb://appointmentmanagement"))
				.route("stressreliefservice", p -> p
						.path("/stressrelief-service/**")
						.filters(f ->
								f.filter(authFilter.apply(
										new AuthenticationPrefilter.Config())))
						.uri("lb://stressreliefservice"))
				.route("articleservice", p -> p
						.path("/article-service/**")
						.filters(f ->
								f.filter(authFilter.apply(
										new AuthenticationPrefilter.Config())))
						.uri("lb://articleservice"))
				.build();

	}}
