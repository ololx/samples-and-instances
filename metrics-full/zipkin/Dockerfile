FROM masstroy/alpine-docker-java-maven
ADD . /etc/zipkin
#RUN curl -sSL https://zipkin.io/quickstart.sh | bash -s

RUN bash ./etc/zipkin/quickstart.sh

EXPOSE 9411

ENTRYPOINT ["java"]
CMD ["-jar", "./zipkin.jar"]