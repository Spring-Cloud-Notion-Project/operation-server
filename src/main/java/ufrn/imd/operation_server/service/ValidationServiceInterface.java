package ufrn.imd.operation_server.service;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("validation-server")
public interface ValidationServiceInterface {

    @PostMapping("/validatejson")
    @CircuitBreaker(name="validation-server", fallbackMethod = "fallbackJsonValidation")
    @Bulkhead(name="validation-server")
    @RateLimiter(name="validation-server")
    @Retry(name="validation-server")
    String validateJson(@RequestBody String json);

    default String fallbackJsonValidation(Throwable exception){
        return "Validation Server is not available.";
    }
}
