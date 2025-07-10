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
    @Retry(name = "ai-server")
    @RateLimiter(name = "ai-server")
    @Bulkhead(name = "ai-server")
    @CircuitBreaker(name = "ai-server", fallbackMethod = "fallbackTaskReportCB")
    String notionChat(@RequestBody AINotionRequest aiNotionRequest);

    default String fallbackTaskReportCB(AINotionRequest request, Throwable exception) {
        throw new RuntimeException("[CircuitBreaker] AI Server is not available.", exception);
    }

    default String fallbackTaskReportBulkhead(AINotionRequest request, Throwable exception) {
        throw new RuntimeException("[Bulkhead] AI Server is not available.", exception);
    }

    default String fallbackTaskReportRL(AINotionRequest request, Throwable exception) {
        throw new RuntimeException("[RateLimiter] AI Server is not available.", exception);
    }

    default String fallbackTaskReportRetry(AINotionRequest request, Throwable exception) {
        throw new RuntimeException("[Retry] AI Server is not available.", exception);
    }
}