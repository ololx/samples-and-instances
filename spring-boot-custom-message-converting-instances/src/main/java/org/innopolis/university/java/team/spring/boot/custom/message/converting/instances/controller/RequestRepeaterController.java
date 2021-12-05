/**
 * Copyright 2021 the project spring-boot-custom-messageconverting-instances authors
 * and the original author or authors annotated by {@author}
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.innopolis.university.java.team.spring.boot.custom.message.converting.instances.controller;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.innopolis.university.java.team.spring.boot.custom.message.converting.instances.configuration.JSONTMessageConverter;
import org.innopolis.university.java.team.spring.boot.custom.message.converting.instances.model.OriginFizzBuzz;
import org.innopolis.university.java.team.spring.boot.custom.message.converting.instances.model.TranslatedBla;
import org.innopolis.university.java.team.spring.boot.custom.message.converting.instances.model.TranslatedFizzBuzz;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @project spring-boot-custom-messageconverting-instances
 * @created 2021-12-05 16:23
 * <p>
 * @author Alexander A. Kropotin
 */
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
@CrossOrigin(origins = "/**")
@RequestMapping(value = "/")
@RestController
public class RequestRepeaterController {

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(
            path = "/json",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public JsonNode repeatJSON(@RequestBody JsonNode request) {
        log.info("Получили запрос c оригинальным JSON- {}", request);

        return request;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(
            path = "/fizz-buzz/origin",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public OriginFizzBuzz repeatFizzBuzzOrigin(@RequestBody OriginFizzBuzz request) {
        log.info("Получили запрос с оригинальным FizzBuzz - {}", request);

        return request;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(
            path = "/fizz-buzz/translated",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public TranslatedFizzBuzz repeatFizzBuzzTranslated(@RequestBody TranslatedFizzBuzz request) {
        log.info("Получили запрос с транслированными полями ФиззБузз - {}", request);

        return request;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(
            path = "/bla/translated",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public TranslatedBla repeatBlaTranslated(@RequestBody TranslatedBla request) {
        log.info("Получили запрос с транслированными полями Бла - {}", request);

        return request;
    }

}
