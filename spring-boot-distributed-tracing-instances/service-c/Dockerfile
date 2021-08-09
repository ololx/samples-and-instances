FROM maven:alpine

ARG APP_DIR=app
WORKDIR ${APP_DIR}
COPY .. .

RUN mvn clean package

EXPOSE 8183

RUN ls
RUN pwd

ENTRYPOINT ["java"]
CMD ["-jar", "./target/service-c-0.0.1.jar"]