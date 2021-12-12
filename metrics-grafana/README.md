# metrics-graphana

This is a custom `Grafana` image with pre-installed datasources (Prometheus, Loki, Zipkin, ...) and dashboards (Spring-Boot metrics, ...).

[![license](https://img.shields.io/badge/license-MIT-informational?style=flat-square)](LICENSE)

---

[![Grafana](https://img.shields.io/badge/Grafana-F2F4F9?style=for-the-badge&logo=grafana&logoColor=orange&labelColor=F2F4F9)](#built-with)

[![Docker](https://img.shields.io/badge/Docker-2CA5E0?style=for-the-badge&logo=docker&logoColor=white)](#built-with)

## 📇 Table of Contents

- [About](#about)
- [Features](#feature)
- [Getting Started](#getting-started)
- [Built With](#built-with)
- [Authors](#authors)
- [Licensing](#licensing)

##  📖 About

This is a custom `Grafana` image with pre-installed `datasources` (Prometheus, Loki, Zipkin, ...) and `dashboards` (Spring-Boot metrics, ...). This image could be simple configured by the editing `provisioning` files. It allows to configure the `dashboards` (add new dasboard or change current) and `datasources` (add new datasource or delete current). And also it allows to change the default admin user name adn password via config file (`grapjana.ini').

## 🎚 Features

The `Docker` image with follows provisionings:

- datasources:
 
  -- Loki -  is required `Grafana Loki` pre installed with defaults url `loki:3100`;
  
  -- Prometheus -  is required `Prometheus` pre installed with defaults url `prometheus:9090`;
  
  -- Zipkin - is required `Zipkin` pre installed with defaults url `zipkin:9411`;
  
- dashboards:
 
  -- Spring-Boot-Statistics - for the `Spring-Boot`services  stats visualization.

### Changelog

- For more information on a releases, a features and a changes, please read the [changelog](CHANGELOG.md) notes.

## 🚦 Getting Started

These instructions allow to get a copy of this project and run it on a local machine.

### Prerequisites

Before using it, make sure that follows software are installed on the local machine:

- **Docker** - tool for defining and running `Docker` applications.

If any of the listed software is not installed, then it can be installed by instruction as described below.

#### Docker

   - Install Docker according to instructions from an [official](https://docs.docker.com/engine/install/) source.

### Installing

In order to install it is quite simple to clone or download this repository.

### Cloning

For the cloning this repository to a local machine, just use one of follows links:

```ssh
git@github.com:innopolis-university-java-team/metrics-graphana.git
```

```http
https://github.com/ololx/spring-boot-metrics-instances
```

### Using

To run and try out it is required to: 

0. [OPTIONAL] change the default admin credentials or modify some dashboards and some datasources. 
  
  - To modify the default admin credentials, just edit the file at the path `./configuration/grafana.ini`.

```ini
[security]

admin_user = iujt
admin_password = 123
```

  - To modify (delete, add or change connection settings) some datasources, just edit the file at the path `./provisionning/datasources/all.yml`.
  
  - To modify (delete, add or change) some dashboards, just edit the file at the path `./provisionning/datasources/all.yml` and put the `*.json` file with dasboard into directory `./provisionning/datasources`.

1. build image

```ssh
docker build -t {name_of_image} .
```

For instance, command for the building this image as  `metrics-graphana`:
```ssh
docker build -t metrics-graphana .
```

2. run container

```ssh
docker run -d -p {port:port_other} {name_of_image} .
```

For instance, command for the running this this image:
```ssh
docker run -d -p 3000:3000 metrics-graphana .
```

3. Open sevices GUI

After successfully launching the service, open the any browser and go to the service endpoint:

```http
http://{service_address}:{port}/
```

For instance, the link for this service on a local machine:

```http
http://localhost:3000/
```

**NOTE:** The default admin login/password is a `iujt/123`.

## 🛠 Built With

- **Graphana** -  visualization and analytics platform;
- **Docker** - tool for defining and running `Docker` applications.

## ©️ Authors

* **Alexander A. Kropotin** - *Initial work* - [ololx](https://github.com/ololx).

## 🔏 Licensing

This project is licensed under the MIT license - see the [lisence](LICENSE) document for details.
