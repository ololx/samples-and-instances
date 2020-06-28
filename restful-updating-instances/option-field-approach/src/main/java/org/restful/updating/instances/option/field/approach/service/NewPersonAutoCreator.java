package org.restful.updating.instances.option.field.approach.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.restful.updating.instances.option.field.approach.model.entity.Person;
import org.restful.updating.instances.option.field.approach.repository.PersonRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @project restful-updating-instances
 * @created 28.08.2020 07:54
 * <p>
 * @author Alexander A. Kropotin
 */
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
@Component
public class NewPersonAutoCreator implements ApplicationListener<ApplicationReadyEvent> {

    PersonRepository peopleRepository;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {

        List<Person> newPeoples = new ArrayList<Person>(){{
            for (int i = 1; i <= 5; i++) {
                add(
                        Person.builder()
                                .uid(Long.valueOf(i))
                                .firstName("Boris_" + i)
                                .lastName("Bronson_" + i)
                                .age(i)
                                .build()
                );
            }
        }};

        peopleRepository.saveAll(newPeoples);

        log.info("Stored the follows peoples:\n{}", newPeoples.toString());
    }
}
