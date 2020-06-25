package org.restful.querying.instances.specification.builder.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.restful.querying.instances.specification.builder.model.entity.Item;
import org.restful.querying.instances.specification.builder.repository.ItemRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Root dir creator.
 */
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
@Component
public class SomeItemAutoCreator implements ApplicationListener<ApplicationReadyEvent> {

    ItemRepository someItemRepository;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {

        List<Item> someItems = new ArrayList<Item>(){{
            for (int i = 1; i <= 5; i++) {
                add(
                        Item.builder()
                                .uid(Long.valueOf(i))
                                .name("name_" + i)
                                .length(i*i)
                                .build()
                );
            }
        }};

        someItemRepository.saveAll(someItems);

        log.info("Stored the follows items:\n{}", someItems.toString());
    }
}
