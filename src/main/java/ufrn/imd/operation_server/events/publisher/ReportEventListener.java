package ufrn.imd.operation_server.events.publisher;

import ufrn.imd.operation_server.request.AINotionRequestInput;

import java.util.UUID;

public interface ReportEventListener {
    public void emitReportRequested(AINotionRequestInput payload, UUID id);
}
