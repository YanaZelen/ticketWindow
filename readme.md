## Требования

- Docker
- Docker Compose

## Установка

1. Клонируйте репозиторий:

   ```bash
   git clone https://github.com/yourusername/your-repo.git
   cd your-repo

2. Создайте файл .env в корневой директории проекта и добавьте следующие параметры:

POSTGRES_DB=
POSTGRES_USER=
POSTGRES_PASSWORD=

Пример .env файла:

POSTGRES_DB=mydatabase
POSTGRES_USER=myuser
POSTGRES_PASSWORD=mypassword

3. Запустите Docker Compose:

docker-compose --env-file .env up --build