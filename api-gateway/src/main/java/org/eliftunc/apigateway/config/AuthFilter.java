package org.eliftunc.apigateway.config;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthFilter implements GlobalFilter {

    private final WebClient.Builder webClientBuilder;

    public AuthFilter(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().value();

        if(path.equals("/api/auth/authenticate") || path.equals("/api/auth/validate") ||
                path.equals("/api/users/create-user") || path.equals("/api/auth/refreshToken")){
            return chain.filter(exchange);
        }

        return webClientBuilder.build()
                .post()
                .uri("http://localhost:8084/api/auth/validate")
                .headers(headers-> headers.addAll(exchange.getRequest().getHeaders()))
                .retrieve()
                .toBodilessEntity()
                .flatMap(response ->{
                    if(response.getStatusCode().is2xxSuccessful()){
                        return chain.filter(exchange);
                    } else {
                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        return exchange.getResponse().setComplete();
                    }
                })
                .onErrorResume(error -> {
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                });
    }
}