package ufrn.imd.operation_server.service;

import org.redisson.api.RBucketReactive;
import org.redisson.api.RedissonReactiveClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ufrn.imd.operation_server.models.Document;
import ufrn.imd.operation_server.models.ReportEntity;
import ufrn.imd.operation_server.repositories.ReportRepository;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class ReportCacheService {

    private final DocumentWebClientService documentWebClientService;
    private final RedissonReactiveClient redissonReactiveClient;

    private final ReportRepository reportRepository;

    public ReportCacheService(DocumentWebClientService documentWebClientService,
                              RedissonReactiveClient redissonReactiveClient, ReportRepository reportRepository) {
        this.documentWebClientService = documentWebClientService;
        this.redissonReactiveClient = redissonReactiveClient;
        this.reportRepository = reportRepository;
    }

    public Mono<Document> getDocumentById(Long id) {
        String key = "document:" + id;
        RBucketReactive<Document> bucket = redissonReactiveClient.getBucket(key);
        return bucket.get()
                .switchIfEmpty(
                        documentWebClientService.getDocumentById(id)
                                .flatMap(doc -> bucket.set(doc, 10, TimeUnit.MINUTES).thenReturn(doc)));
    }

    public Mono<ReportEntity> getReportById(UUID id) {
        String key = "report:" + id;
        RBucketReactive<ReportEntity> bucket = redissonReactiveClient.getBucket(key);
        return bucket.get()
                .switchIfEmpty(
                        Mono.fromCallable(() -> reportRepository.findById(id))
                                .flatMap(Mono::justOrEmpty)
                                .flatMap(report -> bucket.set(report, 10, TimeUnit.MINUTES).thenReturn(report)));
    }
}
