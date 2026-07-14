package dunice.com.ru.api.sbkafkaproducersample.application.usecase;

import dunice.com.ru.api.sbkafkaproducersample.application.port.in.ListOrdersUseCase;
import dunice.com.ru.api.sbkafkaproducersample.application.port.out.OrderPersistencePort;
import dunice.com.ru.api.sbkafkaproducersample.domain.model.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListOrdersService implements ListOrdersUseCase {

  private final OrderPersistencePort orderPersistencePort;

  public ListOrdersService(OrderPersistencePort orderPersistencePort) {
    this.orderPersistencePort = orderPersistencePort;
  }

  @Override
  public List<Order> findAll() {
    return orderPersistencePort.findAll();
  }
}
