package ufrn.imd.operation_server.events.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ufrn.imd.operation_server.events.saga.ReportEvent;
import ufrn.imd.operation_server.models.ReportStatus;
import ufrn.imd.operation_server.service.ReportService;

@Service
public class ReportEventProcessorImpl implements ReportEventProcessor<ReportEvent> {
    private static final Logger log = LoggerFactory.getLogger(ReportEventProcessor.class);

    private final ReportService reportService;

    public ReportEventProcessorImpl(ReportService reportService) {
        this.reportService = reportService;
    }

    @Override
    public Mono<Void> handleConsumer(ReportEvent.DocumentCreated event) {
        log.info("Atualizando relatório {}.", event.reportId());
        return Mono.fromRunnable(() -> {
            reportService.updateReportById(event.reportId(), event.path(), ReportStatus.COMPLETED);
        });
    }

    @Override
    public Mono<Void> handleConsumer(ReportEvent.ReportFailed event) {
        log.warn("Falha recebida para o relatório {}. Executando exclusão.", event.reportId());
        return Mono.fromRunnable(() -> {
            reportService.deleteReportById(event.reportId());
        });
    }

    @Override
    public Mono<ReportEvent> handle(ReportEvent.ReportContentGenerated event) {
        log.info("Conteudo gerado do relatório {}.", event.reportId());
        return Mono.just(new ReportEvent.CreateReportDocument(event.reportId(), event.content()));
    }

}
