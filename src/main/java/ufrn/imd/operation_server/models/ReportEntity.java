package ufrn.imd.operation_server.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Data
public class ReportEntity {

        @Id
        private UUID id;

        private String title;

        private String documentPath;

        private OffsetDateTime requestedDate;

        @Enumerated(EnumType.STRING)
        private ReportStatus status;

        public ReportEntity() {}

        public ReportEntity(UUID id, String title, String documentPath, OffsetDateTime requestedDate, ReportStatus status) {
                this.id = id;
                this.title = title;
                this.documentPath = documentPath;
                this.requestedDate = requestedDate;
                this.status = status;
        }
}