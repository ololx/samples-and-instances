package org.orm.patterns.instances.active.jdbc.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.orm.patterns.instances.active.jdbc.model.entity.Phone;
import org.orm.patterns.instances.active.jdbc.model.entity.Phone;
import org.orm.patterns.instances.commons.mapping.CustomModelMapper;
import org.orm.patterns.instances.commons.model.detail.PersonDetail;
import org.orm.patterns.instances.commons.model.detail.PhoneDetail;
import org.orm.patterns.instances.commons.model.detail.PhoneDetail;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.orm.patterns.instances.commons.util.OutColorsUtils.ANSI_PURPLE_BACKGROUND;
import static org.orm.patterns.instances.commons.util.OutColorsUtils.ANSI_RESET;

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
     * @throws MappingException the mapping exception
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
     * Update phone detail.
     *
     * @param idPhone            the uid phone
     * @param updatePhoneRequest the update phone request
     * @return the phone detail
     * @throws MappingException the mapping exception
     */
    public PhoneDetail update(Long idPhone, PhoneDetail updatePhoneRequest)
            throws CustomModelMapper.MappingException {
        connectionWrapper.open();
        log.info(
                "Получили запрос на обновлении сущности - {}\n с идентификатором - {}",
                updatePhoneRequest,
                idPhone);

        Phone phone = Phone.findById(idPhone);
        assertNotNull(
                phone,
                String.format("Сущности с таким идентификатором - {} не существует", idPhone)
        );
        log.info("Получили сущность - {}", phone);

        phone.fromMap(
                this.phoneModelMapper.map(updatePhoneRequest, phone.toMap())
        );
        boolean isUpdated = phone.saveIt();
        assertTrue(isUpdated, "Не получилось обновить сущность");

        PhoneDetail updatePhoneResponse = this.phoneModelMapper.map(
                phone.toMap(),
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
     * @throws MappingException the mapping exception
     */
    public List<PhoneDetail> find() throws CustomModelMapper.MappingException {
        connectionWrapper.open();
        List<Phone> phones = Phone.findAll();
        List<PhoneDetail> findPhoneResponse = this.phoneModelMapper.map(
                phones.stream().map(p -> p.toMap()).collect(Collectors.toList()),
                PhoneDetail.class
        );
        log.info("Возвращаем ответ - {}", findPhoneResponse);
        connectionWrapper.close();

        return findPhoneResponse;
    }

    /**
     * Delete phone detail.
     *
     * @param idPhone the uid phone
     * @return the phone detail
     * @throws CustomModelMapper.MappingException the mapping exception
     */
    public PhoneDetail delete(Long idPhone) {
        connectionWrapper.open();
        log.info("Получили запрос на удаление сущности с идентификатором - {}", idPhone);

        Phone phone = Phone.findById(idPhone);
        assertNotNull(
                phone,
                String.format("Сущности с таким идентификатором - {} не существует", idPhone)
        );
        log.info("Получили сущность - {}", phone);

        boolean isDeleted = phone.delete();
        assertTrue(isDeleted, "Не получилось удалить сущность");

        PhoneDetail deletePhoneResponse = PhoneDetail.builder()
                .id(Optional.ofNullable(idPhone))
                .build();
        log.info("Возвращаем ответ - {}", deletePhoneResponse);
        connectionWrapper.close();

        return deletePhoneResponse;
    }
}
