package org.some.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.annotations.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * The type Some data controller.
 *
 * @author Alexander A. Kropotin
 * @project some -api
 * @created 2021 -07-03 11:51 <p>
 */
@Api(
        value="SomeDataController",
        description="This controllers allows to create/read/update/delete some entities"
)
@Validated
@RequestMapping(value = "some-data/instances")
@RestController
@CrossOrigin(origins = "/**")
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
public class SomeDataController {

    /**
     * Create new some data instance json node.
     *
     * @param someDataInstance the some data instance
     * @return the json node
     * @throws JsonProcessingException the json processing exception
     */
    @ApiOperation(
            value = "Create some data instance",
            notes = "This method allows to create some data instance"
    )
    @PostMapping(
            path = "/",
            consumes = "application/json",
            produces = "application/json"
    )
    public JsonNode createSomeDataInstance(
            @ApiParam(
                    name="someDataInstance",
                    value = "The instance of the some data entity",
                    required = true
            ) @RequestBody JsonNode someDataInstance) {
        log.info("Receive create request - {}", someDataInstance);

        return someDataInstance;
    }

    @ApiOperation(
            value = "Update some data instance",
            notes = "This method allows to change some data instance"
    )
    @PatchMapping(
            path = "/{id}",
            consumes = "application/json",
            produces = "application/json"
    )
    public JsonNode updateSomeDataInstance(
            @ApiParam(
                    name="id",
                    value = "The instance of the some data entity",
                    required = true
            ) @PathVariable(name = "id") long id,
            @ApiParam(
                    name="someDataInstance",
                    value = "The instance of the some data entity",
                    required = true
            ) @RequestBody JsonNode someDataInstance) {
        log.info("Receive update request - {}\n and - {}", id, someDataInstance);

        return someDataInstance;
    }

    @ApiOperation(
            value = "Delete some data instance",
            notes = "This method allows to remove some data instance"
    )
    @DeleteMapping(
            path = "/{id}",
            produces = "application/json"
    )
    public JsonNode deleteSomeDataInstance(
            @ApiParam(
                    name="id",
                    value = "The instance of the some data entity",
                    required = true
            ) @PathVariable(name = "id") long id) {
        log.info("Receive delete request - {}", id);

        return null;
    }

    @ApiOperation(
            value = "Read some data instance",
            notes = "This method allows to get some data instance"
    )
    @GetMapping(
            path = "/{id}",
            produces = "application/json"
    )
    public JsonNode readSomeDataInstance(
            @ApiParam(
                    name="id",
                    value = "The instance of the some data entity",
                    required = true
            ) @PathVariable(name = "id") long id) {
        log.info("Receive select request - {}", id);

        return null;
    }
}
