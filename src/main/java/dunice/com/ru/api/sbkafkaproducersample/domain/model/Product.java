package dunice.com.ru.api.sbkafkaproducersample.domain.model;

import java.math.BigDecimal;

public record Product(Long id, String name, BigDecimal price) {
}
