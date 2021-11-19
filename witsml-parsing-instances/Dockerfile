FROM harbor.stageogip.ru/vtd/java14:maven-3.6.3-jdk-11 AS build
WORKDIR /data
COPY . /data

RUN mvn clean package

FROM harbor.stageogip.ru/vtd/java14:11-jre-alpine AS final
WORKDIR /data
COPY --from=build /data/javaxb-implementation/target/javaxb-implementation-0.0.1.jar /data/javaxb-implementation-0.0.1.jar

EXPOSE 8881
ENTRYPOINT ["/bin/sh", "-c", "java -jar javaxb-implementation-0.0.1.jar"]