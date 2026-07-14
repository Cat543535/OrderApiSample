package dunice.com.ru.api.sbkafkaproducersample.application.usecase;

import dunice.com.ru.api.sbkafkaproducersample.application.port.in.ListUsersUseCase;
import dunice.com.ru.api.sbkafkaproducersample.application.port.out.UserQueryPort;
import dunice.com.ru.api.sbkafkaproducersample.domain.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListUsersService implements ListUsersUseCase {

  private final UserQueryPort userQueryPort;

  public ListUsersService(UserQueryPort userQueryPort) {
    this.userQueryPort = userQueryPort;
  }

  @Override
  public List<User> findAll() {
    return userQueryPort.findAll();
  }
}
