package ufrn.imd.operation_server.dto;

import ufrn.imd.operation_server.models.ReportStatus;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ReportDTO(
        UUID id,
        String title,
        OffsetDateTime requestedDate,
        ReportStatus status
) {
}
