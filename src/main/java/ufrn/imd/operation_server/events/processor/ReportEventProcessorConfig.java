package ufrn.imd.operation_server.events.processor;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import reactor.core.publisher.Flux;
import ufrn.imd.operation_server.events.publisher.EventPublisher;
import ufrn.imd.operation_server.events.saga.ReportEvent;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Configuration
@RequiredArgsConstructor
public class ReportEventProcessorConfig {

    private static final Logger log = LoggerFactory.getLogger(ReportEventProcessor.class);

    private final EventPublisher<ReportEvent> eventPublisher;
    private final ReportEventProcessor<ReportEvent> eventProcessor;

    @Bean
    public Supplier<Flux<Message<ReportEvent>>> reportEventProducer() {
        log.info("Requisição de relatório produzida");
        return () -> this.eventPublisher.publish().map(this::toMessage);
    }

    @Bean
    public Consumer<ReportEvent.DocumentCreated> documentProcessor() {
        log.info("Evento de documento criado recebido");
        return event -> eventProcessor.processConsumer(event).block();
    }

    @Bean
    public Consumer<ReportEvent.ReportFailed> failureProcessor() {
        return event -> eventProcessor.processConsumer(event).block();
    }

    protected Message<ReportEvent> toMessage(ReportEvent event) {
        return MessageBuilder.withPayload(event)
                .setHeader(KafkaHeaders.KEY, event.reportId().toString())
                .setHeader("type", event.getClass().getSimpleName())
                .build();
    }
}
