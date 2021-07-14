package org.some.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.annotations.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.some.api.commons.mapping.MapperAdapter;
import org.some.api.model.detail.SomeApiDetail;
import org.some.api.model.detail.SomeDataDetail;
import org.some.api.service.SomeDataService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
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

    @Qualifier("SimpleSomeDataService")
    SomeDataService someDataService;

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
    @JsonView(SomeApiDetail.Create.class)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
            path = "/",
            consumes = "application/json",
            produces = "application/json"
    )
    public SomeDataDetail createSomeDataInstance(
            @ApiParam(
                    example = "{\"id\": 1, \"someString\": \"bla bla\"}",
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value = "{\"id\": 1, \"someString\": \"bla bla\"}"
                            )
                    ),
                    name="someDataInstance",
                    value = "The instance of the some data entity",
                    required = true
            ) @RequestBody SomeDataDetail someDataInstance) throws MapperAdapter.MappingException {
        log.info("Receive request - {}", someDataInstance);
        SomeDataDetail someDataDetail = this.someDataService.create(someDataInstance);
        log.info("Send response - {}", someDataDetail);

        return someDataDetail;
    }

    /**
     * Update some data instance json node.
     *
     * @param id               the id
     * @param someDataInstance the some data instance
     * @return the json node
     */
    @ApiOperation(
            value = "Update some data instance",
            notes = "This method allows to change some data instance"
    )
    @JsonView(SomeApiDetail.Update.class)
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(
            path = "/{id}",
            consumes = "application/json",
            produces = "application/json"
    )
    public SomeDataDetail updateSomeDataInstance(
            @ApiParam(
                    name="id",
                    value = "The instance of the some data entity",
                    required = true
            ) @PathVariable(name = "id") int id,
            @ApiParam(
                    name="someDataInstance",
                    value = "The instance of the some data entity",
                    required = true
            ) @RequestBody SomeDataDetail someDataInstance) throws MapperAdapter.MappingException {
        log.info("Receive request - {} and {}", id, someDataInstance);
        SomeDataDetail someDataDetail = this.someDataService.update(id, someDataInstance);
        log.info("Send response - {}", someDataDetail);

        return someDataDetail;
    }

    /**
     * Delete some data instance json node.
     *
     * @param id the id
     * @return the json node
     */
    @ApiOperation(
            value = "Delete some data instance",
            notes = "This method allows to remove some data instance"
    )
    @JsonView(SomeApiDetail.Delete.class)
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(
            path = "/{id}",
            produces = "application/json"
    )
    public SomeDataDetail deleteSomeDataInstance(
            @ApiParam(
                    name="id",
                    value = "The instance of the some data entity",
                    required = true
            ) @PathVariable(name = "id") int id) {
        log.info("Receive request - {}", id);
        SomeDataDetail someDataDetail = this.someDataService.delete(id);
        log.info("Send response - {}", someDataDetail);

        return someDataDetail;
    }

    /**
     * Read some data instance json node.
     *
     * @param id the id
     * @return the json node
     */
    @ApiOperation(
            value = "Read some data instance",
            notes = "This method allows to get some data instance"
    )
    @JsonView(SomeApiDetail.Retrieve.class)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(
            path = "/{id}",
            produces = "application/json"
    )
    public SomeDataDetail readSomeDataInstance(
            @ApiParam(
                    name="id",
                    value = "The instance of the some data entity",
                    required = true
            ) @PathVariable(name = "id") int id) throws MapperAdapter.MappingException {
        log.info("Receive request - {}", id);
        SomeDataDetail someDataDetail = this.someDataService.read(id);
        log.info("Send response - {}", someDataDetail);

        return someDataDetail;
    }
}
