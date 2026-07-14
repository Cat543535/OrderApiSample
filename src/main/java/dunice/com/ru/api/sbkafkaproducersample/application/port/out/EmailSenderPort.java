package dunice.com.ru.api.sbkafkaproducersample.application.port.out;

import dunice.com.ru.api.sbkafkaproducersample.domain.model.InvoiceEmail;

public interface EmailSenderPort {

  void send(InvoiceEmail invoiceEmail);
}
