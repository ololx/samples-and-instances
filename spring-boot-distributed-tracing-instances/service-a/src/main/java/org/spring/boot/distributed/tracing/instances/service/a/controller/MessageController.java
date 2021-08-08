package org.spring.boot.distributed.tracing.instances.service.a.controller;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.annotations.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.spring.boot.distributed.tracing.instances.service.a.service.MessageService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * The type Message controller.
 *
 * @author Alexander A. Kropotin
 * @project service -a
 * @created 2021 -08-08 20:55 <p>
 */
@Api(
        value = "MessageController",
        description = "This controller allows to create new message object"
)
@Validated
@CrossOrigin(origins = "/**")
@RequestMapping(value = "/messages")
@RestController
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
public class MessageController {

    @Qualifier("SimpleMessageService")
    MessageService messageService;

    /**
     * Create message response entity.
     *
     * @param message the message
     * @return the response entity
     * @throws Exception the exception
     */
    @ApiOperation(
            value = "Create new message object",
            notes = "This method allows to create new message object"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 201,
                    message = "Successfully completed",
                    response = JsonNode.class,
                    responseContainer = "ResponseEntity"
            ),
            @ApiResponse(
                    code = 400,
                    message = "Operation not performed",
                    response = ExceptionController.ExceptionDetail.class
            )
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
            path = "/",
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<JsonNode> createMessage(
            @ApiParam(
                    name="message",
                    value = "The instance of the message entity <br/>" +
                            "example: <br/>" +
                            "{\"content\": \"123123\"}",
                    required = true,
                    example = "{\"content\": \"message content\"}",
                    defaultValue = "{\"content\": \"message content\"}"
            ) @RequestBody JsonNode message) throws Exception {
        log.info("Receive request - {}", message);
        ResponseEntity<JsonNode> createResponse = this.messageService.create(message);
        log.info("Send response - {}", createResponse);

        return createResponse;
    }
}
