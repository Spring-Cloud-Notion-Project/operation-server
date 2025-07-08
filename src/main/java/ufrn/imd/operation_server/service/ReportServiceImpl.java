package ufrn.imd.operation_server.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ufrn.imd.operation_server.request.AINotionRequest;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    AIServiceInterface aiServiceInterface;

    @Override
    public ResponseEntity<String> getTasksReport(AINotionRequest aiNotionRequest) {
        return ResponseEntity.ok(aiServiceInterface.notionChat(aiNotionRequest));
    }

}
