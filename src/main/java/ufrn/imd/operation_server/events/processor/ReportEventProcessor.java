package ufrn.imd.operation_server.events.processor;

import reactor.core.publisher.Mono;
import ufrn.imd.operation_server.events.processor.EventProcessor;
import ufrn.imd.operation_server.events.saga.DomainEvent;
import ufrn.imd.operation_server.events.saga.ReportEvent;

// processador especializado para eventos da saga de reports
public interface ReportEventProcessor<R extends DomainEvent> extends EventProcessor<ReportEvent, R> {

    @Override
    default Mono<R> process(ReportEvent event) {
        return switch (event){
            case ReportEvent.DocumentCreated e -> this.handle(e);
            default -> throw new IllegalStateException("Unexpected value: " + event);
        };
    }

    Mono<R> handle(ReportEvent.DocumentCreated event);
}
