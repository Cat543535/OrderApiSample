package dunice.com.ru.api.sbkafkaproducersample.application.dto;

public record CreateOrderItemCommand(Long productId, Integer quantity) {
}
