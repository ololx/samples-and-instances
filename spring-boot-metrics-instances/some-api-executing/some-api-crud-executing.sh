#!/bin/bash

iteration=1

while [ 1 ]
do
  let iteration++
  curl -X POST "http://some-api:8080/some-data/instances/" -H "accept: application/json" -H "Content-Type: application/json" -d "{\"id\": $iteration, \"someString\": \"$iteration\"}"
  sleep 20
done