package org.spring.security.instances.basic.authentication.instance.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.spring.security.instances.basic.authentication.instance.model.exception.NonExistentEntityException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The type Swagger configuration.
 *
 * @author Alexander A. Kropotin
 * @project basic -authentication
 * @created 2021 -07-26 09:08 <p>
 */
@Slf4j
@CrossOrigin(origins = "/**")
@RestControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ApiModel(
            value = "ExceptionDetail",
            description = "The model of exception details"
    )
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(
            level = AccessLevel.PRIVATE
    )
    public static class ExceptionDetail {

        /**
         * The Status.
         */
        @ApiModelProperty(
                notes = "The HTTP state code",
                example = "1"
        )
        @JsonProperty(value = "status")
        HttpStatus status;

        /**
         * The Timestamp.
         */
        @ApiModelProperty(
                notes = "The date",
                example = "1"
        )
        @JsonProperty(value = "timestamp")
        Date timestamp;

        /**
         * The Stack trace.
         */
        @ApiModelProperty(
                notes = "The stack trace",
                example = "1"
        )
        @JsonProperty(value = "stackTraces")
        String stackTraces;

        /**
         * The Comment.
         */
        @ApiModelProperty(
                notes = "The exception custom message",
                example = "1"
        )
        @JsonProperty(value = "comment")
        String comment;

        /**
         * The Message.
         */
        @ApiModelProperty(
                notes = "The exception origin message",
                example = "1"
        )
        @JsonProperty(value = "message")
        String message;

        /**
         * The Details.
         */
        @ApiModelProperty(
                notes = "Request details",
                example = "1"
        )
        @JsonProperty(value = "details")
        String details;

        {
            this.timestamp = new Date();
        }
    }

    /**
     * Handle exception internal response entity.
     *
     * @param ex      the ex
     * @param body    the body
     * @param headers the headers
     * @param status  the status
     * @param request the request
     * @return the response entity
     */
    //Всегда 400
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex,
                                                             @Nullable Object body,
                                                             HttpHeaders headers,
                                                             HttpStatus status,
                                                             WebRequest request) {

        return new ResponseEntity<>(body, headers, status);
    }

    /**
     * Handle method argument not valid response entity.
     *
     * @param e       the e
     * @param headers the headers
     * @param status  the status
     * @param request the request
     * @return the response entity
     */
    /*
     * Обработка ошибок запроса к API
     * BindException - выдается при фатальных ошибках привязки
     * MethodArgumentNotValidException - выдается, когда аргумент с аннотацией @Valid не прошел проверку
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        log.error("exception - " + request, e);

        List<String> messages = new ArrayList<>();

        int numberOfErrorMessage = 1;
        for(FieldError error : e.getBindingResult().getFieldErrors()) {
            messages.add(String.format(
                    "Wrong request param #%s - %s",
                    numberOfErrorMessage,
                    error.getDefaultMessage())
            );
            numberOfErrorMessage++;
        }

        numberOfErrorMessage = 1;
        for (ObjectError error : e.getBindingResult().getGlobalErrors()) {
            messages.add(String.format(
                    "Неправильный запрос #%s - %s",
                    numberOfErrorMessage,
                    error.getDefaultMessage())
            );
            numberOfErrorMessage++;
        }

        String stackTrace = Arrays.stream(e.getStackTrace())
                .map(stackTraceElement -> stackTraceElement.toString() +  " \n " )
                .collect(Collectors.joining());

        ExceptionDetail exceptionDetail = ExceptionDetail.builder()
                .status(HttpStatus.BAD_REQUEST)
                .stackTraces(stackTrace)
                .comment("The API execution error - wrong request param")
                .message(String.join("<br />", messages))
                .details(request.getDescription(true))
                .timestamp(new Date())
                .build();

        return handleExceptionInternal(e, exceptionDetail, headers, exceptionDetail.getStatus(), request);
    }

    /**
     * Handle missing servlet request parameter response entity.
     *
     * @param e       the e
     * @param headers the headers
     * @param status  the status
     * @param request the request
     * @return the response entity
     */
    /*
     * Обработка ошибок запроса к API
     * MissingServletRequestPartException - выдается, когда часть составного запроса не найдена (отсутствует параметр)
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException e,
                                                                          HttpHeaders headers,
                                                                          HttpStatus status,
                                                                          WebRequest request) {
        log.error("exception - " + request, e);

        String message = String.format("The param is lost - %s", e.getParameterName());

        String stackTrace = Arrays.stream(e.getStackTrace())
                .map(stackTraceElement -> stackTraceElement.toString() +  " \n " )
                .collect(Collectors.joining());

        ExceptionDetail exceptionDetail = ExceptionDetail.builder()
                .status(HttpStatus.BAD_REQUEST)
                .stackTraces(stackTrace)
                .comment("The API execution error - the param is lost")
                .message(message)
                .details(request.getDescription(true))
                .timestamp(new Date())
                .build();

        return handleExceptionInternal(e, exceptionDetail, headers, exceptionDetail.getStatus(), request);
    }

    /**
     * Handle constraint violation response entity.
     *
     * @param e       the e
     * @param request the request
     * @return the response entity
     */
    /*
     * Обработка ошибок запроса к API
     * ConstrainViolationException - сообщает о результате нарушения ограничений валидации значений запроса
     */
    @ExceptionHandler({
            ConstraintViolationException.class
    })
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException e, WebRequest request) {
        log.error("exception - " + request, e);

        List<String> messages = new ArrayList<>();

        int numberOfErrorMessage = 1;
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            messages.add(String.format(
                    "Wrong request paramа #%s - %s",
                    numberOfErrorMessage,
                    violation.getMessage())
            );
            numberOfErrorMessage++;
        }

        String stackTrace = Arrays.stream(e.getStackTrace())
                .map(stackTraceElement -> stackTraceElement.toString() +  " \n " )
                .collect(Collectors.joining());

        ExceptionDetail exceptionDetail = ExceptionDetail.builder()
                .status(HttpStatus.BAD_REQUEST)
                .stackTraces(stackTrace)
                .comment("The API execution error - wrong request param")
                .message(String.join("<br />", messages))
                .details(request.getDescription(true))
                .timestamp(new Date())
                .build();

        return handleExceptionInternal(e, exceptionDetail, new HttpHeaders(), exceptionDetail.getStatus(), request);
    }

    /**
     * Handle constraint violation from transaction response entity.
     *
     * @param e       the e
     * @param request the request
     * @return the response entity
     */
    /*
     * Обработка ошибок запроса к API
     * ConstrainViolationException - может быть также завернуто в ошибку транзакции (если вызвана там)
     */
    @ExceptionHandler({
            TransactionSystemException.class
    })
    public final ResponseEntity<Object> handleConstraintViolationFromTransaction(Exception e, WebRequest request) {
        log.error("exception - " + request, e);

        Throwable cause = e.getCause();
        while((cause != null) && !(cause instanceof ConstraintViolationException)) {
            cause = cause.getCause();
        }

        if (cause instanceof ConstraintViolationException) {
            return this.handleConstraintViolation((ConstraintViolationException) cause, request);
        }

        return this.handleUnspecified(e, request);
    }

    /**
     * Handle method argument type mismatch response entity.
     *
     * @param e       the e
     * @param request the request
     * @return the response entity
     */
    /*
     * Обработка ошибок запроса к API
     * TypeMismatchException - выдается при попытке установить бин свойство с неправильным типом.
     * MethodArgumentTypeMismatchException - выдается, когда аргумент метода не является ожидаемым типом
     */
    @ExceptionHandler({
            MethodArgumentTypeMismatchException.class
    })
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e,
                                                                   WebRequest request) {

        log.error("exception - " + request, e);

        String type = "array";
        if(e.getRequiredType() == String.class)
            type = "string";
        else if(e.getRequiredType() == Date.class
                || e.getRequiredType() == Calendar.class
                || e.getRequiredType() == Timestamp.class)
            type = "date";
        else if(e.getRequiredType() == Integer.class
                || e.getRequiredType() == Long.class
                || e.getRequiredType() == Short.class
                || e.getRequiredType() == Byte.class
                || e.getRequiredType() == Double.class
                || e.getRequiredType() == BigDecimal.class)
            type = "digit";

        String message = String.format("The param %s must to be define as %s", e.getName(), type);

        String stackTrace = Arrays.stream(e.getStackTrace())
                .map(stackTraceElement -> stackTraceElement.toString() +  " \n " )
                .collect(Collectors.joining());

        ExceptionDetail exceptionDetail = ExceptionDetail.builder()
                .status(HttpStatus.BAD_REQUEST)
                .stackTraces(stackTrace)
                .comment("The API execution error - wrong request param format")
                .message(message)
                .details(request.getDescription(true))
                .timestamp(new Date())
                .build();

        return handleExceptionInternal(e, exceptionDetail, new HttpHeaders(), exceptionDetail.getStatus(), request);
    }

    /**
     * Handle sql exception response entity.
     *
     * @param e       the e
     * @param request the request
     * @return the response entity
     */
    /*
     * Обработка ошибок SQL
     */
    @ExceptionHandler({
            SQLException.class
    })
    public ResponseEntity<Object> handleSqlException(MethodArgumentTypeMismatchException e,
                                                                   WebRequest request) {

        log.error("exception - " + request, e);

        String message = "The wrong request params - please check data";

        String stackTrace = Arrays.stream(e.getStackTrace())
                .map(stackTraceElement -> stackTraceElement.toString() +  " \n " )
                .collect(Collectors.joining());

        ExceptionDetail exceptionDetail = ExceptionDetail.builder()
                .status(HttpStatus.BAD_REQUEST)
                .stackTraces(stackTrace)
                .comment("The API execution error - the sql execution error")
                .message(message)
                .details(request.getDescription(true))
                .timestamp(new Date())
                .build();

        return handleExceptionInternal(e, exceptionDetail, new HttpHeaders(), exceptionDetail.getStatus(), request);
    }

    /**
     * Handle illegal arguments response entity.
     *
     * @param e       the e
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler({
            IllegalArgumentException.class,
            ValidationException.class
    })
    public final ResponseEntity<Object> handleIllegalArguments(Exception e, WebRequest request) {
        log.error("exception - " + request, e);String[] messageParts = e.getMessage().split (": ");

        String message = "";
        for(int i = 1; i < messageParts.length; i++) {
            message += messageParts[i];
        }

        String stackTrace = Arrays.stream(e.getStackTrace())
                .map(stackTraceElement -> stackTraceElement.toString() +  " \n " )
                .collect(Collectors.joining());

        ExceptionDetail exceptionDetail = ExceptionDetail.builder()
                .status(HttpStatus.BAD_REQUEST)
                .stackTraces(stackTrace)
                .comment("The API execution error - illegal arguments")
                .message(message)
                .details(request.getDescription(true))
                .timestamp(new Date())
                .build();

        return handleExceptionInternal(e, exceptionDetail, new HttpHeaders(), exceptionDetail.getStatus(), request);
    }

    /**
     * Handle specified exception response entity.
     *
     * @param e       the e
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(NonExistentEntityException.class)
    public final ResponseEntity<Object> handleSpecifiedException(Exception e, WebRequest request) {
        log.error("exception - " + request, e);

        String stackTrace = Arrays.stream(e.getStackTrace())
                .map(stackTraceElement -> stackTraceElement.toString() +  " \n " )
                .collect(Collectors.joining());

        ExceptionDetail exceptionDetail = ExceptionDetail.builder()
                .status(HttpStatus.NOT_FOUND)
                .stackTraces(stackTrace)
                .comment("The API execution error - requested entity is not presented in the service")
                .message(e.getMessage())
                .details(request.getDescription(true))
                .timestamp(new Date())
                .build();

        return handleExceptionInternal(e, exceptionDetail, new HttpHeaders(), exceptionDetail.getStatus(), request);
    }

    /**
     * Handle unspecified response entity.
     *
     * @param e       the e
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleUnspecified(Exception e, WebRequest request) {
        if(log.isErrorEnabled()) {
            log.error("exception - " + request, e);
        }

        String stackTrace = Arrays.stream(e.getStackTrace())
                .map(stackTraceElement -> stackTraceElement.toString() +  " \n " )
                .collect(Collectors.joining());

        ExceptionDetail exceptionDetail = ExceptionDetail.builder()
                .status(HttpStatus.BAD_REQUEST)
                .stackTraces(stackTrace)
                .comment("The API execution error - the request data is illegal")
                .message("Contact the system administrator")
                .details(request.getDescription(true))
                .timestamp(new Date())
                .build();

        return handleExceptionInternal(e, exceptionDetail, new HttpHeaders(), exceptionDetail.getStatus(), request);
    }
}