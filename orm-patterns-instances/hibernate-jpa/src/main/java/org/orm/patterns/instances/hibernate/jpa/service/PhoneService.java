package org.orm.patterns.instances.hibernate.jpa.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.orm.patterns.instances.commons.mapping.CustomModelMapper;
import org.orm.patterns.instances.commons.model.detail.PhoneDetail;
import org.orm.patterns.instances.hibernate.jpa.model.entity.Person;
import org.orm.patterns.instances.hibernate.jpa.model.entity.Phone;
import org.orm.patterns.instances.hibernate.jpa.repository.PersonRepository;
import org.orm.patterns.instances.hibernate.jpa.repository.PhoneRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    /*
     * The Phone repository
     */
    PhoneRepository phoneRepository;

    /*
     * The Person repository
     */
    PersonRepository personRepository;

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
        Person person = this.personRepository.getOne(createPhoneRequest.getPersonId().orElse(null));
        log.info("Получили сущность - {}");
        phone.setPerson(person);
        log.info("Создали сущность - {}", phone);

        this.phoneRepository.save(phone);

        PhoneDetail createPhoneResponse = this.phoneModelMapper.map(
                phone,
                new PhoneDetail()
        );
        log.info("Возвращаем ответ - {}", createPhoneResponse);

        return createPhoneResponse;
    }

    /**
     * Update phone detail.
     *
     * @param updatePhoneRequest the create phone request
     * @return the phone detail
     * @throws CustomModelMapper.MappingException the mapping exception
     */
    public PhoneDetail update(PhoneDetail updatePhoneRequest)
            throws CustomModelMapper.MappingException {
        log.info("Получили запрос на создание сущности - {}", updatePhoneRequest);
        Phone phone = this.phoneModelMapper.map(updatePhoneRequest, new Phone());
        Person person = this.personRepository.getOne(updatePhoneRequest.getPersonId().orElse(null));
        log.info("Получили сущность - {}");
        phone.setPerson(person);
        log.info("Создали сущность - {}", phone);

        this.phoneRepository.save(phone);

        PhoneDetail createPhoneResponse = this.phoneModelMapper.map(
                phone,
                new PhoneDetail()
        );
        log.info("Возвращаем ответ - {}", createPhoneResponse);

        return createPhoneResponse;
    }

    /**
     * Update phone detail.
     *
     * @param idPhone            the uid phone
     * @param updatePhoneRequest the update phone request
     * @return the phone detail
     * @throws CustomModelMapper.MappingException the mapping exception
     */
    public PhoneDetail update(Long idPhone, PhoneDetail updatePhoneRequest)
            throws CustomModelMapper.MappingException {
        log.info(
                "Получили запрос на обновлении сущности - {}\n с идентификатором - {}",
                updatePhoneRequest,
                idPhone);

        Phone phone = this.phoneRepository.getOne(idPhone);
        assertNotNull(
                phone,
                String.format("Сущности с таким идентификатором - {} не существует", idPhone)
        );
        log.info("Получили сущность - {}", phone);

        phone = this.phoneModelMapper.map(updatePhoneRequest, phone);

        this.phoneRepository.save(phone);

        PhoneDetail updatePhoneResponse = this.phoneModelMapper.map(
                phone,
                new PhoneDetail()
        );
        log.info("Возвращаем ответ - {}", updatePhoneResponse);

        return updatePhoneResponse;
    }

    /**
     * Find list.
     *
     * @return the list
     * @throws CustomModelMapper.MappingException the mapping exception
     */
    public List<PhoneDetail> find() throws CustomModelMapper.MappingException {
        List<Phone> phones = this.phoneRepository.findAll();
        List<PhoneDetail> findPhoneResponse = this.phoneModelMapper.map(
                phones,
                PhoneDetail.class
        );
        log.info("Возвращаем ответ - {}", findPhoneResponse);

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
        log.info("Получили запрос на удаление сущности с идентификатором - {}", idPhone);

        Phone phone = this.phoneRepository.getOne(idPhone);
        assertNotNull(
                phone,
                String.format("Сущности с таким идентификатором - {} не существует", idPhone)
        );
        log.info("Получили сущность - {}", phone);

        this.phoneRepository.delete(phone);
        assertTrue(phone.getId() == null, "Не получилось удалить сущность");

        PhoneDetail deletePhoneResponse = PhoneDetail.builder()
                .id(Optional.ofNullable(idPhone))
                .build();
        log.info("Возвращаем ответ - {}", deletePhoneResponse);

        return deletePhoneResponse;
    }
}
