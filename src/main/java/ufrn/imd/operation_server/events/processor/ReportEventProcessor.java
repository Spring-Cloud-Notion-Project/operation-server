package ufrn.imd.operation_server.events.processor;

import reactor.core.publisher.Mono;
import ufrn.imd.operation_server.events.processor.EventProcessor;
import ufrn.imd.operation_server.events.saga.DomainEvent;
import ufrn.imd.operation_server.events.saga.ReportEvent;

public interface ReportEventProcessor<R extends DomainEvent> extends EventProcessor<ReportEvent, R> {

    @Override
    default Mono<R> process(ReportEvent event) {
        return switch (event){
            case ReportEvent.ReportContentGenerated e -> this.handle(e);
            default -> throw new IllegalStateException("Unexpected value: " + event);
        };
    }

    @Override
    default Mono<Void> processConsumer(ReportEvent event) {
        return switch (event){
            case ReportEvent.DocumentCreated e -> this.handleConsumer(e);
            case ReportEvent.ReportFailed e -> this.handleConsumer(e);
            default -> throw new IllegalStateException("Unexpected value: " + event);
        };
    }

    Mono<Void> handleConsumer(ReportEvent.DocumentCreated event);
    Mono<Void> handleConsumer(ReportEvent.ReportFailed event);

    Mono<R> handle(ReportEvent.ReportContentGenerated event);
}
