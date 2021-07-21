package org.spring.boot.metrics.instances.some.api.client.controller;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.annotations.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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

    @Qualifier("RestTemplate")
    RestTemplate someApiClient;

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
            )
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
            path = "/",
            consumes = "application/json",
            produces = "application/json"
    )
    public JsonNode createSomeDataInstance(
            @ApiParam(
                    examples = @Example(value = @ExampleProperty(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            value = "{\"id\": 1, \"someString\": \"bla bla\"}"
                    )),
                    name="someDataInstance",
                    value = "The instance of the some data entity",
                    required = true
            ) @RequestBody JsonNode someDataInstance) {
        log.info("Receive request - {}", someDataInstance);
        HttpEntity<JsonNode> request = new HttpEntity<>(someDataInstance);
        JsonNode someDataDetail = this.someApiClient.postForObject("http://some-api:8081/some-data/instances/", request, JsonNode.class);
        log.info("Send response - {}", someDataDetail);

        return someDataDetail;
    }
}
