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
        Base.open("org.postgresql.Driver", "jdbc:postgresql://localhost:5433/persons", "postgres", "postgres");
        assertNotNull(createPersonRequest, "Организация не может быть null");
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

        Base.close();

        return createPersonResponse;
    }

    /**
     * Update person detail.
     *
     * @param uidPerson           the uid person
     * @param updatePersonRequest the update person request
     * @return the person detail
     * @throws CustomModelMapper.MappingException the mapping exception
     */
    public PersonDetail update(Long uidPerson, PersonDetail updatePersonRequest)
            throws CustomModelMapper.MappingException {
        log.info(
                "Получили запрос на обновлении сущности - {}\n с идентификатором - {}",
                updatePersonRequest,
                uidPerson);

        Person person = Person.findById(uidPerson);
        assertNotNull(
                person,
                String.format("Сущности с таким идентификатором - {} не существует", uidPerson)
        );
        log.info("Получили сущность - {}", person);

        person = this.personModelMapper.map(updatePersonRequest, person);
        boolean isUpdated = person.save();
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
     * @param uidPerson the uid person
     * @return the person detail
     * @throws CustomModelMapper.MappingException the mapping exception
     */
    public PersonDetail delete(Long uidPerson) throws CustomModelMapper.MappingException {
        log.info("Получили запрос на удаление сущности с идентификатором - {}", uidPerson);

        Person person = Person.findById(uidPerson);
        assertNotNull(
                person,
                String.format("Сущности с таким идентификатором - {} не существует", uidPerson)
        );
        log.info("Получили сущность - {}", person);

        boolean isDeleted = person.delete();
        assertTrue(isDeleted, "Не получилось удалить сущность");

        PersonDetail deletePersonResponse = new PersonDetail();
        log.info("Возвращаем ответ - {}", deletePersonResponse);

        return deletePersonResponse;
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
     * @throws CustomModelMapper.MappingException the mapping exception
     */
    public List<PersonDetail> find(List<Long> uid,
                                   List<String> name,
                                   List<String> inn,
                                   List<String> kpp,
                                   List<String> address) throws CustomModelMapper.MappingException {
        log.info(
                "Получили запрос на выборку сущностей:\nuid - {}\nname - {}\ninn - {}\nkpp - {}\naddress - {}",
                uid,
                name,
                inn,
                kpp,
                address
                );

        return Collections.emptyList();
    }
}
