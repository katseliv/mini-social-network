# Мини Социальная Сеть

Это простое RESTful API приложение, реализующее упрощенный функционал мессенджера, созданное с использованием фреймворка Spring Boot.

## Оглавление
- Регистрация и авторизация
- Профиль пользователя
- Социальная часть
- Дополнительные возможности
- Используемые технологии
- Установка и запуск
- Примеры REST-запросов
- Лицензия

## Основные функции

### Регистрация и авторизация

- Регистрация новых пользователей с указанием почты, пароля, никнейма, имени и фамилии.
- Хэширование паролей для безопасного хранения в базе данных.
- Вход в систему с созданием сессии и использованием JWT токенов.

### Профиль пользователя

- Обновление данных профиля, включая никнейм, имя, фамилию и email.
- Обновление пароля учетной записи.

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
git clone https://github.com/yourusername/messenger-api.git
cd messenger-api
```
2. Настройте базу данных PostgreSQL и укажите соответствующие параметры в файле `application.yaml`.
3. Соберите и запустите проект:
```
./mvnw spring-boot:run
```
4. Приложение будет доступно по адресу http://localhost:8080.

## REST API
Документацию по REST API можно найти в Swagger UI по адресу `http://localhost:8080/swagger-ui.html`.

### Примеры запросов
Регистрация нового пользователя
http
Copy code
POST /api/register
Content-Type: application/json

{
    "email": "user@example.com",
    "password": "securePassword",
    "nickname": "user123",
    "firstName": "John",
    "lastName": "Doe"
}
Вход в систему
http
Copy code
POST /api/login
Content-Type: application/json

{
    "email": "user@example.com",
    "password": "securePassword"
}

Отправка сообщения
http
Copy code
POST /api/messages/send
Content-Type: application/json
Authorization: Bearer <ваш_токен>

{
    "recipientNickname": "friend123",
    "message": "Привет, друг!"
}
Обновление данных профиля
http
Copy code
PUT /api/profile/update
Content-Type: application/json
Authorization: Bearer <ваш_токен>

{
    "nickname": "newNickname",
    "firstName": "New",
    "lastName": "Name",
    "email": "new@example.com"
}
Автор
Ваше имя

Лицензия
Этот проект лицензируется в соответствии с лицензией XYZ. Подробности см. в файле LICENSE.

Copy code

Помимо этого примера, не забудьте документировать ваши API-эндпоинты и предоставить приме
