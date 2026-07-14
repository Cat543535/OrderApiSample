package dunice.com.ru.api.sbkafkaproducersample.application.usecase;

import dunice.com.ru.api.sbkafkaproducersample.application.exception.NotFoundException;
import dunice.com.ru.api.sbkafkaproducersample.application.port.out.UserQueryPort;
import dunice.com.ru.api.sbkafkaproducersample.domain.model.User;
import org.springframework.stereotype.Service;

@Service
public class LoadOrderUserService {

  private final UserQueryPort userQueryPort;

  public LoadOrderUserService(UserQueryPort userQueryPort) {
    this.userQueryPort = userQueryPort;
  }

  public User loadById(Long userId) {
    return userQueryPort.findById(userId)
        .orElseThrow(() -> new NotFoundException("User not found for id " + userId));
  }
}
