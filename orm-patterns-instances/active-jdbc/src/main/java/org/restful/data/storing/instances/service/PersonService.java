package org.restful.data.storing.instances.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.javalite.activejdbc.Base;
import org.restful.data.storing.instances.model.detail.PersonDetail;
import org.restful.data.storing.instances.model.entity.Person;
import org.restful.data.storing.instances.service.mapping.CustomModelMapper;
import org.restful.data.storing.instances.service.mapping.PersonModelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.hibernate.validator.internal.util.Contracts.assertTrue;

/**
 * The type Person service.
 */
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
@Service
public class PersonService {

    PersonModelMapper personModelMapper;

    /**
     * Create person detail.
     *
     * @param createPersonRequest the create person request
     * @return the person detail
     * @throws CustomModelMapper.MappingException the mapping exception
     */
    public PersonDetail create(PersonDetail createPersonRequest)
            throws CustomModelMapper.MappingException {
        log.info("Получили запрос на создание сущности - {}", createPersonRequest);
        Person person = new Person().fromMap(
                this.personModelMapper.map(createPersonRequest, new Person().toMap())
        );
        log.info("Создали сущность - {}", person);

        boolean isSaved = person.insert();
        assertTrue(isSaved, "Не получилось сохранить сущность");

        PersonDetail createPersonResponse = this.personModelMapper.map(
                person.toMap(),
                new PersonDetail()
        );
        log.info("Возвращаем ответ - {}", createPersonResponse);

        return createPersonResponse;
    }

    /**
     * Update person detail.
     *
     * @param idPerson           the uid person
     * @param updatePersonRequest the update person request
     * @return the person detail
     * @throws CustomModelMapper.MappingException the mapping exception
     */
    public PersonDetail update(Long idPerson, PersonDetail updatePersonRequest)
            throws CustomModelMapper.MappingException {
        log.info(
                "Получили запрос на обновлении сущности - {}\n с идентификатором - {}",
                updatePersonRequest,
                idPerson);

        Person person = Person.findById(idPerson);
        assertNotNull(
                person,
                String.format("Сущности с таким идентификатором - {} не существует", idPerson)
        );
        log.info("Получили сущность - {}", person);

        person.fromMap(
                this.personModelMapper.map(updatePersonRequest, person.toMap())
        );
        boolean isUpdated = person.saveIt();
        assertTrue(isUpdated, "Не получилось обновить сущность");

        PersonDetail updatePersonResponse = this.personModelMapper.map(
                person,
                new PersonDetail()
        );
        log.info("Возвращаем ответ - {}", updatePersonResponse);

        return updatePersonResponse;
    }

    /**
     * Delete person detail.
     *
     * @param idPerson the uid person
     * @return the person detail
     * @throws CustomModelMapper.MappingException the mapping exception
     */
    public PersonDetail delete(Long idPerson) {
        log.info("Получили запрос на удаление сущности с идентификатором - {}", idPerson);

        Person person = Person.findById(idPerson);
        assertNotNull(
                person,
                String.format("Сущности с таким идентификатором - {} не существует", idPerson)
        );
        log.info("Получили сущность - {}", person);

        boolean isDeleted = person.delete();
        assertTrue(isDeleted, "Не получилось удалить сущность");

        PersonDetail deletePersonResponse = PersonDetail.builder()
                .id(Optional.ofNullable(idPerson))
                .build();
        log.info("Возвращаем ответ - {}", deletePersonResponse);

        return deletePersonResponse;
    }

    /**
     * Find list.
     *
     * @return the list
     * @throws CustomModelMapper.MappingException the mapping exception
     */
    public List<PersonDetail> find() throws CustomModelMapper.MappingException {

        List<Person> persons = Person.findAll();
        List<PersonDetail> findPersonResponse = this.personModelMapper.map(
                persons.stream().map(p -> p.toMap()).collect(Collectors.toList()),
                PersonDetail.class
        );
        log.info("Возвращаем ответ - {}", findPersonResponse);

        return findPersonResponse;
    }
}
