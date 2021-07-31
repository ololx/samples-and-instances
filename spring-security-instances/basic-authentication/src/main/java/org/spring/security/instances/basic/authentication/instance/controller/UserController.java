package org.spring.security.instances.basic.authentication.instance.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.spring.security.instances.basic.authentication.instance.core.mapping.MapperAdapter;
import org.spring.security.instances.basic.authentication.instance.model.detail.BasicAuthenticationInstanceDetail;
import org.spring.security.instances.basic.authentication.instance.model.detail.UserDetail;
import org.spring.security.instances.basic.authentication.instance.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * The type User controller.
 *
 * @author Alexander A. Kropotin
 * @project basic -authentication
 * @created 2021 -07-26 09:08 <p>
 */
@Api(
        value="UserController",
        description="Контроллер обрабатывающий запросы пользователей"
)
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
@Validated
@CrossOrigin(origins = "/**")
@RequestMapping(value = "/user")
@RestController
public class UserController {

    /**
     * The User service.
     */
    @Qualifier("SimpleUserService")
    UserService userService;

    /**
     * Gets cors headers.
     *
     * @param response the response
     */
    @RequestMapping(method=RequestMethod.OPTIONS)
    public void getCorsHeaders(HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PATCH, DELETE, OPTIONS");
        response.addHeader("Access-Control-Allow-Headers", "origin, content-type, accept, x-requested-with");
        response.addHeader("Access-Control-Max-Age", "3600");
    }

    /**
     * Create user detail.
     *
     * @param userInstance the user detail
     * @return the user detail
     */
    @ApiOperation(
            value = "Завести пользователя",
            notes = "Метод принимает запросы на создание пользователя в системе"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 201,
                    message = "Запрос выполнен успешно <br />"
                            + "<b>Пример ответа: </b><br />" +
                            "",
                    response = UserDetail.class
            ),
            @ApiResponse(
                    code = 400,
                    message = "Возникли проблемы во время выполнения запроса - проверьте корректность данных",
                    response = ExceptionController.ExceptionDetail.class
            )
    })
    @JsonView(BasicAuthenticationInstanceDetail.Create.class)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
            path = "/",
            consumes = "application/json",
            produces = "application/json"
    )
    public UserDetail createUser(
            @ApiParam(
                    name="userInstance",
                    value = "Модель сущности пользователь <br />"
                            + "Пример: {...}",
                    required = true,
                    example = "{...}"
            ) @RequestBody UserDetail userInstance) throws MapperAdapter.MappingException {

        log.info("Receive request - {}", userInstance);
        UserDetail someUserDetail = this.userService.create(userInstance);
        log.info("Send response - {}", someUserDetail);

        return someUserDetail;

    }

    /**
     * Update user detail.
     *
     * @param id         the id
     * @param userInstance the user detail
     * @return the user detail
     */
    @ApiOperation(
            value = "Изменить атрибуты пользователя",
            notes = "Метод принимает запросы на изменение данных пользователя"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Запрос выполнен успешно <br />"
                            + "<b>Пример ответа: </b><br />" +
                            "{...}",
                    response = UserDetail.class
            ),
            @ApiResponse(
                    code = 400,
                    message = "Возникли проблемы во время выполнения запроса - проверьте корректность данных",
                    response = ExceptionController.ExceptionDetail.class
            )
    })
    @JsonView(BasicAuthenticationInstanceDetail.Update.class)
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(
            value = "/{id}",
            consumes = "application/json",
            produces = "application/json"
    )
    public UserDetail updateUser(
            @ApiParam(
                    name="id",
                    value = "Идентификатор пользователя <br />"
                            + "Пример: 1",
                    required = true,
                    example = "1"
            ) @PathVariable(
                    value = "id",
                    required = true) Integer id,
            @ApiParam(
                    name="userInstance",
                    value = "Модель сущности пользователь <br />"
                            + "Пример: {...}",
                    required = true,
                    example = "{...}"
            ) @RequestBody UserDetail userInstance) throws MapperAdapter.MappingException {
        log.info("Receive request - {} and {}", id, userInstance);
        UserDetail userDetail = this.userService.update(id, userInstance);
        log.info("Send response - {}", userDetail);

        return userDetail;
    }

    /**
     * Delete user detail.
     *
     * @param id the id
     * @return the user detail
     */
    @ApiOperation(
            value = "Удалить информацию о пользователе из бд",
            notes = "Метод принимает запросы на удвление инфомации о пользователях в БД"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Запрос выполнен успешно <br />"
                            + "<b>Пример ответа: </b><br />" +
                            "{}",
                    response = UserDetail.class
            ),
            @ApiResponse(
                    code = 400,
                    message = "Возникли проблемы во время выполнения запроса - проверьте корректность данных",
                    response = ExceptionController.ExceptionDetail.class
            )
    })
    @JsonView(BasicAuthenticationInstanceDetail.Delete.class)
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(
            value = "/{id}",
            produces = "application/json"
    )
    public UserDetail deleteUser(
            @ApiParam(
                    name="id",
                    value = "Идентификатор пользователя <br />"
                            + "Пример: 7",
                    required = true,
                    example = "7"
            ) @PathVariable(
                    value = "id",
                    required = true) Integer id) {
        log.info("Receive request - {}", id);
        UserDetail userDetail = this.userService.delete(id);
        log.info("Send response - {}", userDetail);

        return userDetail;
    }

    /**
     * BasicAuthenticationInstanceDetail.Retrieve user detail.
     *
     * @param id the id
     * @return the user detail
     */
    @ApiOperation(
            value = "Выбрать информацию о пользователе из бд",
            notes = "Метод принимает запросы на выборку инфомации о пользователях из БД"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Запрос выполнен успешно <br />"
                            + "<b>Пример ответа: </b><br />" +
                            "{...}",
                    response = UserDetail.class
            ),
            @ApiResponse(
                    code = 400,
                    message = "Возникли проблемы во время выполнения запроса - проверьте корректность данных",
                    response = ExceptionController.ExceptionDetail.class
            )
    })
    @JsonView(BasicAuthenticationInstanceDetail.Retrieve.class)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(
            value = "/{id}",
            produces = "application/json"
    )
    public UserDetail retrieveUser(
            @ApiParam(
                    name="id",
                    value = "Идентификатор пользователя<br />"
                            + "Пример: 1",
                    required = false,
                    example = "1"
            ) @PathVariable(
                    value = "id",
                    required = false) Integer id) throws MapperAdapter.MappingException {

        log.info("Receive request - {}", id);
        UserDetail userDetail = this.userService.retrieve(id);
        log.info("Send response - {}", userDetail);

        return userDetail;
    }

    @ApiOperation(
            value = "Выбрать информацию о пользователях из бд",
            notes = "Метод принимает запросы на выборку инфомации о пользователях из БД"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Запрос выполнен успешно <br />"
                            + "<b>Пример ответа: </b><br />" +
                            "{...}",
                    response = UserDetail.class,
                    responseContainer = "List"
            ),
            @ApiResponse(
                    code = 400,
                    message = "Возникли проблемы во время выполнения запроса - проверьте корректность данных",
                    response = ExceptionController.ExceptionDetail.class
            )
    })
    @JsonView(BasicAuthenticationInstanceDetail.Retrieve.class)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(
            path = "/",
            produces = "application/json"
    )
    public List<UserDetail> retrieveAllUsers() throws MapperAdapter.MappingException {

        log.info("Receive request");
        List<UserDetail> userDetail = (List<UserDetail>) this.userService.retrieve();
        log.info("Send response - {}", userDetail);

        return userDetail;
    }

}
