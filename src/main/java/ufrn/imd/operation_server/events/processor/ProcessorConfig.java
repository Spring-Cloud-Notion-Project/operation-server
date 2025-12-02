package ufrn.imd.operation_server.events.processor;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import reactor.core.publisher.Flux;
import ufrn.imd.operation_server.events.publisher.AbstractReportEventRouterConfig;
import ufrn.imd.operation_server.events.publisher.EventPublisher;
import ufrn.imd.operation_server.events.saga.ReportEvent;

import ufrn.imd.operation_server.models.ReportStatus;
import ufrn.imd.operation_server.repositories.ReportRepository;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Configuration
@RequiredArgsConstructor
public class ProcessorConfig extends AbstractReportEventRouterConfig {

    private static final Logger log = LoggerFactory.getLogger(AbstractReportEventRouterConfig.class);

    private final EventPublisher<ReportEvent> eventPublisher;
    private final ReportRepository reportRepository;

    @Bean
    public Supplier<Flux<Message<ReportEvent>>> reportEventProducer() {
        return () -> this.eventPublisher.publish().map(this::toMessage);
    }

    @Bean
    public Consumer<ReportEvent.DocumentCreated> documentProcessor() {
        return event -> {
            reportRepository.findById(event.reportId()).ifPresent(report -> {
                report.setStatus(ReportStatus.COMPLETED);
                report.setDocumentPath(event.path());
                reportRepository.save(report);
            });
        };
    }

    @Bean
    public Consumer<ReportEvent.ReportFailed> failureProcessor() {
        return event -> {
            log.warn("Falha recebida para o relatório {}. Executando exclusão.",
                    event.reportId());
            reportRepository.deleteById(event.reportId());
        };
    }
}
