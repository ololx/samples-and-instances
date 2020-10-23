package org.orm.patterns.instances.active.jdbc.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.orm.patterns.instances.active.jdbc.model.entity.Person;
import org.orm.patterns.instances.active.jdbc.model.entity.Phone;
import org.orm.patterns.instances.commons.mapping.CustomModelMapper;
import org.orm.patterns.instances.commons.model.detail.PersonDetail;
import org.orm.patterns.instances.commons.model.detail.PhoneDetail;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.hibernate.validator.internal.util.Contracts.assertTrue;

/**
 * The type Phone service.
 */
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
@Service
public class PhoneService {

    /*
     * The custom Phone model mapper
     */
    CustomModelMapper phoneModelMapper;

    /**
     * The Connection wrapper.
     */
    ConnectionWrapper connectionWrapper;

    /**
     * Create phone detail.
     *
     * @param createPhoneRequest the create phone request
     * @return the phone detail
     * @throws CustomModelMapper.MappingException the mapping exception
     */
    public PhoneDetail create(PhoneDetail createPhoneRequest)
            throws CustomModelMapper.MappingException {
        connectionWrapper.open();
        log.info("Получили запрос на создание сущности - {}", createPhoneRequest);
        Phone phone = new Phone().fromMap(
                this.phoneModelMapper.map(createPhoneRequest, new Phone().toMap())
        );
        log.info("Создали сущность - {}", phone);

        boolean isSaved = phone.saveIt();
        assertTrue(isSaved, "Не получилось сохранить сущность");

        PhoneDetail createPhoneResponse = this.phoneModelMapper.map(
                phone.toMap(),
                new PhoneDetail()
        );
        log.info("Возвращаем ответ - {}", createPhoneResponse);

        connectionWrapper.close();

        return createPhoneResponse;
    }

    /**
     * Update person detail.
     *
     * @param idPhone            the uid person
     * @param updatePhoneRequest the update person request
     * @return the person detail
     * @throws CustomModelMapper.MappingException the mapping exception
     */
    public PhoneDetail update(Long idPhone, PhoneDetail updatePhoneRequest)
            throws CustomModelMapper.MappingException {
        connectionWrapper.open();
        log.info(
                "Получили запрос на обновлении сущности - {}\n с идентификатором - {}",
                updatePhoneRequest,
                idPhone);

        Phone person = Phone.findById(idPhone);
        assertNotNull(
                person,
                String.format("Сущности с таким идентификатором - {} не существует", idPhone)
        );
        log.info("Получили сущность - {}", person);

        person.fromMap(
                this.phoneModelMapper.map(updatePhoneRequest, person.toMap())
        );
        boolean isUpdated = person.saveIt();
        assertTrue(isUpdated, "Не получилось обновить сущность");

        PhoneDetail updatePhoneResponse = this.phoneModelMapper.map(
                person.toMap(),
                new PhoneDetail()
        );
        log.info("Возвращаем ответ - {}", updatePhoneResponse);

        connectionWrapper.close();

        return updatePhoneResponse;
    }

    /**
     * Find list.
     *
     * @return the list
     * @throws CustomModelMapper.MappingException the mapping exception
     */
    public List<PhoneDetail> find() throws CustomModelMapper.MappingException {
        connectionWrapper.open();
        List<Phone> phones = Person.findAll();
        List<PhoneDetail> findPhoneResponse = this.phoneModelMapper.map(
                phones.stream().map(p -> p.toMap()).collect(Collectors.toList()),
                PhoneDetail.class
        );
        log.info("Возвращаем ответ - {}", findPhoneResponse);

        connectionWrapper.close();

        return findPhoneResponse;
    }
}
