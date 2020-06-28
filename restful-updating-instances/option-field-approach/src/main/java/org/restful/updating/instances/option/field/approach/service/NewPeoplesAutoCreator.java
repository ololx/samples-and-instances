package org.restful.updating.instances.option.field.approach.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.restful.updating.instances.option.field.approach.model.entity.People;
import org.restful.updating.instances.option.field.approach.repository.PeopleRepository;
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
public class NewPeoplesAutoCreator implements ApplicationListener<ApplicationReadyEvent> {

    PeopleRepository peopleRepository;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {

        List<People> newPeoples = new ArrayList<People>(){{
            for (int i = 1; i <= 5; i++) {
                add(
                        People.builder()
                                .uid(Long.valueOf(i))
                                .firstName("John_" + i)
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
