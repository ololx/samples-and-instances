package org.orm.patterns.instances.hibernate.jdbc.template.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.orm.patterns.instances.commons.mapping.CustomModelMapper;
import org.orm.patterns.instances.commons.model.detail.PhoneDetail;
import org.orm.patterns.instances.hibernate.jdbc.template.model.entity.Phone;
import org.orm.patterns.instances.hibernate.jdbc.template.model.entity.Person;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

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

    @PersistenceContext
    private EntityManager entityManager;

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
        Person person = this.entityManager.find(Person.class, createPhoneRequest.getPersonId().orElse(null));
        log.info("Получили сущность - {}", person);
        phone.setPerson(person);
        log.info("Создали сущность - {}", phone);

        this.entityManager.persist(phone);

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

        Phone phone = this.entityManager.find(Phone.class, idPhone);
        assertNotNull(
                phone,
                String.format("Сущности с таким идентификатором - {} не существует", idPhone)
        );
        log.info("Получили сущность - {}", phone);

        this.phoneModelMapper.map(updatePhoneRequest, phone);
        this.entityManager.persist(phone);

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
        List<Phone> phones = this.entityManager.createQuery("SELECT p FROM " + Phone.class.getSimpleName() + " p")
                .getResultList();
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
        Phone storedPhone = this.entityManager.find(Phone.class, idPhone);
        assertNotNull(
                storedPhone,
                String.format("Сущности с таким идентификатором - {} не существует", idPhone)
        );

        this.entityManager.remove(idPhone);

        assertTrue(
                storedPhone.getId() == null,
                String.format("Не получилось удалить сущность с идентификатором - {}", idPhone)
        );

        PhoneDetail deletePhoneResponse = PhoneDetail.builder()
                .id(Optional.ofNullable(idPhone))
                .build();
        log.info("Возвращаем ответ - {}", deletePhoneResponse);

        return deletePhoneResponse;
    }
}
