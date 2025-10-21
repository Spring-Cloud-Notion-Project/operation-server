package ufrn.imd.operation_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ufrn.imd.operation_server.configs.WebClientConfig;
import ufrn.imd.operation_server.models.Document;
import ufrn.imd.operation_server.request.AINotionRequestInput;
import ufrn.imd.operation_server.response.CreateReportResponse;
import ufrn.imd.operation_server.response.GenerateDocumentResponse;

import java.util.List;

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


    @Override
    public Mono<CreateReportResponse> generateDocument(AINotionRequestInput aiNotionRequest) {
        return aiWebClientService.notionChat(aiNotionRequest)
                .flatMap(aiResponse -> documentWebClientService.createDocument(aiResponse)
                        .map(generateDocumentResponse -> new CreateReportResponse("Relatório criado com sucesso", aiResponse))
                )
                .onErrorMap(ex -> new RuntimeException("Erro ao gerar o documento", ex));
    }

    @Override
    public Flux<Document> getAllDocuments() {
        return documentWebClientService.getAllDocuments();
    }

    @Override
    public Mono<Document> getDocumentById(Long id) {
        //return documentWebClientService.getDocumentById(id);
        return documentCacheService.getDocumentById(id);
    }
}
