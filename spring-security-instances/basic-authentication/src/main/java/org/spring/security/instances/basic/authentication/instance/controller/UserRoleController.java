package org.spring.security.instances.basic.authentication.instance.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.spring.security.instances.basic.authentication.instance.core.mapping.MapperAdapter;
import org.spring.security.instances.basic.authentication.instance.model.detail.UserRoleDetail;
import org.spring.security.instances.basic.authentication.instance.service.UserRoleService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * The type User role controller.
 *
 * @author Alexander A. Kropotin
 * @project basic -authentication
 * @created 2021 -07-26 09:08 <p>
 */
@Api(
        value="UserRoleController",
        description="Контроллер обрабатывающий запросы ролей пользователей"
)
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
@Validated
@CrossOrigin(origins = "/**")
@RequestMapping(value = "/user-role")
@RestController
public class UserRoleController {

    @Qualifier("SimpleUserRoleService")
    UserRoleService userRoleService;

    /**
     * Gets cors headers.
     *
     * @param response the response
     */
    @RequestMapping(method=RequestMethod.OPTIONS)
    public void getCorsHeaders(HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, OPTIONS");
        response.addHeader("Access-Control-Allow-Headers", "origin, content-type, accept, x-requested-with");
        response.addHeader("Access-Control-Max-Age", "3600");
    }

    /**
     * Retrieve list.
     *
     * @return the list
     * @throws MapperAdapter.MappingException the mapping exception
     */
    @ApiOperation(
            value = "Выбрать информацию о ролях из бд",
            notes = "Метод принимает запросы на выборку всей инфомации о ролях из БД"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Запрос выполнен успешно <br />"
                            + "<b>Пример ответа: </b><br />" +
                            "{}",
                    response = UserRoleDetail.class,
                    responseContainer = "List"
            ),
            @ApiResponse(
                    code = 400,
                    message = "Возникли проблемы во время выполнения запроса - проверьте корректность данных",
                    response = ExceptionController.ExceptionDetail.class
            )
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(
            path = "/",
            produces = "application/json"
    )
    public List<UserRoleDetail> retrieveAllUserRoles() throws MapperAdapter.MappingException {
        log.info("Receive request");
        List<UserRoleDetail> userRoleDetail = (List<UserRoleDetail>) this.userRoleService.retrieve();
        log.info("Send response - {}", userRoleDetail);

        return userRoleDetail;
    }
}
