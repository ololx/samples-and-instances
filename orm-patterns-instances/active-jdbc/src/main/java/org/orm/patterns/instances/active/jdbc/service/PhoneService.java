package org.orm.patterns.instances.active.jdbc.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.orm.patterns.instances.active.jdbc.model.entity.Person;
import org.orm.patterns.instances.active.jdbc.model.entity.Phone;
import org.orm.patterns.instances.commons.mapping.CustomModelMapper;
import org.orm.patterns.instances.commons.model.detail.PhoneDetail;
import org.springframework.stereotype.Service;

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

        boolean isSaved = phone.insert();
        assertTrue(isSaved, "Не получилось сохранить сущность");

        PhoneDetail createPhoneResponse = this.phoneModelMapper.map(
                phone.toMap(),
                new PhoneDetail()
        );
        log.info("Возвращаем ответ - {}", createPhoneResponse);

        log.info("\u001B[35m" + "The Person data with Phone data - {}", Person.findAll());

        connectionWrapper.close();

        return createPhoneResponse;
    }
}
