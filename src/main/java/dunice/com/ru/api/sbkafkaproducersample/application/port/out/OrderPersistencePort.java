package dunice.com.ru.api.sbkafkaproducersample.application.port.out;

import dunice.com.ru.api.sbkafkaproducersample.domain.enumtype.OrderStatus;
import dunice.com.ru.api.sbkafkaproducersample.domain.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderPersistencePort {

  Order save(Order order);

  List<Order> findAll();

  Optional<Order> findById(Long id);

  void updateStatus(Long orderId, OrderStatus status);
}
