package ufrn.imd.operation_server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufrn.imd.operation_server.request.AINotionRequest;
import ufrn.imd.operation_server.service.ReportService;

@RestController
@RequestMapping("/report")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/tasks")
    public ResponseEntity<String> getTasksReport(@RequestBody AINotionRequest aiNotionRequest){
        return reportService.getTasksReport(aiNotionRequest);
    }
}
