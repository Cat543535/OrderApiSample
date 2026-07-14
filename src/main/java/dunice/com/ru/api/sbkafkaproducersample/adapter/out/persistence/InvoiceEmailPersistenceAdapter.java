package dunice.com.ru.api.sbkafkaproducersample.adapter.out.persistence;

import dunice.com.ru.api.sbkafkaproducersample.adapter.out.persistence.entity.InvoiceEmailJpaEntity;
import dunice.com.ru.api.sbkafkaproducersample.adapter.out.persistence.entity.OrderJpaEntity;
import dunice.com.ru.api.sbkafkaproducersample.adapter.out.persistence.entity.UserJpaEntity;
import dunice.com.ru.api.sbkafkaproducersample.adapter.out.persistence.mapper.PersistenceMapper;
import dunice.com.ru.api.sbkafkaproducersample.adapter.out.persistence.repository.InvoiceEmailJpaRepository;
import dunice.com.ru.api.sbkafkaproducersample.adapter.out.persistence.repository.OrderJpaRepository;
import dunice.com.ru.api.sbkafkaproducersample.adapter.out.persistence.repository.UserJpaRepository;
import dunice.com.ru.api.sbkafkaproducersample.application.exception.NotFoundException;
import dunice.com.ru.api.sbkafkaproducersample.application.port.out.InvoiceEmailPort;
import dunice.com.ru.api.sbkafkaproducersample.domain.model.InvoiceEmail;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@Component
public class InvoiceEmailPersistenceAdapter implements InvoiceEmailPort {

  private final InvoiceEmailJpaRepository invoiceEmailJpaRepository;
  private final UserJpaRepository userJpaRepository;
  private final OrderJpaRepository orderJpaRepository;
  private final PersistenceMapper persistenceMapper;

  public InvoiceEmailPersistenceAdapter(
      InvoiceEmailJpaRepository invoiceEmailJpaRepository,
      UserJpaRepository userJpaRepository,
      OrderJpaRepository orderJpaRepository,
      PersistenceMapper persistenceMapper
  ) {
    this.invoiceEmailJpaRepository = invoiceEmailJpaRepository;
    this.userJpaRepository = userJpaRepository;
    this.orderJpaRepository = orderJpaRepository;
    this.persistenceMapper = persistenceMapper;
  }

  @Override
  public InvoiceEmail save(InvoiceEmail invoiceEmail) {
    UserJpaEntity user = userJpaRepository.findById(invoiceEmail.userId())
        .orElseThrow(() -> new NotFoundException("User not found for id " + invoiceEmail.userId()));
    OrderJpaEntity order = orderJpaRepository.findById(invoiceEmail.orderId())
        .orElseThrow(() -> new NotFoundException("Order not found for id " + invoiceEmail.orderId()));
    InvoiceEmailJpaEntity entity = persistenceMapper.toEntity(invoiceEmail, user, order);
    return persistenceMapper.toDomain(invoiceEmailJpaRepository.save(entity));
  }

  @Override
  public Optional<InvoiceEmail> findById(UUID id) {
    return invoiceEmailJpaRepository.findById(id).map(persistenceMapper::toDomain);
  }

  @Transactional
  @Override
  public void markSent(UUID id) {
    InvoiceEmailJpaEntity entity = invoiceEmailJpaRepository.getReferenceById(id);
    entity.setSentAt(OffsetDateTime.now());
  }
}
