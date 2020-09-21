package org.restful.data.storing.instances.controller;

import io.swagger.annotations.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.restful.data.storing.instances.model.detail.ExceptionDetail;
import org.restful.data.storing.instances.model.detail.PersonDetail;
import org.restful.data.storing.instances.service.PersonService;
import org.restful.data.storing.instances.service.mapping.CustomModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(
        value="PersonController",
        description="Контроллер предоставляет CRUD операции для\nсоздания, выборки, обновления и удаления " +
                "сущностей \"Person\""
)
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
@Validated
@RequestMapping(value = "/persons")
@CrossOrigin(origins = "/**")
@RestController
public class PersonController {

    /**
     * The Organization service.
     */
    /*
     * Сервис для person
     */
    PersonService organizationService;

    /**
     * Create organization detail.
     *
     * @param organizationDetail the organization detail
     * @return the organization detail
     * @throws CustomModelMapper.MappingException the mapping exception
     */
    @ApiOperation(
            value = "Создать новую person",
            notes = "Метод принимает запрос на создание новой person"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Операция выполнена успешно",
                    response = PersonDetail.class
            ),
            @ApiResponse(
                    code = 400,
                    message = "Операция не выполнена - проверьте корректность данных",
                    response = ExceptionDetail.class
            )
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public PersonDetail create(
            @ApiParam(
                    name="organizationDetail",
                    value = "Данные для сущности \"Person\"<br />" +
                            "Пример:<br />" +
                            "{\n" +
                            "  \"first_name\": \"хмырь\",\n" +
                            "  \"last_name\": \"шмыга\",\n" +
                            "  \"age\": 12\n" +
                            "}",
                    required = true
            )
            @RequestBody
                    PersonDetail organizationDetail) throws CustomModelMapper.MappingException {

        log.info("Получили запрос - {}", organizationDetail);

        return this.organizationService.create(organizationDetail);
    }

    /**
     * Update organization detail.
     *
     * @param uid                the uid
     * @param organizationDetail the organization detail
     * @return the organization detail
     * @throwCustoms ModelMapper.MappingException the mapping exception
     */
    @ApiOperation(
            value = "Изменить person",
            notes = "Метод принимает запрос на изменение данных person"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Операция выполнена успешно",
                    response = PersonDetail.class
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
    public PersonDetail update(
            @ApiParam(
                    name="uid",
                    value = "Идентификатор сущности \"Person\"",
                    example = "1",
                    required = true
            )
            @PathVariable
                    Long uid,
            @ApiParam(
                    name="organizationDetail",
                    value = "Данные для сущности \"Person\"",
                    example = "{\"name\": \"WCorp\"}",
                    required = true
            )
            @RequestBody
                    PersonDetail organizationDetail) throws CustomModelMapper.MappingException {

        log.info("Получили запрос:\nuid - {}\norganization - {}", uid, organizationDetail);

        return this.organizationService.update(uid, organizationDetail);
    }

    /**
     * Delete organization detail.
     *
     * @param uid the uid
     * @return the organization detail
     * @throwCustoms ModelMapper.MappingException the mapping exception
     */
    @ApiOperation(
            value = "Удалить person",
            notes = "Метод принимает запрос на удаление данных person"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Операция выполнена успешно",
                    response = PersonDetail.class
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
    public PersonDetail delete(
            @ApiParam(
                    name="uid",
                    value = "Идентификатор сущности \"Person\"",
                    example = "1",
                    required = true
            )
            @PathVariable
                    Long uid) throws CustomModelMapper.MappingException {

        log.info("Получили запрос - {}", uid);

        return this.organizationService.delete(uid);
    }

    /**
     * Find list.
     *
     * @param uid     the uid
     * @param name    the name
     * @param inn     the inn
     * @param kpp     the kpp
     * @param address the address
     * @return the list
     * @throwCustoms ModelMapper.MappingException the mapping exception
     */
    @ApiOperation(
            value = "Выбрать person",
            notes = "Метод принимает запрос на выборку данных person"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Операция выполнена успешно",
                    response = PersonDetail.class,
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
    public List<PersonDetail> find(
            @ApiParam(
                    name="uid",
                    value = "Идентификатор сущности \"Person\"",
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
                    value = "Наименование сущности \"Person\"",
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
                    value = "ИНН сущности \"Person\"",
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
                    value = "КПП сущности \"Person\"",
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
                    value = "Адрес сущности \"Person\"",
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
