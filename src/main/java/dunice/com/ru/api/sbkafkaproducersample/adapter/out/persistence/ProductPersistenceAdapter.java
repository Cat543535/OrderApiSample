package dunice.com.ru.api.sbkafkaproducersample.adapter.out.persistence;

import dunice.com.ru.api.sbkafkaproducersample.adapter.out.persistence.mapper.PersistenceMapper;
import dunice.com.ru.api.sbkafkaproducersample.adapter.out.persistence.repository.ProductJpaRepository;
import dunice.com.ru.api.sbkafkaproducersample.application.port.out.ProductQueryPort;
import dunice.com.ru.api.sbkafkaproducersample.domain.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class ProductPersistenceAdapter implements ProductQueryPort {

  private final ProductJpaRepository productJpaRepository;
  private final PersistenceMapper persistenceMapper;

  public ProductPersistenceAdapter(ProductJpaRepository productJpaRepository, PersistenceMapper persistenceMapper) {
    this.productJpaRepository = productJpaRepository;
    this.persistenceMapper = persistenceMapper;
  }

  @Override
  public List<Product> findAll() {
    return productJpaRepository.findAll().stream().map(persistenceMapper::toDomain).toList();
  }

  @Override
  public Optional<Product> findById(Long id) {
    return productJpaRepository.findById(id).map(persistenceMapper::toDomain);
  }

  @Override
  public List<Product> findAllByIds(Set<Long> ids) {
    return productJpaRepository.findByIdIn(ids).stream().map(persistenceMapper::toDomain).toList();
  }
}
