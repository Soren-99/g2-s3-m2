package sorenrahimi.g2s3m2.payloads.errors;

import java.time.LocalDateTime;

public record ErrorsPayload(
        String message,
        LocalDateTime timestamp) { }


