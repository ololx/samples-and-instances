package org.restful.data.storing.instances.controller;

import lombok.extern.slf4j.Slf4j;
import org.restful.data.storing.instances.model.detail.ExceptionDetail;
import org.springframework.dao.DataIntegrityViolationException;
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
import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Exception controller.
 */
@Slf4j
@CrossOrigin(origins = "/**")
@RestControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    /**
     * Handle exception internal response entity.
     * <p>
     * Всегда 400 статус
     *
     * @param ex      the ex
     * @param body    the body
     * @param headers the headers
     * @param status  the status
     * @param request the request
     * @return the response entity
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex,
                                                             @Nullable Object body,
                                                             HttpHeaders headers,
                                                             HttpStatus status,
                                                             WebRequest request) {

        return new ResponseEntity<>(body, headers, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle method argument not valid response entity.
     * <p>
     * Обработка ошибок запроса к API
     * BindException - выдается при фатальных ошибках привязки
     * MethodArgumentNotValidException - выдается, когда аргумент с аннотацией @Valid не прошел проверку
     *
     * @param e       the e
     * @param headers the headers
     * @param status  the status
     * @param request the request
     * @return the response entity
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
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

        ExceptionDetail exceptionDetail = ExceptionDetail.builder()
                .status(HttpStatus.BAD_REQUEST)
                .comment("Ошибка запроса к API - невалидные значения параметров запроса")
                .message(messages.stream().collect(Collectors.joining("<br />")))
                .details(request.getDescription(true))
                .timestamp(new Date())
                .build();

        return handleExceptionInternal(e, exceptionDetail, headers, exceptionDetail.getStatus(), request);
    }

    /**
     * Handle missing servlet request parameter response entity.
     * <p>
     * Обработка ошибок запроса к API
     * MissingServletRequestPartException - выдается, когда часть составного запроса не найдена (отсутствует параметр)
     *
     * @param e       the e
     * @param headers the headers
     * @param status  the status
     * @param request the request
     * @return the response entity
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException e,
                                                                          HttpHeaders headers,
                                                                          HttpStatus status,
                                                                          WebRequest request) {
        log.error("exception - " + request, e);

        String message = String.format("Потерялся параметр - %s", e.getParameterName());

        ExceptionDetail exceptionDetail = ExceptionDetail.builder()
                .status(HttpStatus.BAD_REQUEST)
                .comment("Ошибка запроса к API - отсутствует параметр запроса")
                .message(message)
                .details(request.getDescription(true))
                .timestamp(new Date())
                .build();

        return handleExceptionInternal(e, exceptionDetail, headers, exceptionDetail.getStatus(), request);
    }

    /**
     * Handle constraint violation response entity.
     * <p>
     * Обработка ошибок запроса к API
     * ConstrainViolationException - сообщает о результате нарушения ограничений валидации значений запроса
     *
     * @param e       the e
     * @param request the request
     * @return the response entity
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

        ExceptionDetail exceptionDetail = ExceptionDetail.builder()
                .status(HttpStatus.BAD_REQUEST)
                .comment("Ошибка запроса к API - невалидные значения параметров запроса")
                .message(messages.stream().collect(Collectors.joining("<br />")))
                .details(request.getDescription(true))
                .timestamp(new Date())
                .build();

        return handleExceptionInternal(e, exceptionDetail, new HttpHeaders(), exceptionDetail.getStatus(), request);
    }

    /**
     * Handle constraint violation from transaction response entity.
     * <p>
     * Обработка ошибок запроса к API
     * ConstrainViolationException - может быть также завернуто в ошибку транзакции (если вызвана там)
     *
     * @param e       the e
     * @param request the request
     * @return the response entity
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
     * <p>
     * Обработка ошибок запроса к API
     * TypeMismatchException - выдается при попытке установить бин свойство с неправильным типом.
     * MethodArgumentTypeMismatchException - выдается, когда аргумент метода не является ожидаемым типом
     *
     * @param e       the e
     * @param request the request
     * @return the response entity
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

        ExceptionDetail exceptionDetail = ExceptionDetail.builder()
                .status(HttpStatus.BAD_REQUEST)
                .comment("Ошибка запроса к API - неправильный формат значения параметров запроса")
                .message(message)
                .details(request.getDescription(true))
                .timestamp(new Date())
                .build();

        return handleExceptionInternal(e, exceptionDetail, new HttpHeaders(), exceptionDetail.getStatus(), request);
    }

    /**
     * Handle sql exception response entity.
     * <p>
     * Обработка ошибок SQL
     *
     * @param e       the e
     * @param request the request
     * @return the response entity
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            SQLException.class,
            DataIntegrityViolationException.class
    })
    public ResponseEntity<Object> handleSqlException(MethodArgumentTypeMismatchException e,
                                                                   WebRequest request) {

        log.error("exception - " + request, e);

        String message = "Неправильные параметры запроса - проверьте данные";

        ExceptionDetail exceptionDetail = ExceptionDetail.builder()
                .status(HttpStatus.BAD_REQUEST)
                .comment("Ошибка запроса к API - ошибка при обращении к СУБД из-за невалидных данных")
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
            IllegalArgumentException.class,
            ValidationException.class
    })
    public final ResponseEntity<Object> handleIllegalArguments(Exception e, WebRequest request) {
        log.error("exception - " + request, e);

        String[] messageParts = e.getMessage().split (": ");
        String message = "";
        for(int i = 1; i < messageParts.length; i++) {
            message += messageParts[i];
        }


        ExceptionDetail exceptionDetail = ExceptionDetail.builder()
                .status(HttpStatus.BAD_REQUEST)
                .comment("Ошибка запроса к API - указаны параметры запроса, для которых не нашлось данных в системе")
                .message(message)
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
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            Exception.class
    })
    public final ResponseEntity<Object> handleUnspecified(Exception e, WebRequest request) {
        log.error("exception - " + request, e);

        ExceptionDetail exceptionDetail = ExceptionDetail.builder()
                .status(HttpStatus.BAD_REQUEST)
                .comment("Ошибка выполнения запроса API - ошибка связана с отправкой невалидных данных")
                .message("Неизвестная ошибка, связанная с неправильным использованием продукта " +
                        "- обратитесь к системному администратору")
                .details(request.getDescription(true))
                .timestamp(new Date())
                .build();

        return handleExceptionInternal(e, exceptionDetail, new HttpHeaders(), exceptionDetail.getStatus(), request);
    }
}