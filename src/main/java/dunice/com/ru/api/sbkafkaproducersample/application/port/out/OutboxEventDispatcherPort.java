package dunice.com.ru.api.sbkafkaproducersample.application.port.out;

import dunice.com.ru.api.sbkafkaproducersample.domain.model.OutboxEvent;

public interface OutboxEventDispatcherPort {

  boolean supports(String eventType);

  void dispatch(OutboxEvent event);
}
