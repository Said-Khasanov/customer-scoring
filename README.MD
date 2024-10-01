# О сервисе

**customer-scoring** - REST-сервис, который реализует задачу оценки клиента с
использованием дерева принятия решений (формат – DMN).

**Технологии:** Java, Spring Boot, BPM Camunda, PostgreSQL, Docker

# Запуск

Для запуска необходимо клонировать репозиторий

`git clone https://github.com/Said-Khasanov/customer-scoring`

Выполнить команды

`cd customer-scoring`

`mvn clean install`

`docker compose up -d`

Или запустить скрипт **run.sh** (run.bat для Windows) 


# Endpoint для оценки
http://localhost:8080/api/v1/scoring

Пример тела POST запроса

`{
"inn": "1234567890",
"region": 77,
"capital": 6000000
}`