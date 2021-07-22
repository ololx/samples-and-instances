package org.spring.boot.metrics.instances.some.api.client.controller;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.annotations.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.spring.boot.metrics.instances.some.api.client.service.SomeApiClientService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * The type Some data controller.
 *
 * @author Alexander A. Kropotin
 * @project some -api -client
 * @created 2021 -07-03 11:51 <p>
 */
@Api(
        value="SomeApiDataController",
        description="This controller allows to create/read/update/delete some entities"
)
@Validated
@CrossOrigin(origins = "/**")
@RequestMapping(value = "some-api/some-data/instances")
@RestController
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
public class SomeApiDataController {

    @Qualifier("SimpleSomeApiClientService")
    SomeApiClientService someApiClientService;

    /**
     * Create some data instance json node.
     *
     * @param id               the id
     * @param someDataInstance the some data instance
     * @return the json node
     */
    @ApiOperation(
            value = "Update or create (if not exists) some data instance",
            notes = "This method allows to create some data instance"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 201,
                    message = "Successfully completed",
                    examples = @Example(value = @ExampleProperty(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            value = "{\"id\": 1, \"someString\": \"foo\"}"
                    ))
            ),
            @ApiResponse(
                    code = 200,
                    message = "Successfully completed",
                    examples = @Example(value = @ExampleProperty(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            value = "{\"id\": 1, \"someString\": \"bar\"}"
                    ))
            ),
            @ApiResponse(
                    code = 400,
                    message = "Failure uncompleted",
                    examples = @Example(value = @ExampleProperty(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            value = "{" +
                                    "\"status\": \"BAD_REQUEST\", " +
                                    "\"timestamp\": \"2021-07-22T09:44:56.972+0000\", " +
                                    "\"stackTraces\": \"\", " +
                                    "\"comment\": \"The API execution error - the request data is illegal\", " +
                                    "\"message\": \"Contact the system administrator\", " +
                                    "\"details\": \"uri=/some-api/some-data/instances/1;client=0:0:0:0:0:0:0:1\" " +
                                    "}"
                    ))
            ),
            @ApiResponse(
                    code = 500,
                    message = "Service is unreachable"
            )
    })
    @PutMapping(
            path = "/{id}",
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<JsonNode> changeSomeDataInstance(
            @ApiParam(
                    name="id",
                    value = "The instance of the some data entity",
                    required = true
            ) @PathVariable(
                    name = "id"
            ) Integer id,
            @ApiParam(
                    name="someDataInstance",
                    value = "The instance of the some data entity",
                    required = true
            ) @RequestBody JsonNode someDataInstance) {
        log.info("Receive request - {} and {}", id, someDataInstance);
        ResponseEntity<JsonNode> response = this.someApiClientService.retrieve(id);

        if (response.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
            response = this.someApiClientService.create(someDataInstance);
            log.debug("Send create entity response - {}", response);

            return response;
        }

        response = this.someApiClientService.update(id, someDataInstance);
        log.debug("Send update entity response - {}", response);

        return response;
    }

    /**
     * Delete some data instance response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @ApiOperation(
            value = "Delete some data instance",
            notes = "This method allows to create some data instance"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully completed",
                    examples = @Example(value = @ExampleProperty(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            value = "{\"id\": 1}"
                    ))
            ),
            @ApiResponse(
                    code = 400,
                    message = "Failure uncompleted",
                    examples = @Example(value = @ExampleProperty(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            value = "{" +
                                    "\"status\": \"NOT_FOUND\", " +
                                    "\"timestamp\": \"2021-07-22T09:44:56.972+0000\", " +
                                    "\"stackTraces\": \"\", " +
                                    "\"comment\": \"The API execution error - requested entity is not presented in the service\", " +
                                    "\"message\": \"There is no SomeData with this id - 111\", " +
                                    "\"details\": \"uri=/some-api/some-data/instances/1;client=0:0:0:0:0:0:0:1\" " +
                                    "}"
                    ))
            ),
            @ApiResponse(
                    code = 500,
                    message = "Service is unreachable"
            )
    })
    @DeleteMapping(
            path = "/{id}",
            produces = "application/json"
    )
    public ResponseEntity<JsonNode> deleteSomeDataInstance(
            @ApiParam(
                    name="id",
                    value = "The instance of the some data entity",
                    required = true
            ) @PathVariable(
                    name = "id"
            ) Integer id) {
        log.info("Receive request - {}", id);
        ResponseEntity<JsonNode> response = this.someApiClientService.delete(id);
        log.debug("Send delete entity response - {}", response);

        return response;
    }
}
