package ufrn.imd.operation_server.events.saga;

import ufrn.imd.operation_server.events.saga.Saga;

import java.util.UUID;

public interface ReportSaga extends Saga {
    UUID reportId();
}
