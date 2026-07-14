package dunice.com.ru.api.sbkafkaproducersample.application.usecase;

import dunice.com.ru.api.sbkafkaproducersample.application.port.in.ListProductsUseCase;
import dunice.com.ru.api.sbkafkaproducersample.application.port.out.ProductQueryPort;
import dunice.com.ru.api.sbkafkaproducersample.domain.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListProductsService implements ListProductsUseCase {

  private final ProductQueryPort productQueryPort;

  public ListProductsService(ProductQueryPort productQueryPort) {
    this.productQueryPort = productQueryPort;
  }

  @Override
  public List<Product> findAll() {
    return productQueryPort.findAll();
  }
}
