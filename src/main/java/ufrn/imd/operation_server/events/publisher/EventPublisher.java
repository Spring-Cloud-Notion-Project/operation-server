package ufrn.imd.operation_server.events.publisher;

import reactor.core.publisher.Flux;
import ufrn.imd.operation_server.events.saga.DomainEvent;

// disponibiliza o fluxo contínuo de eventos para o Saga
public interface EventPublisher <T extends DomainEvent> {
    Flux<T> publish();
}
