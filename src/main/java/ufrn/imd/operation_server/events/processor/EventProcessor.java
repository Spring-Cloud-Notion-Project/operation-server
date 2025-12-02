package ufrn.imd.operation_server.events.processor;

import reactor.core.publisher.Mono;
import ufrn.imd.operation_server.events.saga.DomainEvent;

// trata um evento de entrada e produz o próximo evento no fluxo
public interface EventProcessor<T extends DomainEvent, R extends DomainEvent> {
    Mono<R> process(T event);
}
