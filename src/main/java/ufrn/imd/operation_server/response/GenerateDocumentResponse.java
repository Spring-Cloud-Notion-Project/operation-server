package ufrn.imd.operation_server.response;

public record GenerateDocumentResponse(
    Long id,
    String name,
    String fullPath
) {
}
