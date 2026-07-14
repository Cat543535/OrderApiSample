package dunice.com.ru.api.sbkafkaproducersample.application.dto;

import java.util.List;

public record CreateOrderCommand(Long userId, List<CreateOrderItemCommand> items) {
}
