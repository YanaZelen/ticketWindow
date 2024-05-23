Тестовый проект

## Требования

- Docker
- Docker Compose

## Установка

1. Клонируйте репозиторий:

   ```bash
   git clone https://github.com/YanaZelen/ticketWindow.git
   cd ticketWindow

2. Создайте файл .env в корневой директории проекта и добавьте следующие параметры:

POSTGRES_DB=
POSTGRES_USER=
POSTGRES_PASSWORD=

Пример .env файла:

POSTGRES_DB=mydatabase
POSTGRES_USER=myuser
POSTGRES_PASSWORD=mypassword

3. Загрузите Maven Wrapper и запустите Docker Compose:

mvn -N io.takari:maven:wrapper

docker-compose --env-file .env up --build

4. Документация Swagger

Документация доступна по ссылке http://localhost:8080/swagger-ui.html после запуска приложения