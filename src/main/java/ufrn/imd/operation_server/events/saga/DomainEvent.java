package ufrn.imd.operation_server.events.saga;

import java.time.Instant;

public interface DomainEvent {
    Instant createdAt();
}
