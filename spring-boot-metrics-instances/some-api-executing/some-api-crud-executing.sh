#!/bin/bash

iteration=1

while [ 1 ]
do
    let iteration++
    curl -X PUT "http://some-api-client:8082/some-api/some-data/instances/$iteration" -H "accept: application/json" -H "Content-Type: application/json" -d "{\"id\": $iteration, \"someString\": \"foo\"}"
    sleep 5
    curl -X PUT "http://some-api-client:8082/some-api/some-data/instances/$iteration" -H "accept: application/json" -H "Content-Type: application/json" -d "{\"id\": $iteration, \"someString\": \"bar\"}"
    sleep 25
done
