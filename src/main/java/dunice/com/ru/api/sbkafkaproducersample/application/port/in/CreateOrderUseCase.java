package dunice.com.ru.api.sbkafkaproducersample.application.port.in;

import dunice.com.ru.api.sbkafkaproducersample.application.dto.CreateOrderCommand;
import dunice.com.ru.api.sbkafkaproducersample.domain.model.Order;

import java.util.concurrent.CompletableFuture;

public interface CreateOrderUseCase {

  CompletableFuture<Order> create(CreateOrderCommand command);
}
