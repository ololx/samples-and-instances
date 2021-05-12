package org.orm.patterns.instances.hibernate.jdbc.template.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.orm.patterns.instances.commons.mapping.CustomModelMapper;
import org.orm.patterns.instances.commons.model.detail.PersonDetail;
import org.orm.patterns.instances.commons.model.detail.PhoneDetail;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.orm.patterns.instances.commons.util.OutColorsUtils.ANSI_CYAN_BACKGROUND;
import static org.orm.patterns.instances.commons.util.OutColorsUtils.ANSI_RESET;

/**
 * The type Person service execution.
 */
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
@Component
public class PhoneServiceExecution implements ApplicationListener<ApplicationReadyEvent> {

    /**
     * The Person service.
     */
    PersonService personService;

    /**
     * The Phone service.
     */
    PhoneService phoneService;

    /**
     * On application event.
     *
     * @param event the event
     */
    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {

        //create new
        this.createExecution();
        this.findExecution();

        //update exists
        this.updateExecution();
        this.findExecution();

        //get all
        this.findExecution();

        //delete exists
        this.deleteExecution();
        this.findExecution();

        return;
    }

    /**
     * Create execution.
     */
    private void createExecution() {
        try {
            PersonDetail createPersonRequest = PersonDetail.builder()
                    .firstName(Optional.ofNullable("Person"))
                    .lastName(Optional.ofNullable("Personson"))
                    .age(Optional.ofNullable(16))
                    .build();
            PersonDetail createPersonResponse = this.personService.create(createPersonRequest);
            log.info(ANSI_CYAN_BACKGROUND + "Receive the created Person data - {}" + ANSI_RESET, createPersonResponse);

            PhoneDetail createPhoneRequest = PhoneDetail.builder()
                    .personId(Optional.ofNullable(createPersonResponse.getId().orElse(1L)))
                    .number(Optional.ofNullable("+7 (999) 999-9999"))
                    .build();
            PhoneDetail createPhoneResponse = this.phoneService.create(createPhoneRequest);
            log.info(ANSI_CYAN_BACKGROUND + "Receive the created Phone data - {}" + ANSI_RESET, createPhoneResponse);
        } catch (CustomModelMapper.MappingException e) {
            log.error("Couldn't create the new Person, because - {}", e.getMessage());
        }
    }

    /**
     * Update execution.
     */
    private void updateExecution() {
        try {
            PersonDetail createPersonRequest = PersonDetail.builder()
                    .firstName(Optional.ofNullable("Person"))
                    .lastName(Optional.ofNullable("Personson"))
                    .age(Optional.ofNullable(16))
                    .build();
            PersonDetail createPersonResponse = this.personService.create(createPersonRequest);
            log.info(ANSI_CYAN_BACKGROUND + "Receive the created Person data - {}" + ANSI_RESET, createPersonResponse);

            PhoneDetail createPhoneRequest = PhoneDetail.builder()
                    .personId(Optional.ofNullable(createPersonResponse.getId().orElse(1L)))
                    .number(Optional.ofNullable("+7 (999) 999-9999"))
                    .build();
            PhoneDetail createPhoneResponse = this.phoneService.create(createPhoneRequest);
            Long updatePhoneIdRequest = createPhoneResponse.getId().get();

            PhoneDetail updatePhoneRequest = PhoneDetail.builder()
                    .personId(Optional.ofNullable(createPersonResponse.getId().orElse(1L)))
                    .number(Optional.ofNullable("+7 (111) 111-1111"))
                    .build();
            PhoneDetail updatePhoneResponse = this.phoneService.update(updatePhoneIdRequest, updatePhoneRequest);
            log.info(ANSI_CYAN_BACKGROUND + "Receive the updated Phone data - {}" + ANSI_RESET, updatePhoneResponse);
        } catch (CustomModelMapper.MappingException e) {
            log.error("Couldn't create the new Phone, because - {}", e.getMessage());
        }
    }

    /**
     * Find execution.
     */
    private void findExecution() {
        try {
            Collection<PhoneDetail> findPhoneResponse = this.phoneService.find();
            log.info(
                    ANSI_CYAN_BACKGROUND + "Receive the collection of Phones data - {}" + ANSI_RESET,
                    findPhoneResponse
            );
        } catch (CustomModelMapper.MappingException e) {
            log.error("Couldn't find any Phone, because - {}", e.getMessage());
        }
    }

    /**
     * Delete execution.
     */
    private void deleteExecution() {
        try {
            Collection<PhoneDetail> findPhoneResponse = new ArrayList<>();
            if ((findPhoneResponse = this.phoneService.find()).isEmpty()) {
                PersonDetail createPersonRequest = PersonDetail.builder()
                        .firstName(Optional.ofNullable("Person"))
                        .lastName(Optional.ofNullable("Personson"))
                        .age(Optional.ofNullable(16))
                        .build();
                PersonDetail createPersonResponse = this.personService.create(createPersonRequest);
                log.info(ANSI_CYAN_BACKGROUND + "Receive the created Person data - {}" + ANSI_RESET, createPersonResponse);

                PhoneDetail createPhoneRequest = PhoneDetail.builder()
                        .personId(Optional.ofNullable(createPersonResponse.getId().orElse(1L)))
                        .number(Optional.ofNullable("+7 (999) 999-9999"))
                        .build();
                findPhoneResponse.add(this.phoneService.create(createPhoneRequest));
            }

            this.phoneService.find().stream()
                    .forEach(phone -> {
                        Long deletePhoneIdRequest = phone.getId().get();
                        PhoneDetail deletePhoneResponse = this.phoneService.delete(deletePhoneIdRequest);
                        log.info(
                                ANSI_CYAN_BACKGROUND + "Receive the deleted Phone data - {}" + ANSI_RESET,
                                deletePhoneResponse
                        );
                    });
        } catch (Exception e) {
            log.error("Couldn't delete the Phone, because - {}", e.getMessage());
        }
    }
}
