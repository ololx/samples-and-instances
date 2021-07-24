FROM masstroy/alpine-docker-java-maven

# get the latest source of Zipkin Server
RUN curl -sSL https://zipkin.io/quickstart.sh | bash -s

EXPOSE 9411

# Run the server
ENTRYPOINT ["java"]
CMD ["-jar", "./zipkin.jar"]