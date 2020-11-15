# Coding Assessment - Engineering Intern Program 2021 @ Zendesk

TicketViewerApp

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

1.Clone the repo

https://github.com/Morpheush333/ticketViewer/tree/master

2. Change password and email for request to Zendesc Api:

application.properties 

(by default my test Zendesc account)


```
spring.datasource.address = https://mateuszmedon.zendesk.com/api/v2/tickets.json
spring.datasource.username = mateuszmedon1@gmail.com
spring.datasource.password = testpassword
```

### Installing

Run Spring-boot application

Enter the url in your browser
```
http://localhost:8080/ticketViewer/tickets
```

## Running the tests

com.mateuszmedon.ticketViewer.TicketViewerApplicationTests


## Built With

*  [Spring Boot](https://spring.io/) - Project
* [Maven](https://maven.apache.org/) - Dependency Management


## Author

* **Mateusz Medon** - *Initial work* - [portfolio](http://mateuszmedon.com/)


## Acknowledgments

* Zendesk
* Inspiration