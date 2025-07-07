package ufrn.imd.operation_server.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ufrn.imd.operation_server.request.AINotionRequest;

@FeignClient("ai-server")
public interface AIServiceInterface {
    @PostMapping("/notion")
    String notionChat(@RequestBody AINotionRequest aiNotionRequest);
}
