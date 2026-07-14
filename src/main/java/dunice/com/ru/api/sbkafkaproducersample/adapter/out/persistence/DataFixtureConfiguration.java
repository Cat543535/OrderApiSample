package dunice.com.ru.api.sbkafkaproducersample.adapter.out.persistence;

import dunice.com.ru.api.sbkafkaproducersample.adapter.out.persistence.entity.ProductJpaEntity;
import dunice.com.ru.api.sbkafkaproducersample.adapter.out.persistence.entity.UserJpaEntity;
import dunice.com.ru.api.sbkafkaproducersample.adapter.out.persistence.repository.ProductJpaRepository;
import dunice.com.ru.api.sbkafkaproducersample.adapter.out.persistence.repository.UserJpaRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class DataFixtureConfiguration {

  @Bean
  ApplicationRunner userFixture(UserJpaRepository userJpaRepository) {
    return args -> {
      if (userJpaRepository.count() == 0) {
        userJpaRepository.save(UserJpaEntity.builder()
            .name("Default User")
            .email("default.user@example.com")
            .build());
      }
    };
  }

  @Bean
  ApplicationRunner productFixture(ProductJpaRepository productJpaRepository) {
    return args -> {
      if (productJpaRepository.count() == 0) {
        productJpaRepository.saveAll(List.of(
            ProductJpaEntity.builder().name("Notebook").price(new BigDecimal("12.50")).build(),
            ProductJpaEntity.builder().name("Keyboard").price(new BigDecimal("45.90")).build(),
            ProductJpaEntity.builder().name("Mouse").price(new BigDecimal("18.00")).build()
        ));
      }
    };
  }
}
