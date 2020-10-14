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
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

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
     * The Connection wrapper.
     */
    ConnectionWrapper connectionWrapper;

    /**
     * On application event.
     *
     * @param event the event
     */
    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {

        //create new
        this.createExecution();

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
                    .age(Optional.ofNullable(12))
                    .build();
            PersonDetail createPersonResponse = this.personService.create(createPersonRequest);
            log.info(ANSI_CYAN_BACKGROUND + "Receive the created Person data - {}" + ANSI_RESET, createPersonResponse);

            PhoneDetail createPhoneRequest = PhoneDetail.builder()
                    .personId(Optional.ofNullable(createPersonResponse.getId().orElse(1L)))
                    .number(Optional.ofNullable("+7 (999) 999-9999"))
                    .build();
            PhoneDetail createPhoneResponse = this.phoneService.create(createPhoneRequest);
            log.info(ANSI_CYAN_BACKGROUND + "Receive the created Phone data - {}" + ANSI_RESET, createPhoneResponse);

            connectionWrapper.open();
            Person.findAll().forEach(p -> {
                log.info(ANSI_CYAN_BACKGROUND + "Receive the created PP data - {}" + ANSI_RESET, p.getAll(Phone.class));
            });
            connectionWrapper.close();

        } catch (CustomModelMapper.MappingException e) {
            log.debug("Couldn't create the new Person, because - {}", e.getMessage());
        }
    }
}
