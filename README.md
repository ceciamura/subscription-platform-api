# Subscription API

Backend REST API built with Spring Boot and PostgreSQL.

## Technologies

- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Docker Compose
- Swagger / OpenAPI
- JUnit
- Mockito

---

## Features

- Create subscription
- Get all subscriptions
- Get subscription by ID
- Update subscription
- Delete subscription
- Validation handling
- Global exception handling

---

## Run PostgreSQL with Docker

```bash
docker compose up -d
```

## Run PostgreSQL with Docker
```bash
mvn spring-boot:run
```
or 

Run DemoApplication from IntelliJ

## Swagger URL
http://localhost:8080/swagger-ui/index.html

## Endpoints

| Method | Endpoint            | Description            |
| ------ | ------------------- | ---------------------- |
| POST   | /subscriptions      | Create subscription    |
| GET    | /subscriptions      | Get all subscriptions  |
| GET    | /subscriptions/{id} | Get subscription by id |
| PUT    | /subscriptions/{id} | Update subscription    |
| DELETE | /subscriptions/{id} | Delete subscription    |


