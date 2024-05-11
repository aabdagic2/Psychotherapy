package com.nwt.tim2.apigateway;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Component
public class AuthenticationPrefilter extends AbstractGatewayFilterFactory<AuthenticationPrefilter.Config> {
    List<String> excludedUrls = Arrays.asList(
            "user/authenticate",
            "user/register",
            "user/verify",
            "user/refresh-token",
            "user/validate-token",
            "user/notification"
    );

    @Autowired
    private final WebClient.Builder webClientBuilder;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationPrefilter.class);

    public AuthenticationPrefilter(WebClient.Builder webClientBuilder) {
        super(Config.class);
        this.webClientBuilder=webClientBuilder;
    }


    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String bearerToken = "";
            if(request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION) && !request.getHeaders().get(HttpHeaders.AUTHORIZATION).isEmpty())
                bearerToken = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            logger.info("Bearer Token: "+ bearerToken);

            if(isSecured.test(request)) {
                return webClientBuilder.build().post()
                        .uri("lb://userservice/user/validate-token")
                        .header(HttpHeaders.AUTHORIZATION, bearerToken)
                        .retrieve().bodyToMono(UserResponse.class)
                        .map(response -> {
                            exchange.getRequest().mutate().header("email", response.getEmail());
                            exchange.getRequest().mutate().header("role", response.getRole());
                            exchange.getRequest().mutate().header("uuid", response.getUuid());

                            return exchange;
                        })
                        .flatMap(chain::filter)
                        .onErrorResume(error -> {

                            HttpStatusCode errorCode = null;
                            String errorMsg = "";
                            if (error instanceof WebClientResponseException) {
                                WebClientResponseException webCLientException = (WebClientResponseException) error;
                                errorCode = webCLientException.getStatusCode();
                                errorMsg =  webCLientException.getResponseBodyAsString();

                            } else {
                                errorCode = HttpStatusCode.valueOf(502);
                                errorMsg = HttpStatus.BAD_GATEWAY.getReasonPhrase();
                            }
                            return onError(exchange,errorMsg,  errorCode);
                        });
            }

            return chain.filter(exchange);
        };
    }

    public Predicate<ServerHttpRequest> isSecured = request -> excludedUrls.stream().noneMatch(uri -> request.getURI().getPath().contains(uri));
    private Mono<Void> onError(ServerWebExchange exchange,  String err, HttpStatusCode httpStatus) {
        DataBufferFactory dataBufferFactory = exchange.getResponse().bufferFactory();

        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        response.getHeaders().add("Content-Type", "application/json");
        byte[] bytes = err.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);


        return response.writeWith(Flux.just(buffer));
    }

    public static class Config {

        public Config() {}
    }
}