package dunice.com.ru.api.sbkafkaproducersample.application.port.out;

import dunice.com.ru.api.sbkafkaproducersample.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserQueryPort {

  List<User> findAll();

  Optional<User> findById(Long id);
}
