package ufrn.imd.operation_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ufrn.imd.operation_server.request.AINotionRequest;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    AIServiceInterface aiServiceInterface;

    @Autowired
    ValidationServiceInterface validationServiceInterface;

    @Override
    public ResponseEntity<String> getTasksReport(AINotionRequest aiNotionRequest) {
        String aiResponse;
        try {
            aiResponse = aiServiceInterface.notionChat(aiNotionRequest);
        } catch (RuntimeException ex) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ex.getMessage());
        }

        String validationResponse = validationServiceInterface.validateJson(aiResponse);
        if ("Validation Server is not available.".equals(validationResponse)) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(validationResponse);
        }

        return ResponseEntity.ok(validationResponse);
    }

}
