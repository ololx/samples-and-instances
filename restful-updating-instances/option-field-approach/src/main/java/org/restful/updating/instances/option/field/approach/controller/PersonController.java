/**
 * Copyright 2020 the project restful-updating-instances authors
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
package org.restful.updating.instances.option.field.approach.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.restful.updating.instances.option.field.approach.model.entity.Person;
import org.restful.updating.instances.option.field.approach.model.payload.PersonDetail;
import org.restful.updating.instances.option.field.approach.repository.PersonRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

/**
 * @project restful-updating-instances
 * @created 28.06.2020 07:54
 * <p>
 * @author Alexander A. Kropotin
 */
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
@CrossOrigin(value = "/**")
@RestController
@RequestMapping("/persons")
public class PersonController {

    PersonRepository peopleRepository;

    ObjectMapper objectMapper;

    @SneakyThrows
    @PatchMapping(
            path = "/{uid}"
    )
    public PersonDetail update(
            @ApiParam(
                    name="uid",
                    value = "The updatePersonRequest entity uid",
                    required = true
            ) @PathVariable Long uid,
            @ApiParam(
                    name="updatePersonRequest",
                    value = "The updatePersonRequest entity model",
                    required = true
            ) @RequestBody PersonDetail updatePersonRequest) {
        log.info("Catch the request with dto: {}", updatePersonRequest);

        Person people = this.peopleRepository.findById(uid).orElse(null);
        assertNotNull(people, String.format("Person with uid - {} is not existed", uid));
        log.info("Select the entity from the database: {}", people);

        objectMapper.updateValue(people, updatePersonRequest);
        peopleRepository.save(people);
        log.info("Update the entity: {}", people);

        PersonDetail updatePersonResponse = new PersonDetail();
        BeanUtils.copyProperties(people, updatePersonResponse);
        log.info("Return the result of request: {}", updatePersonResponse);

        return updatePersonResponse;
    }
}
