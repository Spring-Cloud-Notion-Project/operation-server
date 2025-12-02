package ufrn.imd.operation_server.service;

import ufrn.imd.operation_server.dto.ReportDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ufrn.imd.operation_server.models.Document;
import ufrn.imd.operation_server.models.ReportStatus;
import ufrn.imd.operation_server.request.AINotionRequestInput;
import ufrn.imd.operation_server.request.CreateReportRequest;
import ufrn.imd.operation_server.response.CreateReportResponse;

public interface ReportService {

    Flux<ReportDTO> getAllReports();

//    Mono<CreateReportResponse> generateDocument(AINotionRequestInput aiNotionRequest);

    Mono<Document> getDocumentById(Long id);

    Mono<ReportDTO> createReport(CreateReportRequest createReportRequest);
}
