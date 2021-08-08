# spring-boot-distributed-tracing-instances
This projects presents instances of the micro-services distributed tracing realization via Spring Boot and Open Tracing technologies implementation.

[![status](https://img.shields.io/badge/status-active-active?style=flat-square)](BADGES_GUIDE.md#status) [![last commit](https://img.shields.io/badge/last_commit-August_08,_2021-informational?style=flat-square)](BADGES_GUIDE.md#commit-date)

[![license](https://img.shields.io/badge/license-UNLICENCE-informational?style=flat-square)](LICENSE)

---

[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)](#built-with) [![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](#built-with) [![Swagger](https://img.shields.io/badge/Swagger-2C3239?style=for-the-badge&logo=swagger&labelColor=2C3239)](#built-with)

[![Jaeger](https://img.shields.io/badge/Jaeger-F2F4F9?style=for-the-badge&labelColor=F2F4F9&logo=data:image/png;base64,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)](#built-with)

[![Docker](https://img.shields.io/badge/Docker-2CA5E0?style=for-the-badge&logo=docker&logoColor=white)](#built-with)

## 📇 Table of Contents

- [About](#about)
- [Demo](#demo)
- [Features](#feature)
- [Getting Started](#getting-started)
- [Built With](#built-with)
- [Authors](#authors)
- [Licensing](#licensing)

##  📖 About
This is a collection of small and focused instances, each of which covers a single and defined approach to Java application development and an required technologies implementation.
In this project each module presents a small simple instance of the `Open Tracing Contrib` technology implementation. A strong focus of these instances is the different cases of the `micro-services distributed tracing` realizations.

## 🎚 Features

The project with instances of the `micro-services distributed tracing` technology implementation.

### To Do

- For more information on an upcoming development, please read the [todo](TODO.md) list.

### Changelog

- For more information on a releases, a features and a changes, please read the [changelog](CHANGELOG.md) notes.

## 🚦 Getting Started

These instructions allow to get a copy of this project and run it on a local machine.

### Prerequisites

Before using it, make sure that follows software are installed on the local machine:

- **Docker Compose** - tool for defining and running multi-container `Docker` applications.

If any of the listed software is not installed, then it can be installed by instruction as described below.

#### Docker Compose

   - Install Docker Compose according to instructions from an [official](https://docs.docker.com/compose/install/) source.

### Installing

In order to install it is quite simple to clone or download this repository.

### Cloning

For the cloning this repository to a local machine, just use the follows link:

```http
https://github.com/ololx/spring-boot-distributed-tracing-instances
```

### Using

To run and try out each instance is required to: 
1. Launch containers with services; 
2. Send requests to instance service.

### Launching

To do the launching some incstance, go to the instance directory and execute the following command:

```bash
docker-compose up --build
```

### Sending request 

To do the sending requests to some instance service, just open specifications by addresses:

- Service A
```http
http://localhost:8182/swagger-ui.html
```

- Service B
```http
http://localhost:8181/swagger-ui.html
```

## 🛠 Built With

- **JDK** - the  java development kit;
- **Maven** - the dependency management;
- **H2 Database Engine** - the database management system;
- **Swagger** - documentation and form generator;
- **Docker Compose** - tool for defining and running multi-container `Docker` applications.

## ©️ Authors

* **Alexander A. Kropotin** - *initial work* - [ololx](https://github.com/ololx).

## 🔏 Licensing

This project is unlicensed - see the [lisence](LICENSE) document for details.