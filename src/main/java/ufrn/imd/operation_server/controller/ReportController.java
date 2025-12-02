package ufrn.imd.operation_server.controller;

import ufrn.imd.operation_server.dto.ReportDTO;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ufrn.imd.operation_server.models.Document;
import ufrn.imd.operation_server.request.AINotionRequestInput;
import ufrn.imd.operation_server.request.CreateReportRequest;
import ufrn.imd.operation_server.service.ReportService;

@RestController
@RequestMapping("/reports")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

//    @MutationMapping
//    public Mono<Boolean> generateReport(@Argument AINotionRequestInput input){
//        return reportService.generateDocument(input)
//                .thenReturn(true);
//    }
    @QueryMapping
    public Flux<ReportDTO> getAllReports(){
        return reportService.getAllReports();
    }

    @QueryMapping
    public Mono<Document> getDocumentById(@Argument String id){
        return reportService.getDocumentById(Long.valueOf(id));
    }

    @MutationMapping
    public Mono<ReportDTO> createReport(@Argument CreateReportRequest input) {
        return reportService.createReport(input);
    }
}
