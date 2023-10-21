# Мини Социальная Сеть

Это простое RESTful API приложение, реализующее упрощенный функционал мессенджера, созданное с использованием фреймворка Spring Boot.

## Оглавление
- [Регистрация и авторизация](#регистрация-и-авторизация)
- [Профиль пользователя](#профиль-пользователя)
- [Социальная часть](#социальная-часть)
- [Дополнительные функции](#дополнительные-функции)
- [Используемые технологии](#используемые-технологии)
- [Установка и запуск приложения](#установка-и-запуск-приложения)
- [REST API](#rest-api)
- [Автор](#автор)

## Основные функции

### Регистрация и авторизация

- Регистрация новых пользователей с указанием почты, пароля, никнейма, имени и фамилии:
  - Новые пользователи сохраняются в хранилище.
  - Есть проверка на уникальность никнейма и почты.
  - Есть хэширование паролей для безопасного хранения в базе данных.
- Вход в систему с созданием сессии и использованием JWT токенов:
  - Есть поддержка Spring Security.
  - Информация о сессии хранится в JWT токенах и передается в хэдерах.
- Выход из системы:
  - Есть механизм защиты от обхода разлогина - инвалидирование JWT токенов.

### Профиль пользователя

- Обновление данных профиля, такие как никнейм, имя, фамилия, почта, аватар, статус и био.
- Обновление пароля учетной записи.
- Удаление профиля пользователя.

### Социальная часть

- Отправка сообщений другим пользователям по их никнейму с проверкой на существование пользователя.
- Просмотр истории общения с другим пользователем.

### Дополнительные функции

- Возможность добавления других пользователей в друзья и просмотр списка друзей.
- Возможность просматривать друзей другого пользователя и скрывать свой список друзей.

## Используемые технологии
- Java
- Spring Boot
- PostgreSQL
- Spring Security
- JWT токены
- Swagger (для документирования API)

## Установка и запуск приложения

Для запуска приложения вам понадобится Java Development Kit (JDK) версии 8 или выше и среда разработки, поддерживающая Spring Boot, такая как IntelliJ IDEA или Eclipse.

1. Клонируйте репозиторий:
```
git clone https://github.com/katseliv/mini-social-network.git
cd mini-social-network
```
2. Настройте базу данных PostgreSQL и укажите соответствующие параметры в файле `application.yaml`.
3. Соберите и запустите проект:
```
./mvnw spring-boot:run
```
4. Приложение будет доступно по адресу http://localhost:8080.

## REST API
Документацию по REST API можно найти в Swagger UI по адресу http://localhost:8080/swagger-ui/.

### Примеры запросов

#### Регистрация нового пользователя
```http
POST /api/v1/users/register
Content-Type: application/json

{
    "username": "user123",
    "firstName": "John",
    "lastName": "Doe",
    "email": "user@example.com",
    "password": "securePassword",
    "passwordConfirmation": "securePassword"
}
```

#### Вход в систему
```http
POST /api/v1/login
Content-Type: application/json

{
    "email": "user@example.com",
    "password": "securePassword"
}
```

#### Отправка сообщения
```http
POST /api/v1/messages
Content-Type: application/json
Authorization: Bearer <ваш_токен>

{
    "receiverUsername": "friend123",
    "message": "Привет, друг!"
}
```

#### Обновление данных профиля
```http
PUT /api/v1/users
Content-Type: application/json
Authorization: Bearer <ваш_токен>

{
    "username": "newUsername",
    "firstName": "New",
    "lastName": "Name",
    "email": "new@example.com",
    "base64StringAvatar": "newBase64StringAvatar",
    "status": "newStatus",
    "bio": "newBio",
}
```

#### Получение списка друзей другого пользователя
```http
GET /api/v1/friends/view/{id}
Content-Type: application/json
Authorization: Bearer <ваш_токен>

{
    "pageNumber": 0,
    "pageSize": 0,
    "totalCount": 0,
    "totalPages": 0,
    "viewDtoList": [
        {
            "username": "string",
            "firstName": "string",
            "lastName": "string",
            "status": "string",
        }
    ]
}
```

## Автор

Автор этого приложения - [Екатерина Селиванова](https://github.com/katseliv).

