FROM alpine:3.6

RUN apk --no-cache add curl

COPY . .

ENTRYPOINT ["sh"]
CMD ["./some-api-crud-executing.sh"]