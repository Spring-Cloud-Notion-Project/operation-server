package ufrn.imd.operation_server.events.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import ufrn.imd.operation_server.events.saga.ReportEvent;

public abstract class AbstractReportEventRouterConfig {
    private static final Logger log = LoggerFactory.getLogger(AbstractReportEventRouterConfig.class);
    //private static final String DESTINATION_HEADER = "spring.cloud.stream.sendto.destination";
    //private static final String REPORT_EVENTS_CHANNEL = "reportRequests-out-0";
//    protected <T extends DomainEvent>Function<Flux<Message<T>>, Flux<Message<ReportEvent>>> processor(EventProcessor<T, ReportEvent> eventProcessor) {
//        return flux -> flux.map()
//    }

    protected Message<ReportEvent> toMessage(ReportEvent event) {
        log.info("Requisição de relatório produzida: {}", event.reportId());

        return MessageBuilder.withPayload(event)
                .setHeader(KafkaHeaders.KEY, event.reportId().toString())
                .setHeader("type", event.getClass().getSimpleName())
                .build();
    }

}
