package dunice.com.ru.api.sbkafkaproducersample.adapter.in.web.response;

import dunice.com.ru.api.sbkafkaproducersample.domain.enumtype.OutboxStatus;

import java.time.OffsetDateTime;
import java.util.UUID;

public record OutboxEventResponse(
    UUID id,
    String aggregateType,
    String aggregateId,
    String eventType,
    String payload,
    OutboxStatus status,
    OffsetDateTime createdAt,
    OffsetDateTime processedAt,
    String errorMessage
) {
}
