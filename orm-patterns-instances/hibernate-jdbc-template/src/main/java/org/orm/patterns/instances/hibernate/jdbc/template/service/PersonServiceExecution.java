package org.orm.patterns.instances.hibernate.jdbc.template.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.orm.patterns.instances.commons.mapping.CustomModelMapper;
import org.orm.patterns.instances.commons.model.detail.PersonDetail;
import org.orm.patterns.instances.hibernate.jdbc.template.model.dao.PersonReporistory;
import org.orm.patterns.instances.hibernate.jdbc.template.model.entity.Person;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

    PersonReporistory personRepository;

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

        this.createByBatchExecution();

        return;
    }

    private void createByBatchExecution() {
        List<Person> persons = new ArrayList<>();
        for (int personNumber = 1; personNumber <= 10_000; personNumber++) {
            persons.add(
                    Person.builder()
                            .firstName("Person-" + personNumber)
                            .lastName("Personson-" + personNumber)
                            .age(12)
                            .build()
            );
        }

        for (int iteration = 1; iteration <= 10; iteration++) {
            this.personRepository.deleteAll();

            LocalDateTime startAt = LocalDateTime.now();
            this.personRepository.saveAll(persons);
            Duration time = Duration.between(startAt, LocalDateTime.now());

            log.info(
                    ANSI_PURPLE_BACKGROUND
                            + "The spent time - {} h {} min {} sec {} ns"
                            + ANSI_RESET,
                    time.toHours(),
                    time.toMinutes(),
                    time.toSeconds(),
                    time.getNano()
            );
        }
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
            log.error("Couldn't update the Person, because - {}", e.getMessage());
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
            log.error("Couldn't find any Person, because - {}", e.getMessage());
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
            log.error("Couldn't delete the Person, because - {}", e.getMessage());
        }
    }
}
