package ufrn.imd.operation_server.service;

import org.springframework.http.ResponseEntity;
import ufrn.imd.operation_server.request.AINotionRequest;

public interface ReportService {
    ResponseEntity<String> getTasksReport(AINotionRequest aiNotionRequest);
}
