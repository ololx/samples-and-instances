package org.orm.patterns.instances.active.jdbc.controller;

import io.swagger.annotations.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.javalite.activejdbc.Base;
import org.orm.patterns.instances.active.jdbc.model.detail.ExceptionDetail;
import org.orm.patterns.instances.active.jdbc.model.detail.PersonDetail;
import org.orm.patterns.instances.active.jdbc.service.PersonService;
import org.orm.patterns.instances.active.jdbc.service.mapping.CustomModelMapper;
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

        Base.open("org.postgresql.Driver", "jdbc:postgresql://localhost:5433/persons", "postgres", "postgres");
        return this.organizationService.create(organizationDetail);
    }

    /**
     * Update organization detail.
     *
     * @param id                the id
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
            value = "/{id}"
    )
    public PersonDetail update(
            @ApiParam(
                    name="id",
                    value = "Идентификатор сущности \"Person\"",
                    example = "1",
                    required = true
            )
            @PathVariable
                    Long id,
            @ApiParam(
                    name="organizationDetail",
                    value = "Данные для сущности \"Person\"",
                    example = "{\"name\": \"WCorp\"}",
                    required = true
            )
            @RequestBody
                    PersonDetail organizationDetail) throws CustomModelMapper.MappingException {

        log.info("Получили запрос:\nid - {}\norganization - {}", id, organizationDetail);

        Base.open("org.postgresql.Driver", "jdbc:postgresql://localhost:5433/persons", "postgres", "postgres");
        return this.organizationService.update(id, organizationDetail);
    }

    /**
     * Delete organization detail.
     *
     * @param id the id
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
            value = "/{id}"
    )
    public PersonDetail delete(
            @ApiParam(
                    name="id",
                    value = "Идентификатор сущности \"Person\"",
                    example = "1",
                    required = true
            )
            @PathVariable
                    Long id) {

        log.info("Получили запрос - {}", id);

        Base.open("org.postgresql.Driver", "jdbc:postgresql://localhost:5433/persons", "postgres", "postgres");
        return this.organizationService.delete(id);
    }

    /**
     * Find list.
     *
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
    public List<PersonDetail> find() throws CustomModelMapper.MappingException {

        log.info("Получили запросна выборку сущностей");

        Base.open("org.postgresql.Driver", "jdbc:postgresql://localhost:5433/persons", "postgres", "postgres");
        return this.organizationService.find();
    }
}
