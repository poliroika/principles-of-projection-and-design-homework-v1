# Homework #1: Currency rate provider + Rate printer

1. `currency-rate-provider` — сервер, который по HTTP/RPC возвращает текущий курс `USDRUB`.
   Курс специально немного меняется при каждом запросе, чтобы число не было константой.

2. `rate-printer` — клиент, который раз в 5 секунд вызывает `currency-rate-provider`
   и печатает курс в консоль.

## Как запустить

Нужно открыть два терминала.

### Терминал 1 — сервер

```bash
cd currency-rate-provider
mvn spring-boot:run
```

Сервер будет работать на `http://localhost:8080`.

Проверка вручную:

```bash
curl http://localhost:8080/api/rate
```

### Терминал 2 — клиент

```bash
cd rate-printer
mvn spring-boot:run
```

Каждые 5 секунд в консоли будет появляться примерно такое:

```text
USDRUB = 92.37
USDRUB = 91.84
USDRUB = 92.11
```

## API

```http
GET /api/rate
```

Ответ:

```json
{
  "pair": "USDRUB",
  "rate": 92.37
}
```
