package dunice.com.ru.api.sbkafkaproducersample.application.port.in;

import dunice.com.ru.api.sbkafkaproducersample.domain.model.User;

import java.util.List;

public interface ListUsersUseCase {

  List<User> findAll();
}
