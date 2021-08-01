# spring-security-instances

This projects presents instances of the `Spring Security` technology implementation.

[![status](https://img.shields.io/badge/status-active-active?style=flat-square)](BADGES_GUIDE.md#status) [![last commit](https://img.shields.io/badge/last_commit-August_01,_2021-informational?style=flat-square)](BADGES_GUIDE.md#commit-date)

[![license](https://img.shields.io/badge/license-UNLICENCE-informational?style=flat-square)](LICENSE)

---

[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)](#built-with) [![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](#built-with) [![Swagger](https://img.shields.io/badge/Swagger-2C3239?style=for-the-badge&logo=swagger&labelColor=2C3239)](#built-with)

[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)](#built-with) 

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
In this project each module presents a small simple instance of the `Spring Security` technology implementation. A strong focus of these instances is the different cases of the `Spring Security` realizations.

### Services

- **basic-authentication** - this service presents a simple instance of the `Spring Security Basic Authentication` technology implementation.

## Demo

This GIF demonastrates the **basic-authentication** instance service.

<img src="https://github.com/ololx/spring-security-instances/blob/assets/spring-security-instances-demo-1.gif?raw=true" width="800"/>

## 🎚 Features

The project with instances of the `Spring Security` technology implementation.

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
https://github.com/ololx/spring-security-instances
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

To do the sending requests to some instance service, just open specifications by address:

```http
http://localhost:8080/swagger-ui.html
```

## 🛠 Built With

- **JDK** - the  java development kit;
- **Maven** - the dependency management;
- **PostgreSQL** - the database management system;
- **Liquibase** - the database migration system.
- **Swagger** - documentation and form generator;
- **Docker Compose** - tool for defining and running multi-container `Docker` applications.

## ©️ Authors

* **Alexander A. Kropotin** - *initial work* - [ololx](https://github.com/ololx).

## 🔏 Licensing

This project is unlicensed - see the [lisence](LICENSE) document for details.
