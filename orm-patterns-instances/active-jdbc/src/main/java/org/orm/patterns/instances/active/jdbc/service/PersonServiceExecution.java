package org.orm.patterns.instances.active.jdbc.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.orm.patterns.instances.active.jdbc.model.detail.PersonDetail;
import org.orm.patterns.instances.active.jdbc.service.mapping.CustomModelMapper;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Collection;

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

        //delete one
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
                    .id(java.util.Optional.of(1L))
                    .firstName(java.util.Optional.of("Person"))
                    .lastName(java.util.Optional.of("Personson"))
                    .age(java.util.Optional.of(12))
                    .build();
            PersonDetail createPersonResponse = this.personService.create(createPersonRequest);
            log.warn("Receive the created Person data - {}", createPersonResponse);
        } catch (CustomModelMapper.MappingException e) {
            log.debug("Couldn't create the new Person, because - {}", e.getMessage());
        }
    }

    /**
     * Update execution.
     */
    private void updateExecution() {
        try {
            Long updatePersonIdRequest = 1L;
            PersonDetail updatePersonRequest = PersonDetail.builder()
                    .firstName(java.util.Optional.of("NoPerson"))
                    .lastName(java.util.Optional.of("NoPersonson"))
                    .age(java.util.Optional.of(21))
                    .build();
            PersonDetail updatePersonResponse = this.personService.update(updatePersonIdRequest, updatePersonRequest);
            log.warn("Receive the updated Person data - {}", updatePersonResponse);
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
            log.warn("Receive the collection of Persons data - {}", findPersonResponse);
        } catch (CustomModelMapper.MappingException e) {
            log.debug("Couldn't find any Person, because - {}", e.getMessage());
        }
    }

    /**
     * Delete execution.
     */
    private void deleteExecution() {
        try {
            Long deletePersonIdRequest = 1L;
            PersonDetail deletePersonResponse = this.personService.delete(deletePersonIdRequest);
            log.warn("Receive the deleted Person data - {}", deletePersonResponse);
        } catch (Exception e) {
            log.debug("Couldn't delete the Person, because - {}", e.getMessage());
        }
    }
}
