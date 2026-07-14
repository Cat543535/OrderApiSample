package dunice.com.ru.api.sbkafkaproducersample.adapter.out.persistence;

import dunice.com.ru.api.sbkafkaproducersample.adapter.out.persistence.mapper.PersistenceMapper;
import dunice.com.ru.api.sbkafkaproducersample.adapter.out.persistence.repository.UserJpaRepository;
import dunice.com.ru.api.sbkafkaproducersample.application.port.out.UserQueryPort;
import dunice.com.ru.api.sbkafkaproducersample.domain.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserPersistenceAdapter implements UserQueryPort {

  private final UserJpaRepository userJpaRepository;
  private final PersistenceMapper persistenceMapper;

  public UserPersistenceAdapter(UserJpaRepository userJpaRepository, PersistenceMapper persistenceMapper) {
    this.userJpaRepository = userJpaRepository;
    this.persistenceMapper = persistenceMapper;
  }

  @Override
  public List<User> findAll() {
    return userJpaRepository.findAll().stream().map(persistenceMapper::toDomain).toList();
  }

  @Override
  public Optional<User> findById(Long id) {
    return userJpaRepository.findById(id).map(persistenceMapper::toDomain);
  }
}
