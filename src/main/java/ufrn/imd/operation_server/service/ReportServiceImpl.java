package ufrn.imd.operation_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
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
    @Override
    public CreateReportResponse generateDocument(AINotionRequestInput aiNotionRequest) {
        String AIResponse;
        try {
            AIResponse = aiServiceInterface.notionChat(aiNotionRequest);
        } catch (RuntimeException ex) {
            throw new RuntimeException("Erro ao se comunicar com a AI");
        }

        GenerateDocumentResponse generateDocumentResponse = documentServiceInterface.createDocument(AIResponse);
        System.out.println(generateDocumentResponse);
        if (generateDocumentResponse.fullPath() != ""){
            CreateReportResponse response = new CreateReportResponse("Relat�rio criado com sucesso", AIResponse);

            return response;
        } else {
            throw new RuntimeException("N�o foi possivel salvar o relat�rio");
        }
    }

    @Override
    public List<Document> getAllDocuments() {
        return documentServiceInterface.getAllDocuments();
    }

    @Override
    @Cacheable("/document")
    public Document getDocumentById(Long id) {
        return documentServiceInterface.getDocumentById(id);
    }
}
