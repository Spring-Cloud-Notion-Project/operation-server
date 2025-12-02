package ufrn.imd.operation_server.service;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ufrn.imd.operation_server.request.AINotionRequestInput;

@Service
public class AIWebClientService {

    private final WebClient webClient;

    public AIWebClientService(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("http://ai-server:8081") // mesmo nome lógico do Feign
                .build();
    }

    @Retry(name = "ai-server")
    @RateLimiter(name = "ai-server")
    @Bulkhead(name = "ai-server")
    @CircuitBreaker(name = "ai-server", fallbackMethod = "fallbackTaskReportCB")
    public Mono<String> notionChat(AINotionRequestInput request) {
        return webClient.post()
                .uri("/notion")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class);
    }
}
