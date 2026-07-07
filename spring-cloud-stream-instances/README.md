# spring cloud stream instances

This project presents an examples of the queue streams realisation grouped by modules. Each module presents the small simple queue stream instance of the `spring-boot` service.

[![status](https://img.shields.io/badge/status-active-active?style=flat-square)](BADGES_GUIDE.md#status) [![last commit](https://img.shields.io/badge/last_commit-August_12,_2020-informational?style=flat-square)](BADGES_GUIDE.md#commit-date)

[![license](https://img.shields.io/badge/license-MIT-informational?style=flat-square)](LICENSE)

---

## 📇 Table of Contents

- [About](#about)
- [Features](#feature)
- [Getting Started](#getting-started)
- [Built With](#built-with)
- [Authors](#authors)
- [Licensing](#licensing)

##  📖 About

This is a collection of small and focused instances - each covering a single and well defined approach in the area of the java application development grouped by modeules. Each module is supposed to provide a separate simple instance. 
In this project each module presents a small simple instance of the queue stream realisation between a spring-boot restful services.
A strong focus of these is, of course, the `spring-cloud-stream` implementation for different queue services.

### Modules

This project includes the follows modules:

- [number-counter](number-counter/README.md) - this module is a java project with simple instances of the Spring Cloud Stream implementation with Rabbit and Kafka, booth in one service;

## 🎚 Features

- The simple `spring-cloud-strea` implementation for consuming and producing messages on booth RabbitMQ and Kafka. 

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
- **[Docker Compose](https://docs.docker.com/compose/)** - tool for defining and running multi-container `Docker` applications.

If any of the listed software is not installed, then it can be installed by instruction as described below.

1. #### Oracle JDK 8+

   - Install Oracle JDK 8+ according to instructions from an [official](https://www.oracle.com/java/technologies/javase-downloads.html) instruction.

2. #### Maven 3+

   - Install Maven 3+ according to instructions from an [official](https://maven.apache.org/) source.
   
3. #### Docker Compose

   - Install Docker Compose according to instructions from an [official](https://docs.docker.com/compose/install/) source.


### Installing

In order to install it is quite simple to clone or download this repository.

### Cloning

For the cloning this repository to a local machine, just use the follows link:

```http
https://github.com/ololx/spring-cloud-stream-instances
```

### Using

To use it is necessary to:
1 - To deploy the additional software.
2 - To build the project.
3 - To launch the instances.

### Deploy the additional software

1 - To do the deployment of the additional software, execute docker compose in the root directory by the following command:

```bash
docker-compose up
```

2 - Wait for all software deployment to complete.

### Building the project

To do the full build, execute maven goal `install` in the root directory by the following command:

```bash
mvn clean install
```

### Building a single module

To do the full build, execute maven goal `install` in the module directory by the following command:

```bash
mvn clean install
```

### Launching in command line

To do the  examples, execute maven goal `spring-boot:run` in the module directory by the following command:

```bash
mvn spring-boot:run
```

### Launching in IDE

This is a multi-module project. Each model is supposed to provide a separate example. 
That's why when  you're working with an individual module, there's no need to import all of them (or build all of them) - you can simply import that particular module in either Eclipse, NetBeanse or IntelliJ IDEA and run each example  in IDE.

## 🛠 Built With

- **[Oracle JDK](https://www.oracle.com/java/technologies/javase-downloads.html)** -  java development kit;
- **[Maven](https://maven.apache.org/)** - dependency management;
- **[RabbitMQ](https://www.cloudamqp.com/?utm_source=google&utm_medium=cpc&utm_term=rabbit%20message%20queue&utm_campaign=general&gclid=CjwKCAjwps75BRAcEiwAEiACMeDjZjyvvqbEwtBlAjbt41zWdXCjui-x5M7y_m3yHQrM8ielZWvWqhoCI_8QAvD_BwE)** - database management system;
- **[Docker Compose](https://docs.docker.com/compose/)** - tool for defining and running multi-container `Docker` applications.

## ©️ Authors

* **Alexander A. Kropotin** - *project work* - [ololx](https://github.com/ololx).

## 🔏 Licensing

This project is licensed under the MIT license - see the [lisence](LICENSE) document for details.
