package ufrn.imd.operation_server.service;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ufrn.imd.operation_server.models.Document;
import ufrn.imd.operation_server.response.GenerateDocumentResponse;

@Service
public class DocumentWebClientService {

    private final WebClient webClient;

    public DocumentWebClientService(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("http://document-server:8083") // mesmo nome lógico do Feign
                .build();
    }

    @CircuitBreaker(name = "document-server", fallbackMethod = "fallbackCreateDocument")
    @Bulkhead(name = "document-server")
    @RateLimiter(name = "document-server")
    @Retry(name = "document-server")
    public Mono<GenerateDocumentResponse> createDocument(String text) {
        return webClient.post()
                .uri("/createdocument")
                .bodyValue(text)
                .retrieve()
                .bodyToMono(GenerateDocumentResponse.class);
    }

    @CircuitBreaker(name = "document-server", fallbackMethod = "fallbackGetDocuments")
    @Bulkhead(name = "document-server")
    @RateLimiter(name = "document-server")
    @Retry(name = "document-server")
    public Flux<Document> getAllDocuments() {
        return webClient.get()
                .uri("/getalldocuments")
                .retrieve()
                .bodyToFlux(Document.class);
    }

    @CircuitBreaker(name = "document-server", fallbackMethod = "fallbackGetDocumentById")
    @Bulkhead(name = "document-server")
    @RateLimiter(name = "document-server")
    @Retry(name = "document-server")
    public Mono<Document> getDocumentById(Long id) {
        return webClient.post()
                .uri("/getdocumentbyid")
                .bodyValue(id)
                .retrieve()
                .bodyToMono(Document.class);
    }

}