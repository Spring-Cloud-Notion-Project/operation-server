package ufrn.imd.operation_server.configs;


import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.config.Config;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RedissonConfig {

    @Bean(name = "redisson", destroyMethod = "shutdown")
    public RedissonReactiveClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://localhost:6379");
        return Redisson.create(config).reactive();
        // "redis://redis-cache:6379"
    }

//    @Primary
//    @Bean
//    public CacheManager cacheManager(RedissonClient redissonClient) {
//        Map<String, CacheConfig> config = new HashMap<>();
//        CacheConfig cacheConfig = new CacheConfig();
//        cacheConfig.setTTL(600000);
//
//        config.put("document", cacheConfig);
//
//        return new RedissonSpringCacheManager(redissonClient, config);
//    }

//    @Scheduled(fixedRate = 600000)
//    @CacheEvict(value="/document", allEntries = true)
//    public void clearCache() {
//        System.out.println("Limpando cache...");
//    }
}
