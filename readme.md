Тестовый проект

## Требования

- Docker
- Docker Compose

## Установка

1. Клонируйте репозиторий:

   ```bash
   git clone https://github.com/YanaZelen/ticketWindow.git
   cd ticketWindow

2. Запустите Docker Compose:
   
   docker-compose up (в Docker соберуться Kafka и PosgreSQl)

3. Соберите и запустите приложение:

   mvn clean install
   java -jar target/stm-1.0-SNAPSHOT.jar

//////////////////////////////////////////
   Неактуально
   docker build -f app.Dockerfile -t app .
   docker run -p 8080:8080 app