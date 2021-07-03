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
        value="DoController",
        description="Контроллер предоставляет CRUD-операции для справочника \"ДО\""
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
            value = "Create new some data instance",
            notes = "This method allows to create new some data instance"
    )
    @PostMapping(
            consumes = "application/json",
            produces = "application/json"
    )
    public JsonNode createNewSomeDataInstance(
            @ApiParam(
                    name="someDataInstance",
                    value = "The instance of the some data entity",
                    required = true
            ) @RequestBody JsonNode someDataInstance) throws JsonProcessingException {
        log.info("Receive new create request - {}", someDataInstance);

        return someDataInstance;
    }
}
