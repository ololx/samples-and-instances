# spring-boot-custom-message-converting-instances
This is a simple realization of custom messages converting in spring-boot HTTP requests and responses.

[![last commit](https://img.shields.io/badge/last_commit-December_05,_2021-informational?style=flat-square)](BADGES_GUIDE.md#commit-date)

[![license](https://img.shields.io/badge/license-UNLICENCE-informational?style=flat-square)](LICENSE)

---

[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)](#built-with) [![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](#built-with) [![Swagger](https://img.shields.io/badge/Swagger-2C3239?style=for-the-badge&logo=swagger&labelColor=2C3239)](#built-with)

## 📇 Table of Contents

- [About](#about)
- [Features](#feature)
- [Getting Started](#getting-started)
- [Built With](#built-with)
- [Authors](#authors)
- [Licensing](#licensing)

##  📖 About
This is a collection of small and focused instances, each of which covers a single and defined approach to Java application development and an required technologies implementation.
This project provides an example of the implementation of centralized message converting of HTTP requests/responses. In this case, the API accepts a DTO with attributes in the Cyrillic alphabet. But the API must and can accept a JSON object with attributes in the Latin alphabet. This example implements a custom incoming/outgoing message converting that simply translates the field names from Latin to Cyrillic and vice versa.

## 🎚 Features

The project with instances of the custom message converting realization.

### To Do

- For more information on an upcoming development, please read the [todo](TODO.md) list.

### Changelog

- For more information on a releases, a features and a changes, please read the [changelog](CHANGELOG.md) notes.

## 🚦 Getting Started

These instructions allow to get a copy of this project and run it on a local machine.

### Prerequisites

Before using it, make sure that follows software are installed on the local machine:

- **[Oracle JDK 8+](https://www.oracle.com/java/technologies/javase-downloads.html)** -  java development kit;
- **[Maven 3+](https://maven.apache.org/)** - dependency management;

If any of the listed software is not installed, then it can be installed by instruction as described below.

1. #### Oracle JDK 11+

   - Install Oracle JDK 11+ according to instructions from an [official](https://www.oracle.com/java/technologies/javase-downloads.html) instruction.

2. #### Maven 3+

   - Install Maven 3+ according to instructions from an [official](https://maven.apache.org/) source.

### Installing

In order to install it is quite simple to clone or download this repository.

### Cloning

For the cloning this repository to a local machine, just use the follows link:

```http
https://github.com/innopolis-university-java-team/spring-boot-custom-message-converting-instances.git
```

### Using

To use it is necessary to:

1 - Build the project
2 - Launch the instances
3 - Send http request

### Building

To do the full build, execute maven goal `package` in the project directory by the following command:

```bash
mvn clean package
```

### Launching in command line

To do the run example, execute maven goal `spring-boot:run` in the project directory by the following command:

```bash
mvn spring-boot:run
```

### Launching in IDE

You can simply import this project in either Eclipse, NetBeanse or IntelliJ IDEA and run it in IDE.

### Sending http request

To do the sending requests to the service, just open specifications by address and try it:

```http
http://localhost:8080/swagger-ui.html
```

This service contains one controller - `Request Repeater Controller`. This controller contains four endpoints, such as:
1. `fizz-buzz/translated` - allows to send the JSON object with attributes named in both Cyrillic or latin. In this case, the custom message converter will translate request attributes into Cyrillic names.  For the evaluating this trick just send one of follows requests and see an APIs logs: 

1.1.1.  Request:
```sh
curl -X POST "http://localhost:8080/fizz-buzz/translated" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"buzz\": \"1\", \"fizz\": \"2\"}"
```
1.1.2.  Response body:
```json
{
  "fizz": "2",
  "buzz": "1"
}
```
1.1.3.  Logs:
```sh
21:17:55.000 INFO JSONTMessageConverter : Convert input message into map - {buzz=1, fizz=2}
21:17:55.005 INFO JSONTMessageConverter : Transliterate input message map from EN to RU - {физз=2, бузз=1}
21:17:55.041 INFO JSONTMessageConverter : Convert transliterated input message map into object - TranslatedFizzBuzz(fizz=2, buzz=1)
21:17:55.049 INFO RequestRepeaterController : Получили запрос с транслированными полями ФиззБузз - TranslatedFizzBuzz(fizz=2, buzz=1)
21:17:55.060 INFO JSONTMessageConverter : Convert output message into map - {физз=2, бузз=1}
21:17:55.062 INFO JSONTMessageConverter : Transliterate output message map from EN to RU - {fizz=2, buzz=1}
21:17:55.066 INFO JSONTMessageConverter : Convert transliterated output message map into object - {"fizz":"2","buzz":"1"}
```

1.2.1.  Request:
```sh
curl -X POST "http://localhost:8080/fizz-buzz/translated" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"бузз\": \"1\", \"физз\": \"2\"}"
```
1.1.2.  Response body:
```json
{
  "fizz": "2",
  "buzz": "1"
}
```
1.1.3.  Logs:
```sh
21:21:21.176 INFO JSONTMessageConverter : Convert input message into map - {бузз=1, физз=2}
21:21:21.177 INFO JSONTMessageConverter : Transliterate input message map from EN to RU - {физз=2, бузз=1}
21:21:21.178 INFO JSONTMessageConverter : Convert transliterated input message map into object - TranslatedFizzBuzz(fizz=2, buzz=1)
21:21:21.178 INFO RequestRepeaterController : Получили запрос с транслированными полями ФиззБузз - TranslatedFizzBuzz(fizz=2, buzz=1)
21:21:21.179 INFO JSONTMessageConverter : Convert output message into map - {физз=2, бузз=1}
21:21:21.180 INFO JSONTMessageConverter : Transliterate output message map from EN to RU - {fizz=2, buzz=1}
21:21:21.180 INFO JSONTMessageConverter : Convert transliterated output message map into object - {"fizz":"2","buzz":"1"}
```

2. `fizz-buzz/origin` - allows to send the JSON object with attributes named in only latin. In this case, the custom message converter willn't be used. For the evaluating this trick just send one of follows requests and see an APIs logs: 

2.1.1.  Request:
```sh
curl -X POST "http://localhost:8080/fizz-buzz/origin" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"buzz\": \"1\", \"fizz\": \"2\"}"
```
2.1.2.  Response body:
```json
{
  "физз": null,
  "бузз": null
}
```
2..1.3.  Logs:
```sh
21:22:54.925 INFO RequestRepeaterController : Получили запрос с оригинальным FizzBuzz - OriginFizzBuzz(fizz=null, buzz=null)
```

***BATASH =)**

2.2.1.  Request:
```sh
curl -X POST "http://localhost:8080/fizz-buzz/origin" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"бузз\": \"1\", \"физз\": \"2\"}"
```
2.1.2.  Response body:
```json
{
  "физз": "2",
  "бузз": "1"
}
```
2.1.3.  Logs:
```sh
21:23:49.972 INFO RequestRepeaterController : Получили запрос с оригинальным FizzBuzz - OriginFizzBuzz(fizz=2, buzz=1)
```

As you can see, the use of such an approach (use custom message converting - the 1 case `translated`) allows centralized processing of requests to the API and responses from the API.

## 🛠 Built With

- **JDK** - the  java development kit;
- **Maven** - the dependency management;
- **Swagger** - documentation and form generator.

## ©️ Authors

* **Alexander A. Kropotin** - *initial work* - [ololx](https://github.com/ololx).

## 🔏 Licensing

This project is unlicensed - see the [lisence](LICENSE) document for details.