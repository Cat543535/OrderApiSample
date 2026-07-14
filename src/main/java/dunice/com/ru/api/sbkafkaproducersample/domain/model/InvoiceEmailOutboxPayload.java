package dunice.com.ru.api.sbkafkaproducersample.domain.model;

import java.util.UUID;

public record InvoiceEmailOutboxPayload(UUID invoiceEmailId) {
}
