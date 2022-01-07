Проект представляет сервис получание gif в зависимости от отношения сегодняшнего курса выбранной валюты к вчерашнему. 
В качестве рализации клиента используется Feign, с помощью которого происходит обращения к соответствующим API:
1.https://openexchangerates.org/api/latest.json - endpoin актального курса 
2.https://openexchangerates.org/api/historical -  endpoin курса с указанной датой
3.https://api.giphy.com/v1/gifs/random?tag=broke - endpoin gif с тегом broke 
4.https://api.giphy.com/v1/gifs/random?tag=rih - endpoint gif с тегом rich
Порт 9090 
Тесты на сервис определены в классе FeignControllerTest




Запуск 
1.docker build --tag=service:latest .
2.docker run -p9090:9090 service:latest
3.http:\\localhost:9090

