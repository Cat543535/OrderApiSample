# Order API

Проект на Spring Boot 4.0.4 с использованием Java 25, PostgreSQL, Kafka, Mailpit, Flyway, OpenAPI, Actuator и паттерна outbox.

## Стек

- Java 25
- Spring Boot 4.0.4
- Spring Web MVC
- Spring Data JPA
- Spring Validation
- PostgreSQL
- Apache Kafka
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

Это позволяет сохранять независимость записи в базу данных, публикации в Kafka и отправки email, сохраняя при этом состояние с возможностью повторных попыток в таблице `outbox_events`.

## Процесс запуска 

1. Запуск PostgreSQL, Kafka, Kafka UI и Mailpit:

```bash
docker compose up --build
```


3. Основные эндпоинты:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI docs: `http://localhost:8080/v3/api-docs`
- Health: `http://localhost:8080/actuator/health`
- Kafka UI: `http://localhost:8081`
- Mailpit UI: `http://localhost:8025`

## Переменные окружения

Приложение автоматически импортирует файл `.env`.

```env
DB_URL=jdbc:postgresql://localhost:5432/orders_db
DB_USERNAME=postgres
DB_PASSWORD=postgres
KAFKA_BOOTSTRAP_SERVERS=localhost:9092
KAFKA_ORDER_TOPIC=orders.created
KAFKA_INVOICE_CONSUMER_GROUP_ID=invoice-email-consumer
MAIL_HOST=localhost
MAIL_PORT=1025
MAIL_SMTP_AUTH=false
MAIL_SMTP_STARTTLS=false
APP_MAIL_FROM=billing@example.com
OUTBOX_BATCH_SIZE=20
OUTBOX_FIXED_DELAY_MS=3000
OUTBOX_MAX_ATTEMPTS=5
```

Дополнительные переключатели (runtime switches):

- `app.kafka.invoice-consumer-enabled=true`
- `app.outbox.publisher.enabled=true`

## Схема базы данных

Миграции Flyway создают:

- таблицы каталога и заказов
- `outbox_events` с метаданными для повторных попыток (`attempt_count`, status, error message, timestamps)
- `invoice_emails` для сгенерированных email-сообщений (payloads) и отслеживания доставки

## Данные по умолчанию (Fixtures)

При запуске приложение вставляет данные по умолчанию только если целевые таблицы пусты:

- User: `Default User / default.user@example.com`
- Products: `Notebook`, `Keyboard`, `Mouse`

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
    },
    {
      "productId": 2,
      "quantity": 1
    }
  ]
}
```

Эндпоинт заказа является асинхронным и возвращает `202 Accepted`. После создания заказа есть возможность проверить:

- сообщения Kafka в Kafka UI
- ожидающие и опубликованные события по адресу `GET /api/outbox-events`
- доставленные email в Mailpit
- переходы статусов заказа с помощью `GET /api/orders`

## Локальный мониторинг (Observability)

Kafka UI доступен по адресу `http://localhost:8081`.

Используется для проверки:

- топиков, таких как `orders.created`
- отправленных сообщений и их полезной нагрузки (payloads)
- consumer-групп

Mailpit доступен по адресу `http://localhost:8025`.

Используется для проверки:

- перехваченных email, отправленных через SMTP-адаптер
- темы/тела письма со счетом, сгенерированных на основе прочитанных событий заказа

Если что-то работает нестабильно:
```bash
docker compose down
docker compose up -d
```