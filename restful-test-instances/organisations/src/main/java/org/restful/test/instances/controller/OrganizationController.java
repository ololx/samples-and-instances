package org.restful.test.instances.controller;

import io.swagger.annotations.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.restful.test.instances.model.detail.ExceptionDetail;
import org.restful.test.instances.model.detail.OrganizationDetail;
import org.restful.test.instances.service.OrganizationService;
import org.restful.test.instances.service.mapping.CustomModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @project restful-test-instances
 * @created 09.09.2020 15:24
 * <p>
 * @author Alexander A. Kropotin
 */
@Api(
        value="OrganizationController",
        description="Контроллер предоставляет CRUD операции для\nсоздания, выборки, обновления и удаления сущностей \"Организация\""
)
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
@Validated
@RequestMapping(value = "/organizations")
@CrossOrigin(origins = "/**")
@RestController
public class OrganizationController {

    /*
     * Сервис для организаций
     */
    OrganizationService organizationService;

    /**
     * Create organization detail.
     *
     * @param organizationDetail the organization detail
     * @return the organization detail
     * @throws CustomModelMapper.MappingException the mapping exception
     */
    @ApiOperation(
            value = "Создать новую организацию",
            notes = "Метод принимает запрос на создание новой организации"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Операция выполнена успешно",
                    response = OrganizationDetail.class
            ),
            @ApiResponse(
                    code = 400,
                    message = "Операция не выполнена - проверьте корректность данных",
                    response = ExceptionDetail.class
            )
    })
    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public OrganizationDetail create(
            @ApiParam(
                    name="organizationDetail",
                    value = "Данные для сущности \"Организация\"",
                    required = true
            )
            @RequestBody
                    OrganizationDetail organizationDetail) throws CustomModelMapper.MappingException {

        log.info("Получили запрос - {}", organizationDetail);

        return this.organizationService.create(organizationDetail);
    }

    @ApiOperation(
            value = "Изменить организацию",
            notes = "Метод принимает запрос на изменение данных организации"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Операция выполнена успешно",
                    response = OrganizationDetail.class
            ),
            @ApiResponse(
                    code = 400,
                    message = "Операция не выполнена - проверьте корректность данных",
                    response = ExceptionDetail.class
            )
    })
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(
            value = "/{uid}"
    )
    public OrganizationDetail update(
            @ApiParam(
                    name="uid",
                    value = "Идентификатор сущности \"Организация\"",
                    example = "1",
                    required = true
            )
            @PathVariable
                    Long uid,
            @ApiParam(
                    name="organizationDetail",
                    value = "Данные для сущности \"Организация\"",
                    example = "{\"name\": \"WCorp\"}",
                    required = true
            )
            @RequestBody
                    OrganizationDetail organizationDetail) throws CustomModelMapper.MappingException {

        log.info("Получили запрос - {}", organizationDetail);

        return this.organizationService.update(uid, organizationDetail);
    }

    @ApiOperation(
            value = "Удалить организацию",
            notes = "Метод принимает запрос на удаление данных организации"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Операция выполнена успешно",
                    response = OrganizationDetail.class
            ),
            @ApiResponse(
                    code = 400,
                    message = "Операция не выполнена - проверьте корректность данных",
                    response = ExceptionDetail.class
            )
    })
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(
            value = "/{uid}"
    )
    public OrganizationDetail delete(
            @ApiParam(
                    name="uid",
                    value = "Идентификатор сущности \"Организация\"",
                    example = "1",
                    required = true
            )
            @PathVariable
                    Long uid) throws CustomModelMapper.MappingException {

        log.info("Получили запрос - {}", uid);

        return this.organizationService.delete(uid);
    }

    @ApiOperation(
            value = "Выбрать организации",
            notes = "Метод принимает запрос на выборку данных организаций"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Операция выполнена успешно",
                    response = OrganizationDetail.class,
                    responseContainer = "List"
            ),
            @ApiResponse(
                    code = 400,
                    message = "Операция не выполнена - проверьте корректность данных",
                    response = ExceptionDetail.class
            )
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<OrganizationDetail> find(
            @ApiParam(
                    name="uid",
                    value = "Идентификатор сущности \"Организация\"",
                    example = "1",
                    required = false
            )
            @RequestParam(
                    value = "uid",
                    required = false
            )
                    List<Long> uid,
            @ApiParam(
                    name="name",
                    value = "Наименование сущности \"Организация\"",
                    example = "1",
                    required = false
            )
            @RequestParam(
                    value = "name",
                    required = false
            )
                    List<String> name,
            @ApiParam(
                    name="inn",
                    value = "ИНН сущности \"Организация\"",
                    example = "1",
                    required = false
            )
            @RequestParam(
                    value = "inn",
                    required = false
            )
                    List<String> inn,
            @ApiParam(
                    name="kpp",
                    value = "КПП сущности \"Организация\"",
                    example = "1",
                    required = false
            )
            @RequestParam(
                    value = "kpp",
                    required = false
            )
                    List<String> kpp,
            @ApiParam(
                    name="address",
                    value = "Адрес сущности \"Организация\"",
                    example = "1",
                    required = false
            )
            @RequestParam(
                    value = "address",
                    required = false
            )
                    List<String> address) throws CustomModelMapper.MappingException {

        log.info(
                "Получили запрос:\nuid - {}\nname - {}\ninn - {}\nkpp - {}\naddress - {}",
                uid,
                name,
                inn,
                kpp,
                address
        );

        return this.organizationService.find(
                uid,
                name,
                inn,
                kpp,
                address
        );
    }
}
