package org.spring.boot.distributed.tracing.instances.service.b.controller;

import io.swagger.annotations.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.spring.boot.distributed.tracing.instances.service.b.model.detail.MessageDetail;
import org.spring.boot.distributed.tracing.instances.service.b.service.MessageService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * The type Message controller.
 *
 * @author Alexander A. Kropotin
 * @project service -b
 * @created 2021 -08-04 17:45 <p>
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
     * Create message message detail.
     *
     * @param message the message
     * @return the message detail
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
                    response = MessageDetail.class,
                    responseContainer = "List"
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
    public MessageDetail createMessage(
            @ApiParam(
                    name="message",
                    value = "The instance of the message entity",
                    required = true
            ) @RequestBody MessageDetail message) throws Exception {
        log.info("Receive request - {}", message);
        MessageDetail messageDetail = this.messageService.create(message);
        log.info("Send response - {}", messageDetail);

        return messageDetail;
    }
}
