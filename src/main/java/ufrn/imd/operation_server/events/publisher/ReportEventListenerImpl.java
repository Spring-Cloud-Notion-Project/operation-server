package ufrn.imd.operation_server.events.publisher;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import ufrn.imd.operation_server.events.publisher.EventPublisher;
import ufrn.imd.operation_server.events.publisher.ReportEventListener;
import ufrn.imd.operation_server.events.saga.ReportEvent;

import java.time.Duration;
import java.util.UUID;

import ufrn.imd.operation_server.request.AINotionRequestInput;

public class ReportEventListenerImpl implements ReportEventListener, EventPublisher<ReportEvent> {
    private final Sinks.Many<ReportEvent> sink;
    private final Flux<ReportEvent> flux;

    public ReportEventListenerImpl(Sinks.Many<ReportEvent> sink, Flux<ReportEvent> flux) {
        this.sink = sink;
        this.flux = flux;
    }

    @Override
    public Flux<ReportEvent> publish() {
        return this.flux;
    }

    @Override
    public void emitReportRequested(AINotionRequestInput payload, UUID id) {
        var event = new ReportEvent.ReportRequested(id, payload);
        this.sink.emitNext(
                event, Sinks.EmitFailureHandler.busyLooping(Duration.ofSeconds(1)));
    }
}
