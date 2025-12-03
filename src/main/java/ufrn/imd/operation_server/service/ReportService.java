package ufrn.imd.operation_server.service;

import ufrn.imd.operation_server.dto.ReportDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ufrn.imd.operation_server.models.Document;
import ufrn.imd.operation_server.models.ReportStatus;
import ufrn.imd.operation_server.request.AINotionRequestInput;
import ufrn.imd.operation_server.request.CreateReportRequest;
import ufrn.imd.operation_server.response.CreateReportResponse;

import java.util.UUID;

public interface ReportService {

    Flux<ReportDTO> getAllReports();

    // Mono<CreateReportResponse> generateDocument(AINotionRequestInput
    // aiNotionRequest);

    Mono<Document> getDocumentById(Long id);

    Mono<ReportDTO> createReport(CreateReportRequest createReportRequest);

    // Mono<ReportDTO> getReportById(UUID id);

    void updateReportById(UUID id, String documentPath, ReportStatus status);

    void deleteReportById(UUID id);
}
