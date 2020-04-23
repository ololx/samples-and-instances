/**
 * Copyright 2020 the project restful-test-instances authors
 * and the original author or authors annotated by {@author}
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.restful.test.instances.db.local.test;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.restful.test.instances.db.local.test.model.entity.Organization;
import org.restful.test.instances.db.local.test.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.hibernate.validator.internal.util.Contracts.assertTrue;

/**
 * @project restful-test-instances
 * @created 2020-04-18 12:19
 * <p>
 * @author Alexander A. Kropotin
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
@Slf4j
@NoArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE
)
public class OrganizationRepositoryTests {

    @Autowired
    OrganizationRepository organizationRepository;

    @Before
    public void beforeEachTest() {

        if (log.isInfoEnabled())
            log.info(
                    "В таблице было {} записей",
                    organizationRepository.count()
            );

        organizationRepository.deleteAll();

        if (log.isInfoEnabled())
            log.info(
                    "В таблице стало {} записей",
                    organizationRepository.count()
            );
    }

    @Test
    public void saveTest() {
        Organization organizationOrigin = Organization.builder()
                .address("Перкопская 15-а")
                .build();

        if (log.isInfoEnabled())
            log.info("Создали сущность `Организация` - {}", organizationOrigin);

        organizationRepository.save(organizationOrigin);

        if (log.isInfoEnabled())
            log.info("Сохранили сущность `Организация` - {}", organizationOrigin);

        assertNotNull(organizationOrigin.getId(), "Сущность не сохранилась");
    }

    @Test
    public void updateTest() {
        String newInn = "1231231239";
        Organization organizationOrigin = Organization.builder()
                .inn("1231231234")
                .address("Перкопская 15-а")
                .build();

        if (log.isInfoEnabled())
            log.info("Создали сущность `Организация` - {}", organizationOrigin);

        organizationRepository.save(organizationOrigin);

        if (log.isInfoEnabled())
            log.info("Сохранили сущность `Организация` - {}", organizationOrigin);

        Organization organizationFromDb = organizationRepository.findById(organizationOrigin.getId()).orElse(null);
        assertNotNull(organizationFromDb, "Организации с таким идентификатором не существует");
        organizationFromDb.setInn(newInn);
        organizationRepository.save(organizationFromDb);

        if (log.isInfoEnabled())
            log.info("Обновили сущность `Организация` - {}", organizationFromDb);

        Organization organizationNewInnFromDb = organizationRepository.findById(organizationOrigin.getId()).orElse(null);
        assertNotNull(organizationNewInnFromDb, "Организации с таким идентификатором не существует");

        assertNotNull(organizationNewInnFromDb.getInn().equals(newInn), "ИНН не обновился");
    }

    @Test
    public void findAllByInnIsNotNullTest() {
        Organization organizationWithInnIsNUll = Organization.builder()
                .address("Перкопская 15-а")
                .build();
        Organization organizationWithInnIsNotNUll = Organization.builder()
                .inn("1231231234")
                .address("Перкопская 10")
                .build();

        List<Organization> organizationsOrigin = List.of(organizationWithInnIsNUll, organizationWithInnIsNotNUll);

        if (log.isInfoEnabled())
            log.info("Создали сущности `Организация`\n{}", organizationsOrigin);

        organizationRepository.saveAll(organizationsOrigin);

        if (log.isInfoEnabled())
            log.info("Сохранили сущности `Организация`\n{}", organizationsOrigin);

        List<Organization> organizationsFromDb = organizationRepository.findAllByInnIsNotNull();

        if (log.isInfoEnabled())
            log.info("Получили сущности `Организация` из БД\n{}", organizationsFromDb);

        assertTrue(!organizationsFromDb.isEmpty(), "Почему-то организации не найдены вообще");
        organizationsFromDb.forEach(eachOrganization -> {
            assertNotNull(eachOrganization.getInn(), "Нашлась огранизация без ИНН");
        });
    }
}
