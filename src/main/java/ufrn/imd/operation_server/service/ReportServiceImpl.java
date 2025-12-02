package ufrn.imd.operation_server.service;

import ufrn.imd.operation_server.dto.ReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ufrn.imd.operation_server.models.ReportEntity;
import ufrn.imd.operation_server.models.ReportStatus;
import ufrn.imd.operation_server.repositories.ReportRepository;
import ufrn.imd.operation_server.events.publisher.ReportEventListener;
import ufrn.imd.operation_server.models.Document;
import ufrn.imd.operation_server.request.AINotionRequestInput;
import ufrn.imd.operation_server.request.CreateReportRequest;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    AIServiceInterface aiServiceInterface;

    @Autowired
    DocumentServiceInterface documentServiceInterface;

    @Autowired
    DocumentCacheService documentCacheService;

    @Autowired
    DocumentWebClientService documentWebClientService;

    @Autowired
    AIWebClientService aiWebClientService;

    @Autowired
    ReportEventListener eventListener;

    @Autowired
    ReportRepository reportRepository;

    @Override
    public Flux<ReportDTO> getAllReports() {
        return Flux.fromIterable(reportRepository.findAll())
                .map(report -> new ReportDTO(
                        report.getId(),
                        report.getDocumentPath(),
                        report.getRequestedDate(),
                        report.getStatus()));
    }

//    @Override
//    public Mono<CreateReportResponse> generateDocument(AINotionRequestInput aiNotionRequest) {
//        // chamar o eventListener e emitir o report requested
//        // return aiWebClientService.notionChat(aiNotionRequest)
//        // .flatMap(aiResponse -> documentWebClientService.createDocument(aiResponse)
//        // .map(generateDocumentResponse -> new CreateReportResponse("Relatório criado
//        // com sucesso", aiResponse))
//        // )
//        // .onErrorMap(ex -> new RuntimeException("Erro ao gerar o documento", ex));
//        // Mock
//        CreateReportResponse response = new CreateReportResponse("Relatório criado com sucesso", "Ai response");
//        return Mono.just(response).doOnNext(r -> eventListener.emitReportRequested(aiNotionRequest));
//    }

    @Override
    public Mono<Document> getDocumentById(Long id) {
        // return documentWebClientService.getDocumentById(id);
        return documentCacheService.getDocumentById(id);
    }

    @Override
    public Mono<ReportDTO> createReport(CreateReportRequest createReportRequest) {

        ReportEntity report = new ReportEntity(
                UUID.randomUUID(),
                createReportRequest.title(),
                null,
                OffsetDateTime.now(),
                ReportStatus.PENDING
        );

        return Mono.fromCallable(() -> reportRepository.save(report))
                .map(saved -> new ReportDTO(
                        saved.getId(),
                        saved.getDocumentPath(),
                        saved.getRequestedDate(),
                        saved.getStatus()
                ))
                .doOnNext(dto -> {
                    AINotionRequestInput aiRequest = new AINotionRequestInput(createReportRequest.page(), createReportRequest.prompt());
                    eventListener.emitReportRequested(aiRequest, dto.id());
                });
    }
}
