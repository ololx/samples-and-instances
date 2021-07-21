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
     * @param someDataInstance the some data instance
     * @return the json node
     */
    @ApiOperation(
            value = "Create some data instance",
            notes = "This method allows to create some data instance"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 201,
                    message = "Successfully completed",
                    examples = @Example(value = @ExampleProperty(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            value = "{\"id\": 1, \"someString\": \"bla bla\"}"
                    ))
            ),
            @ApiResponse(
                    code = 200,
                    message = "Successfully completed",
                    examples = @Example(value = @ExampleProperty(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            value = "{\"id\": 1, \"someString\": \"bla bla\"}"
                    ))
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
                    required = false
            ) @PathVariable(
                    name = "id",
                    required = false
            ) Integer id,
            @ApiParam(
                    name="someDataInstance",
                    value = "The instance of the some data entity",
                    required = true
            ) @RequestBody JsonNode someDataInstance) {
        log.info("Receive request - {} and {}", id, someDataInstance);
        ResponseEntity<JsonNode> response = null;
        if (id == null) {
            response = this.someApiClientService.create(someDataInstance);
            log.debug("Send create entity response - {}", response);

            return response;
        }

        response = this.someApiClientService.retrieve(id);
        if (response.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
            response = this.someApiClientService.create(someDataInstance);
            log.debug("Send create entity response - {}", response);

            return response;
        }

        response = this.someApiClientService.update(id, someDataInstance);
        log.debug("Send update entity response - {}", response);

        return response;
    }
}
