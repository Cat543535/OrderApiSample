package dunice.com.ru.api.sbkafkaproducersample.application.port.in;

import dunice.com.ru.api.sbkafkaproducersample.domain.model.OutboxEvent;

import java.util.List;

public interface ListOutboxEventsUseCase {

  List<OutboxEvent> findAll();
}
