package ufrn.imd.operation_server.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.*;
import ufrn.imd.operation_server.models.Document;
import ufrn.imd.operation_server.request.AINotionRequestInput;
import ufrn.imd.operation_server.response.CreateReportResponse;
import ufrn.imd.operation_server.service.ReportService;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

//    @PostMapping("/chat")
//    public ResponseEntity<CreateReportResponse> generateReport(@RequestBody AINotionRequest aiNotionRequest){
//        CreateReportResponse response = reportService.generateDocument(aiNotionRequest);
//
//        return ResponseEntity.ok(response);
//    }

    @MutationMapping
    public CreateReportResponse generateReport(@Argument AINotionRequestInput input){
        CreateReportResponse response = reportService.generateDocument(input);

        return response;
    }

    //@GetMapping
    @QueryMapping
    public List<Document> getAllReports(){
        List<Document> allDocuments = reportService.getAllDocuments();

        return allDocuments;
    }

    //@GetMapping("/{id}")
    @QueryMapping
    public Document getDocumentById(@Argument String id){
        Document document = reportService.getDocumentById(Long.valueOf(id));

        return document;
    }
}
