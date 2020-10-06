package org.orm.patterns.instances.hibernate.jpa.service;

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

import static org.orm.patterns.instances.commons.util.OutColorsUtils.ANSI_PURPLE_BACKGROUND;
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
public class PersonServiceExecution implements ApplicationListener<ApplicationReadyEvent> {

    /**
     * The Person service.
     */
    PersonService personService;

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
                    .age(Optional.ofNullable(12))
                    .build();
            PersonDetail createPersonResponse = this.personService.create(createPersonRequest);
            log.info(ANSI_PURPLE_BACKGROUND + "Receive the created Person data - {}" + ANSI_RESET, createPersonResponse);
        } catch (CustomModelMapper.MappingException e) {
            log.debug("Couldn't create the new Person, because - {}", e.getMessage());
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
                    .age(Optional.ofNullable(12))
                    .build();
            PersonDetail createPersonResponse = this.personService.create(createPersonRequest);
            Long updatePersonIdRequest = createPersonResponse.getId().get();

            PersonDetail updatePersonRequest = PersonDetail.builder()
                    .firstName(Optional.ofNullable("NoPerson"))
                    .lastName(Optional.ofNullable("NoPersonson"))
                    .age(Optional.ofNullable(21))
                    .build();
            PersonDetail updatePersonResponse = this.personService.update(updatePersonIdRequest, updatePersonRequest);
            log.info(ANSI_PURPLE_BACKGROUND + "Receive the updated Person data - {}" + ANSI_RESET, updatePersonResponse);
        } catch (CustomModelMapper.MappingException e) {
            log.debug("Couldn't update the Person, because - {}", e.getMessage());
        }
    }

    /**
     * Find execution.
     */
    private void findExecution() {
        try {
            Collection<PersonDetail> findPersonResponse = this.personService.find();
            log.info(ANSI_PURPLE_BACKGROUND + "Receive the collection of Persons data - {}" + ANSI_RESET, findPersonResponse);
        } catch (CustomModelMapper.MappingException e) {
            log.debug("Couldn't find any Person, because - {}", e.getMessage());
        }
    }

    /**
     * Delete execution.
     */
    private void deleteExecution() {
        try {
            Collection<PersonDetail> findPersonResponse = new ArrayList<>();
            if ((findPersonResponse = this.personService.find()).isEmpty()) {
                PersonDetail createPersonRequest = PersonDetail.builder()
                        .firstName(Optional.ofNullable("Person"))
                        .lastName(Optional.ofNullable("Personson"))
                        .age(Optional.ofNullable(12))
                        .build();
                findPersonResponse.add(this.personService.create(createPersonRequest));
            }

            this.personService.find().stream()
                    .forEach(person -> {
                        Long deletePersonIdRequest = person.getId().get();
                        PersonDetail deletePersonResponse = this.personService.delete(deletePersonIdRequest);
                        log.info(ANSI_PURPLE_BACKGROUND + "Receive the deleted Person data - {}" + ANSI_RESET, deletePersonResponse);
                    });
        } catch (Exception e) {
            log.debug("Couldn't delete the Person, because - {}", e.getMessage());
        }
    }
}
