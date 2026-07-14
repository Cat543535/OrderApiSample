package dunice.com.ru.api.sbkafkaproducersample.adapter.out.persistence.repository;

import dunice.com.ru.api.sbkafkaproducersample.adapter.out.persistence.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Long> {
}
