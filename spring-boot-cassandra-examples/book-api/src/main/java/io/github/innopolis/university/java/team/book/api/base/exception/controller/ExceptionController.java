package io.github.innopolis.university.java.team.book.api.base.exception.controller;

import io.github.innopolis.university.java.team.book.api.base.exception.model.ExceptionDetail;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * project well-log
 * created 2021-12-16 18:33
 *
 * @author Alexander A. Kropotin
 */
@Slf4j
@CrossOrigin(origins = "/**")
@RestControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

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
    @ResponseStatus(HttpStatus.BAD_REQUEST)
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
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        if(log.isErrorEnabled()) {
            log.error("exception - " + request, e);
        }

        List<String> messages = new ArrayList<>();

        int numberOfErrorMessage = 1;
        for(FieldError error : e.getBindingResult().getFieldErrors()) {
            messages.add(String.format(
                    "Неправильный параметр запроса #%s - %s",
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
                .stackTrace(stackTrace)
                .comment("Ошибка запроса к API - невалидные значения параметров запроса")
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
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException e,
                                                                          HttpHeaders headers,
                                                                          HttpStatus status,
                                                                          WebRequest request) {
        log.error("exception - " + request, e);

        String message = String.format("Потерялся параметр - %s", e.getParameterName());

        String stackTrace = Arrays.stream(e.getStackTrace())
                .map(stackTraceElement -> stackTraceElement.toString() +  " \n " )
                .collect(Collectors.joining());

        ExceptionDetail exceptionDetail = ExceptionDetail.builder()
                .status(HttpStatus.BAD_REQUEST)
                .stackTrace(stackTrace)
                .comment("Ошибка запроса к API - отсутствует параметр запроса")
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
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            ConstraintViolationException.class
    })
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException e, WebRequest request) {

        log.error("exception - " + request, e);

        List<String> messages = new ArrayList<>();

        int numberOfErrorMessage = 1;
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            messages.add(String.format(
                    "Неправильное значение параметра запроса #%s - %s",
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
                .stackTrace(stackTrace)
                .comment("Ошибка запроса к API - невалидные значения параметров запроса")
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
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            TransactionSystemException.class
    })
    public final ResponseEntity<Object> handleConstraintViolationFromTransaction(Exception e, WebRequest request) {
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
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            MethodArgumentTypeMismatchException.class
    })
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e,
                                                                   WebRequest request) {

        log.error("exception - " + request, e);

        String type = "массивом";
        if(e.getRequiredType() == String.class)
            type = "строкой";
        else if(e.getRequiredType() == Date.class
                || e.getRequiredType() == Calendar.class
                || e.getRequiredType() == Timestamp.class)
            type = "датой";
        else if(e.getRequiredType() == Integer.class
                || e.getRequiredType() == Long.class
                || e.getRequiredType() == Short.class
                || e.getRequiredType() == Byte.class
                || e.getRequiredType() == Double.class
                || e.getRequiredType() == BigDecimal.class)
            type = "числом";

        String message = String.format("Параметр %s должен быть задан %s", e.getName(), type);

        String stackTrace = Arrays.stream(e.getStackTrace())
                .map(stackTraceElement -> stackTraceElement.toString() +  " \n " )
                .collect(Collectors.joining());

        ExceptionDetail exceptionDetail = ExceptionDetail.builder()
                .status(HttpStatus.BAD_REQUEST)
                .stackTrace(stackTrace)
                .comment("Ошибка запроса к API - неправильный формат значения параметров запроса")
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
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            IllegalArgumentException.class
    })
    public final ResponseEntity<Object> handleIllegalArguments(Exception e, WebRequest request) {
        log.error("exception - " + request, e);

        String[] messageParts = e.getMessage().split (": ");
        String message = "";
        for(int i = 0; i < messageParts.length; i++) {
            message += messageParts[i];
        }

        String stackTrace = Arrays.stream(e.getStackTrace())
                .map(stackTraceElement -> stackTraceElement.toString() +  " \n " )
                .collect(Collectors.joining());

        ExceptionDetail exceptionDetail = ExceptionDetail.builder()
                .status(HttpStatus.BAD_REQUEST)
                .stackTrace(stackTrace)
                .comment("Ошибка запроса к API - указаны параметры запроса, для которых не нашлось данных в системе")
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
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            SQLException.class
    })
    public final ResponseEntity<Object> handleSQLException(Exception e, WebRequest request) {
        log.error("exception - " + request, e);

        Throwable cause = e;
        while((cause != null) && !(cause instanceof SQLException)) {
            cause = cause.getCause();
        }

        if (cause instanceof SQLException) {
            SQLException sqlException = (SQLException) cause;
            String stackTrace = Arrays.stream(e.getStackTrace())
                    .map(stackTraceElement -> stackTraceElement.toString() +  " \n " )
                    .collect(Collectors.joining());

            ExceptionDetail exceptionDetail = ExceptionDetail.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .stackTrace(stackTrace)
                    .comment("Ошибка при изменении данных в БД - статус SQL = " + sqlException.getSQLState())
                    .message(sqlException.getLocalizedMessage())
                    .details(request.getDescription(true))
                    .timestamp(new Date())
                    .build();
            return this.handleExceptionInternal(e, exceptionDetail, new HttpHeaders(), exceptionDetail.getStatus(), request);
        }

        return this.handleUnspecified(e, request);
    }

    /**
     * Handle unspecified response entity.
     *
     * @param e       the e
     * @param request the request
     * @return the response entity
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            Exception.class
    })
    public final ResponseEntity<Object> handleUnspecified(Exception e, WebRequest request) {
        log.error("exception - " + request, e);

        String stackTrace = Arrays.stream(e.getStackTrace())
                .map(stackTraceElement -> stackTraceElement.toString() +  " \n " )
                .collect(Collectors.joining());

        ExceptionDetail exceptionDetail = ExceptionDetail.builder()
                .status(HttpStatus.BAD_REQUEST)
                .stackTrace(stackTrace)
                .comment("Ошибка выполнения запроса API - ошибка связана с отправкой невалидных данных")
                .message("Неизвестная ошибка, связанная с неправильным использованием продукта " +
                        "- обратитесь к системному администратору")
                .details(request.getDescription(true))
                .timestamp(new Date())
                .build();

        return handleExceptionInternal(e, exceptionDetail, new HttpHeaders(), exceptionDetail.getStatus(), request);
    }
}