package dunice.com.ru.api.sbkafkaproducersample.application.port.in;

import dunice.com.ru.api.sbkafkaproducersample.domain.model.Product;

import java.util.List;

public interface ListProductsUseCase {

  List<Product> findAll();
}
