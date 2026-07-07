# orm-patterns-instances

This project presents a several examples of the database layer realization of a `RESTful` service. Each module presents a small simple instance of the different `ORM Pattern` realizations.

[![status](https://img.shields.io/badge/status-active-active?style=flat-square)](BADGES_GUIDE.md#status) [![last commit](https://img.shields.io/badge/last_commit-May_12,_2021-informational?style=flat-square)](BADGES_GUIDE.md#commit-date)

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

This is a collection of small and focused instances - each covering a single and well defined approach in the area of the java application development grouped by modules. Each module is supposed to provide a separate simple instance. 
In this project each module presents a small simple instance of the database layer realization of a `RESTful` service.
A strong focus of these is, of course, the different `ORM Pattern` realizations.

### Modules

This project includes the follows modules:

- [active-jdbc](active-jdbc/README.md) - is a simple realization of the `Active Records Pattern` via `ActiveJDBC` implementation in the `spring-boot` application;
- [hibernate-jpa](hibernate-jpa/README.md) - is a simple realization of the `Data Mappting Pattern` via `Spring Data JPA` and `Hibernate` implementation in the `spring-boot` application.
- [hibernate-jdbc-template](hibernate-jdbc-template/README.md) - is a simple realization of the `Data Mappting Pattern` via `JdbcTemplate` and `Hibernate` implementation in the `spring-boot` application.

## 🎚 Features

- The simple examples of the different `ORM Pattern` realizations.

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

   - Install PostgreSQL 9+ according to instructions from an [official](https://docs.docker.com/compose/install/) source.


### Installing

In order to install it is quite simple to clone or download this repository.

### Cloning

For the cloning this repository to a local machine, just use the follows link:

```http
https://github.com/ololx/orm-patterns-instances
```

### Using

To use it is necessary to:
1 - Build the project.
2 - Launch the instances.
3 - Instrument if (for the `active-jdbc` module).

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

To do the run examples, execute maven goal `spring-boot:run` in the module directory by the following command:

```bash
mvn spring-boot:run
```

### Launching in IDE

This is a multi-module project. Each model is supposed to provide a separate example. 
That's why when  you're working with an individual module, there's no need to import all of them (or build all of them) - you can simply import that particular module in either Eclipse, NetBeanse or IntelliJ IDEA and run each example  in IDE.

### Instrumenting

The module `active-jdbc` must be instrumented before launching via `maven instrumentation plugin`

## 🛠 Built With

- **[Oracle JDK](https://www.oracle.com/java/technologies/javase-downloads.html)** -  java development kit;
- **[Maven](https://maven.apache.org/)** - dependency management;
- **[PostgreSQL 9+](https://www.postgresql.org/download/)** - database management system;
- **[Docker Compose](https://docs.docker.com/compose/)** - tool for defining and running multi-container `Docker` applications.

## ©️ Authors

* **Alexander A. Kropotin** - *project work* - [ololx](https://github.com/ololx).

## 🔏 Licensing

This project is licensed under the MIT license - see the [lisence](LICENSE) document for details.
