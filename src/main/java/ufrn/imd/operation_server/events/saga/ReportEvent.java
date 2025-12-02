package ufrn.imd.operation_server.events.saga;

import ufrn.imd.operation_server.events.saga.DomainEvent;
import ufrn.imd.operation_server.events.saga.ReportSaga;

import java.time.Instant;
import java.util.UUID;

import ufrn.imd.operation_server.request.AINotionRequestInput;

public sealed interface ReportEvent extends DomainEvent, ReportSaga {

    record ReportRequested(UUID reportId, AINotionRequestInput payload) implements ReportEvent {
        @Override
        public Instant createdAt() {
            return null;
        }
    }
    record DocumentCreated(UUID reportId, String path) implements ReportEvent{
        @Override
        public Instant createdAt() {
            return null;
        }
    }

    record ReportFailed(UUID reportId) implements ReportEvent {
        @Override
        public Instant createdAt() {
            return null;
        }
    }
}
