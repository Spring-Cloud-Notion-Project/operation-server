package ufrn.imd.operation_server.service;

import org.redisson.api.RBucketReactive;
import org.redisson.api.RedissonReactiveClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ufrn.imd.operation_server.models.Document;

import java.util.concurrent.TimeUnit;

@Service
public class DocumentCacheService {

    private final DocumentWebClientService documentWebClientService;
    private final RedissonReactiveClient redissonReactiveClient;

    public DocumentCacheService(DocumentWebClientService documentWebClientService, RedissonReactiveClient redissonReactiveClient) {
        this.documentWebClientService = documentWebClientService;
        this.redissonReactiveClient = redissonReactiveClient;
    }

    public Mono<Document> getDocumentById(Long id){
        String key = "document:" + id;
        RBucketReactive<Document> bucket = redissonReactiveClient.getBucket(key);
        return bucket.get()
                .switchIfEmpty(
                        documentWebClientService.getDocumentById(id)
                                .flatMap(doc->bucket.set(doc, 10, TimeUnit.MINUTES).thenReturn(doc))
                );
    }
}
