package org.orm.patterns.instances.hibernate.jpa.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.orm.patterns.instances.commons.mapping.CustomModelMapper;
import org.orm.patterns.instances.commons.model.detail.PhoneDetail;
import org.orm.patterns.instances.hibernate.jpa.model.entity.Phone;
import org.orm.patterns.instances.hibernate.jpa.repository.PhoneRepository;
import org.springframework.stereotype.Service;

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

    /*
     * The Phone repository
     */
    PhoneRepository phoneRepository;

    /**
     * Create phone detail.
     *
     * @param createPhoneRequest the create phone request
     * @return the phone detail
     * @throws CustomModelMapper.MappingException the mapping exception
     */
    public PhoneDetail create(PhoneDetail createPhoneRequest)
            throws CustomModelMapper.MappingException {
        log.info("Получили запрос на создание сущности - {}", createPhoneRequest);
        Phone phone = this.phoneModelMapper.map(createPhoneRequest, new Phone());
        log.info("Создали сущность - {}", phone);

        this.phoneRepository.save(phone);

        PhoneDetail createPhoneResponse = this.phoneModelMapper.map(
                phone,
                new PhoneDetail()
        );
        log.info("Возвращаем ответ - {}", createPhoneResponse);

        return createPhoneResponse;
    }
}
