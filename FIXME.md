## Ошибки при работе с Visit Controller

>Кейс 1
> 
Что делаю:
- Дергаю метод curl -X POST "http://localhost:8080/owners/1/pets/1/visits" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"date\": \"2022-06-26\", \"description\": \"string\", \"id\": 0}"

Что полуйчаю:
- Expected status code <201> but was <500>.
- БД не изменилась

Что ожидаю:
- Код ответа от сервера 201
- Добавление в таблицу ин-ции согласно запросу


>Кейс 2

Что делаю:
- Дергаю метод curl -X GET "http://localhost:8080/owners/19/pets/28/visits" -H "accept: */*"

Что полуйчаю:
- Expected status code <200> but was <500>.
- БД не изменилась

Что ожидаю:
- Код ответа от сервера 200
- В теле ответа данные по запросу


## Ошибки при работе с Pet Controller
>Кейс 3

Что делаю:
- Дергаю метод curl -X POST "http://localhost:8080/owners/1/pets" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"birthDate\": \"2022-06-26\", \"id\": 101, \"name\": \"Leo0\", \"visits\": [ { \"date\": \"2022-06-27\", \"description\": \"string\", \"id\": 0 } ]}"

Что полуйчаю:
- Сущность в БД добавлена
- метод не вернул тело ответа

Что ожидаю:
- Код ответа от сервера 201
- В теле ответа данные, добавленные по запросу


## Ошибки при работе с Vet Controller
>Кейс 4:

Что делаю:
- Дергаю метод curl -X GET "http://localhost:8080/vets" -H "accept: */*"
- 
Что полуйчаю:
- Метод возвращает первые 6 записей

Что ожидаю:
- метод возвращает все записи 

## Ошибки при работе с Owner Controller

>Кейс 5:

Что делаю:
- Дергаю метод curl -X GET "http://localhost:8080/owners/" -H "accept: */*" без айди 

Что полуйчаю:
- Метод возвращает 200

Что ожидаю:
- метод возвращает 400 BadRequest

>Кейс 6:
>
Метод POST/owners/{ownerId} должен быть PATH/PUT ??? т к меняет существующую запись 

