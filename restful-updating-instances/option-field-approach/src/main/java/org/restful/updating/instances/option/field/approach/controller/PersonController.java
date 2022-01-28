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
import org.springframework.web.bind.annotation.*;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

/**
 * The type Person controller.
 *
 * @author Alexander A. Kropotin
 * @project restful -updating-instances
 * @created 28.06.2020 07:54 <p>
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

    /**
     * Update person detail.
     *
     * @param uid                 the uid
     * @param updatePersonRequest the update person request
     * @return the person detail
     */
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
        objectMapper.updateValue(updatePersonResponse, people);
        log.info("Return the result of request: {}", updatePersonResponse);

        return updatePersonResponse;
    }
}
