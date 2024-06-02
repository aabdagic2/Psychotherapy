package ba.unsa.etf.pnwt.apigateway.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@Component
public class AuthPrefilter extends AbstractGatewayFilterFactory<AuthPrefilter.Config> {

    private final String[] allowAnonymous = new String[] {
            "/userservice/login",
            "/userservice/validate-token",
            "/userservice/registerPatient",
            "/userservice/roles/**"
    };
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    private final WebClient.Builder webClientBuilder;

    @Autowired
    public AuthPrefilter(WebClient.Builder webClientBuilder) {
        super(Config.class);
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if (!isAnonymous.test(request)) {
                String bearerToken = getJwtTokenFromRequest(request);
                return webClientBuilder.build().post()
                        .uri("http://localhost:8080/validate-token")
                        .header(HttpHeaders.AUTHORIZATION, bearerToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(new ValidateTokenRequestDto(config.getRoles()))
                        .retrieve()
                        .bodyToMono(Void.class)
                        .then(chain.filter(exchange))
                        .onErrorResume(error -> {

                            HttpStatusCode errorCode = null;
                            String errorMsg = "";
                            if (error instanceof WebClientResponseException) {
                                WebClientResponseException ex = (WebClientResponseException) error;
                                errorCode = ex.getStatusCode();
                                errorMsg =  ex.getResponseBodyAsString();
                                if (errorCode.value() == HttpStatus.BAD_REQUEST.value()) {
                                    errorCode = HttpStatusCode.valueOf(HttpStatus.UNAUTHORIZED.value());
                                }
                            } else {
                                errorCode = HttpStatusCode.valueOf(502);
                                errorMsg = HttpStatus.BAD_GATEWAY.getReasonPhrase();
                            }
//                            AuthorizationFilter.AUTH_FAILED_CODE
                            return onError(exchange,errorMsg,  errorCode);
                        });
            }
            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatusCode httpStatus) {
        DataBufferFactory dataBufferFactory = exchange.getResponse().bufferFactory();

        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        response.getHeaders().add("Content-Type", "application/json");
        byte[] bytes = err.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);


        return response.writeWith(Flux.just(buffer));
    }

    public static class Config {
        private final List<String> roles;

        public Config(List<String> roles) {
            this.roles = roles;
        }

        public Config() {
            this(List.of());
        }

        public List<String> getRoles() {
            return this.roles;
        }

    }

    private final Predicate<ServerHttpRequest> isAnonymous = request -> Arrays.stream(allowAnonymous).anyMatch(uri -> pathMatcher.match(uri, request.getURI().getPath()));

    private String getJwtTokenFromRequest(ServerHttpRequest request) {
        String bearerToken = request.getHeaders().getFirst("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken;
        }
        return null;
    }
}
