package ufrn.imd.operation_server.events.processor;

import reactor.core.publisher.Mono;
import ufrn.imd.operation_server.events.processor.EventProcessor;
import ufrn.imd.operation_server.events.saga.DomainEvent;
import ufrn.imd.operation_server.events.saga.ReportEvent;

public interface ReportEventProcessor<R extends DomainEvent> extends EventProcessor<ReportEvent, R> {

    @Override
    default Mono<R> process(ReportEvent event) {
        return null;
    }

    @Override
    default Mono<Void> processConsumer(ReportEvent event) {
        return switch (event){
            case ReportEvent.DocumentCreated e -> this.handle(e);
            case ReportEvent.ReportFailed e -> this.handle(e);
            default -> throw new IllegalStateException("Unexpected value: " + event);
        };
    }

    Mono<Void> handle(ReportEvent.DocumentCreated event);
    Mono<Void> handle(ReportEvent.ReportFailed event);
}
