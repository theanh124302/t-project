package tproject.gatewayservice.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class LoggingFilter implements GlobalFilter {
    private static final Logger log = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Path requested: {} ", exchange.getRequest().getPath());
        log.info("Method requested: {} ", exchange.getRequest().getMethod());
        log.info("Query Params: {} ", exchange.getRequest().getQueryParams());
        log.info("Headers: {} ", exchange.getRequest().getHeaders());
        log.info("Remote Address: {} ", exchange.getRequest().getRemoteAddress());
        return chain.filter(exchange);
    }
}