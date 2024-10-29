# Student Management API

## Описание

**Student Management API** — это REST-сервис для получения информации о студентах, с поддержкой аутентификации через OAuth 2.0. API позволяет создавать пользователей, получать токен доступа и использовать его для дальнейших запросов.

## Минимальные требования

- **Java**: openjdk 17
- **MongoDB**: 4.11.1
- **Spring Boot**: 3.2.2
- **OAuth 2.0 resource server**: 6.2.1
- **Docker**: 20.10.21
- **Docker Compose**: v2.12.2

## Порты

- Приложение: `8080:8080`
- MongoDB: `27017:27017`

## Настройка конфигурации

Проект использует два профиля:

1. **Для локального запуска**:
   - MongoDB разворачивается в Docker.
   - Файл конфигурации `src/main/resources/application.properties`

2. **Для полного развертывания в Docker**:
   - Файл docker-compose.yml:
     ```environment:
     - SPRING_DATA_MONGODB_URI=mongodb://root:rootpass@mongo:27017/studentdb?authSource=admin```

## Запуск приложения

### Локальный запуск

1. Запустите MongoDB в Docker:
   ```bash
   docker run --name studentdb -d -p 27017:27017 -e MONGO_INITDB_ROOT_USERNAME=root -e MONGO_INITDB_ROOT_PASSWORD=rootpass mongo```
2. Запустите приложение:
   ```mvn spring-boot:run```

### Запуск в Docker

Для запуска всего приложения в Docker:
```docker-compose up --build```

## Использование API

### 1. Создание пользователя

Для создания пользователя отправьте POST-запрос на `/auth/create`:

```bash
curl -X POST http://localhost:8080/auth/create \
  -H "Content-Type: application/json" \
  -d '{
        "username": "user1",
        "password": "password123"
      }' 
```

Ответ:

```{
  "message": "Пользователь успешно создан.",
  "data": {
    "username": "user1"
  }
}
```

### 2. Получение токена доступа

Для получения токена отправьте POST-запрос на /auth/get_token, указав логин и пароль:
```bash
curl -X POST http://localhost:8080/auth/get_token \
  -H "Content-Type: application/json" \
  -d '{
        "username": "user1",
        "password": "password123"
      }' 
```

Ответ:

```{
  "message": "Токен доступа получен успешно.",
  "data": "Bearer your_access_token_here"
```

Сохраните токен, он понадобится для аутентификации в следующих запросах.

### 3. Запросы к защищенным ресурсам

Для выполнения запросов к защищенным ресурсам передавайте токен в заголовке Authorization. Пример GET-запроса к /students:

```bash
curl -X GET http://localhost:8080/students \
  -H "Authorization: Bearer your_access_token_here" 
```

Примечание: Замените your_access_token_here на токен, полученный на предыдущем шаге.

#### В API присутствуют unit-тесты для проверки контроллеров, запустить можно с помощью команды:
```
mvn test
```
