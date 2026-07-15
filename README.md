# Order API

Проект на Spring Boot 3.4.2 с использованием Java 21, PostgreSQL, Kafka, Mailpit, Flyway, OpenAPI, Actuator и паттерна outbox.

## Стек

- Java 21
- Spring Boot 3.4.2
- Spring Web MVC
- Spring Data JPA
- Spring Validation
- PostgreSQL 17
- Apache Kafka 4.1.0
- Spring Mail
- Mailpit
- Flyway
- Springdoc OpenAPI
- Actuator
- Lombok

## Архитектура

Проект организован по слоям:

- `domain`: основные бизнес-модели и перечисления (enums).
- `application`: порты ввода/вывода, use cases (сценарии использования), команды и бизнес-исключения.
- `adapter.in.web`: REST-контроллеры, валидация запросов, маппинг ответов и обработка исключений с помощью `ProblemDetail`.
- `adapter.in.messaging`: Kafka consumer для событий создания заказа.
- `adapter.out.persistence`: JPA-сущности, репозитории Spring Data, адаптеры, фикстуры и маппинг.
- `adapter.out.messaging`: публикация в Kafka, отправка email по SMTP и поллинг (polling) outbox.
- `config`: конфигурация инфраструктуры.

## Поток событий (Event flow)

Приложение выполняет два асинхронных этапа поверх одного и того же механизма outbox:

1. `POST /api/orders` сохраняет заказ и outbox-событие `ORDER_CREATED` в рамках одной транзакции.
2. `OutboxPublisher` публикует это событие в Kafka.
3. `OrderCreatedInvoiceConsumer` читает (consumes) сообщение из Kafka и создает email со счетом (invoice), а также второе outbox-событие `EMAIL_INVOICE_REQUESTED`.
4. `OutboxPublisher` отправляет email-событие через SMTP и помечает заказ как `INVOICE_DELIVERED`.

## Запуск в Docker

1. Запустите все сервисы:
```bash
docker-compose up --build
```

2. Основные инструменты и UI:
- **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **pgAdmin (БД)**: [http://localhost:5050](http://localhost:5050)
  - *Логин*: `admin@admin.com`
  - *Пароль*: `admin`
- **Kafka UI**: [http://localhost:8081](http://localhost:8081)
- **Mailpit**: [http://localhost:8025](http://localhost:8025)

### Как подключить БД в pgAdmin:
После входа в [pgAdmin](http://localhost:5050):
1. Нажмите **Add New Server**.
2. Вкладка **General**: Name = `OrderDB`.
3. Вкладка **Connection**:
   - **Host name/address**: `postgres` (имя сервиса в Docker)
   - **Port**: `5432`
   - **Maintenance database**: `orders_db`
   - **Username**: `postgres`
   - **Password**: `postgres`
4. Нажмите **Save**.

## Переменные окружения (.env)

```env
DB_URL=jdbc:postgresql://localhost:5432/orders_db
DB_USERNAME=postgres
DB_PASSWORD=postgres
KAFKA_BOOTSTRAP_SERVERS=localhost:9092
KAFKA_ORDER_TOPIC=orders.created
OUTBOX_BATCH_SIZE=20
OUTBOX_FIXED_DELAY_MS=3000
```

## Основные эндпоинты API

- `GET /api/users`
- `GET /api/products`
- `GET /api/orders`
- `POST /api/orders`
- `GET /api/outbox-events`

### Пример запроса на создание заказа
```json
{
  "userId": 1,
  "items": [
    {
      "productId": 1,
      "quantity": 2
    }
  ]
}
```
