package dunice.com.ru.api.sbkafkaproducersample.application.usecase;

import dunice.com.ru.api.sbkafkaproducersample.application.port.in.ListOutboxEventsUseCase;
import dunice.com.ru.api.sbkafkaproducersample.application.port.out.OutboxEventPort;
import dunice.com.ru.api.sbkafkaproducersample.domain.model.OutboxEvent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListOutboxEventsService implements ListOutboxEventsUseCase {

  private final OutboxEventPort outboxEventPort;

  public ListOutboxEventsService(OutboxEventPort outboxEventPort) {
    this.outboxEventPort = outboxEventPort;
  }

  @Override
  public List<OutboxEvent> findAll() {
    return outboxEventPort.findAll();
  }
}
