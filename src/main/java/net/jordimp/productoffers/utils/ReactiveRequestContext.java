package net.jordimp.productoffers.utils;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class ReactiveRequestContext {

    public static final String CONTEXT_KEY = "context";

    public static Mono<ServerHttpRequest> getRequest() {
        return Mono.deferContextual(Mono::just)
            .map(ctx -> ctx.get(CONTEXT_KEY));
    }
}
