package dunice.com.ru.api.sbkafkaproducersample.application.port.out;

import dunice.com.ru.api.sbkafkaproducersample.domain.model.InvoiceEmail;

import java.util.Optional;
import java.util.UUID;

public interface InvoiceEmailPort {

  InvoiceEmail save(InvoiceEmail invoiceEmail);

  Optional<InvoiceEmail> findById(UUID id);

  void markSent(UUID id);
}
