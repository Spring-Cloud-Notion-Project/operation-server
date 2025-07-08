package ufrn.imd.operation_server.service;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ufrn.imd.operation_server.request.AINotionRequest;

@FeignClient("ai-server")
public interface AIServiceInterface {
    @PostMapping("/notion")
    @CircuitBreaker(name="ai-server", fallbackMethod = "fallbackTaskReport")
    @Bulkhead(name="ai-server", fallbackMethod = "fallbackTaskReport")
    @RateLimiter(name="ai-server", fallbackMethod = "fallbackTaskReport")
    @Retry(name="ai-server", fallbackMethod = "fallbackTaskReport")
    String notionChat(@RequestBody AINotionRequest aiNotionRequest);

    default String fallbackTaskReport(Throwable exception){
        return "AI Server is not available.";
    }
}
