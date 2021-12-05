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
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.innopolis.university.java.team.spring.boot.custom.message.converting.instances.configuration.JSONTMessageConverter;
import org.springframework.http.HttpStatus;
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
@RequestMapping(value = "/repeat")
@RestController
public class RequestRepeaterController {

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(
            path = "/",
            consumes = JSONTMessageConverter.APPLICATION_JSONT_VALUE,
            produces = JSONTMessageConverter.APPLICATION_JSONT_VALUE
    )
    public JsonNode repeatJSONT(@RequestBody JsonNode request) {
        log.info("Получили запрос - {}", request);

        return request;
    }
}
