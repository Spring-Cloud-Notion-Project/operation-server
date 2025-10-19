package ufrn.imd.operation_server.service;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ufrn.imd.operation_server.request.AINotionRequestInput;

@FeignClient("ai-server")
public interface AIServiceInterface {
    @PostMapping("/notion")
    @Retry(name = "ai-server")
    @RateLimiter(name = "ai-server")
    @Bulkhead(name = "ai-server")
    @CircuitBreaker(name = "ai-server", fallbackMethod = "fallbackTaskReportCB")
    String notionChat(@RequestBody AINotionRequestInput aiNotionRequest);

    default String fallbackTaskReportCB(AINotionRequestInput request, Throwable exception) {
        throw new RuntimeException("[CircuitBreaker] AI Server is not available.", exception);
    }

    default String fallbackTaskReportBulkhead(AINotionRequestInput request, Throwable exception) {
        throw new RuntimeException("[Bulkhead] AI Server is not available.", exception);
    }

    default String fallbackTaskReportRL(AINotionRequestInput request, Throwable exception) {
        throw new RuntimeException("[RateLimiter] AI Server is not available.", exception);
    }

    default String fallbackTaskReportRetry(AINotionRequestInput request, Throwable exception) {
        throw new RuntimeException("[Retry] AI Server is not available.", exception);
    }
}