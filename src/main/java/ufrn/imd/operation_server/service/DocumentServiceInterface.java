package ufrn.imd.operation_server.service;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ufrn.imd.operation_server.models.Document;
import ufrn.imd.operation_server.response.GenerateDocumentResponse;

import java.util.ArrayList;
import java.util.List;

@FeignClient("document-server")
public interface DocumentServiceInterface {

    @PostMapping("/createdocument")
    @CircuitBreaker(name="document-server", fallbackMethod = "fallbackJsonValidation")
    @Bulkhead(name="document-server")
    @RateLimiter(name="document-server")
    @Retry(name="document-server")
    GenerateDocumentResponse createDocument(@RequestBody String text);

    @GetMapping("/getalldocuments")
    @CircuitBreaker(name="document-server", fallbackMethod = "fallbackJsonValidation")
    @Bulkhead(name="document-server")
    @RateLimiter(name="document-server")
    @Retry(name="document-server")
    List<Document> getAllDocuments();

    @PostMapping("/getdocumentbyid")
    @CircuitBreaker(name="document-server", fallbackMethod = "fallbackJsonValidation")
    @Bulkhead(name="document-server")
    @RateLimiter(name="document-server")
    @Retry(name="document-server")
    Document getDocumentById(@RequestBody Long id);

    default String fallbackCreateDocument(Throwable exception){
        return "N�o foi poss�vel gerar o documento, o Document Server est� indispon�vel";
    }

    default List<Document> fallbackGetDocuments(Throwable exception){
        return new ArrayList<>();
    }

    default String fallbackGetDocumentById(Throwable exception){
        return "Documento n�o encontrado";
    }
}
