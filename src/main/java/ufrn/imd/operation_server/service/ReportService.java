package ufrn.imd.operation_server.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ufrn.imd.operation_server.models.Document;
import ufrn.imd.operation_server.request.AINotionRequestInput;
import ufrn.imd.operation_server.response.CreateReportResponse;

import java.util.List;

public interface ReportService {
    Mono<CreateReportResponse> generateDocument(AINotionRequestInput aiNotionRequest);

    Flux<Document> getAllDocuments();

    Mono<Document> getDocumentById(Long id);

}
