package dunice.com.ru.api.sbkafkaproducersample.application.port.in;

import dunice.com.ru.api.sbkafkaproducersample.domain.model.Order;

import java.util.List;

public interface ListOrdersUseCase {

  List<Order> findAll();
}
