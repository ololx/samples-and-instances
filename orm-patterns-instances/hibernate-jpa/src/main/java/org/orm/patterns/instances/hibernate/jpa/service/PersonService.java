package org.orm.patterns.instances.hibernate.jpa.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.orm.patterns.instances.commons.mapping.CustomModelMapper;
import org.orm.patterns.instances.commons.model.detail.PersonDetail;
import org.orm.patterns.instances.hibernate.jpa.entity.Person;
import org.orm.patterns.instances.hibernate.jpa.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

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

    /*
     * The custom Person model mapper
     */
    CustomModelMapper personModelMapper;

    /*
     * The Person repository
     */
    PersonRepository personRepository;

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
        Person person = this.personModelMapper.map(createPersonRequest, new Person());
        log.info("Создали сущность - {}", person);

        this.personRepository.save(person);

        PersonDetail createPersonResponse = this.personModelMapper.map(
                person,
                new PersonDetail()
        );
        log.info("Возвращаем ответ - {}", createPersonResponse);

        return createPersonResponse;
    }

    /**
     * Update person detail.
     *
     * @param idPerson            the uid person
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

        Person person = this.personRepository.findById(idPerson).orElse(null);
        assertNotNull(
                person,
                String.format("Сущности с таким идентификатором - {} не существует", idPerson)
        );
        log.info("Получили сущность - {}", person);

        person = this.personModelMapper.map(updatePersonRequest, person);
        this.personRepository.save(person);

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

        Person person = this.personRepository.findById(idPerson).orElse(null);
        assertNotNull(
                person,
                String.format("Сущности с таким идентификатором - {} не существует", idPerson)
        );
        log.info("Получили сущность - {}", person);

        this.personRepository.delete(person);

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
        List<Person> persons = this.personRepository.findAll();
        List<PersonDetail> findPersonResponse = this.personModelMapper.map(
                persons,
                PersonDetail.class
        );
        log.info("Возвращаем ответ - {}", findPersonResponse);

        return findPersonResponse;
    }
}
