package ufrn.imd.operation_server.service;

import ufrn.imd.operation_server.models.Document;
import ufrn.imd.operation_server.request.AINotionRequestInput;
import ufrn.imd.operation_server.response.CreateReportResponse;

import java.util.List;

public interface ReportService {
    CreateReportResponse generateDocument(AINotionRequestInput aiNotionRequest);

    List<Document> getAllDocuments();

    Document getDocumentById(Long id);

}
