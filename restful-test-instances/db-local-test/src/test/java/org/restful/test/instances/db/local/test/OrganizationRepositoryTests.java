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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.restful.test.instances.db.local.test.model.entity.Organization;
import org.restful.test.instances.db.local.test.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
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
        this.cleanDb();
    }

    @After
    public void afterEachTest() {
        this.cleanDb();
    }

    @Test
    public void savePositiveTest() {
        Organization organizationOrigin = Organization.builder()
                .name("ПИМ-195")
                .build();

        if (log.isInfoEnabled())
            log.info("Создали сущность `Организация` - {}", organizationOrigin);

        organizationRepository.save(organizationOrigin);

        if (log.isInfoEnabled())
            log.info("Сохранили сущность `Организация` - {}", organizationOrigin);

        assertNotNull(organizationOrigin.getId(), "Сущность не сохранилась");

        Organization organisationSaved = this.organizationRepository
                .findById(organizationOrigin.getId())
                .orElse(null);

        assertNotNull(organisationSaved, "Сущности в бд не обнаружено");
        assertTrue(
                organizationOrigin.equals(organisationSaved),
                "Сущность в бд не такая же как сущность, которую сохраняли"
        );
    }

    @Test(expected = ConstraintViolationException.class)
    public void saveNullNameNegativeTest() {
        Organization organizationOrigin = new Organization();

        if (log.isInfoEnabled())
            log.info("Создали сущность `Организация` - {}", organizationOrigin);

        organizationRepository.save(organizationOrigin);

        if (log.isInfoEnabled())
            log.info("Сохранили сущность `Организация` - {}", organizationOrigin);
    }

    @Test(expected = ConstraintViolationException.class)
    public void saveEmptyNameNegativeTest() {
        Organization organizationOrigin = Organization.builder()
                .name("")
                .build();

        if (log.isInfoEnabled())
            log.info("Создали сущность `Организация` - {}", organizationOrigin);

        organizationRepository.save(organizationOrigin);

        if (log.isInfoEnabled())
            log.info("Сохранили сущность `Организация` - {}", organizationOrigin);
    }

    @Test(expected = ConstraintViolationException.class)
    public void saveBlankNameNegativeTest() {
        Organization organizationOrigin = Organization.builder()
                .name("      ")
                .build();

        if (log.isInfoEnabled())
            log.info("Создали сущность `Организация` - {}", organizationOrigin);

        organizationRepository.save(organizationOrigin);

        if (log.isInfoEnabled())
            log.info("Сохранили сущность `Организация` - {}", organizationOrigin);
    }

    @Test
    public void updateNamePositiveTest() {
        Organization organizationOrigin = Organization.builder()
                .name("ПИМ-190")
                .build();

        if (log.isInfoEnabled())
            log.info("Создали сущность `Организация` - {}", organizationOrigin);

        organizationRepository.save(organizationOrigin);

        if (log.isInfoEnabled())
            log.info("Сохранили сущность `Организация` - {}", organizationOrigin);

        assertNotNull(organizationOrigin.getId(), "Сущность не была сохранена");

        Organization organisationSaved = this.organizationRepository
                .findById(organizationOrigin.getId())
                .orElse(null);

        assertNotNull(organisationSaved, "Сущности в бд не обнаружено");

        organisationSaved.setName("ПИМ-195");
        this.organizationRepository.save(organisationSaved);

        if (log.isInfoEnabled())
            log.info("Обновили сущность `Организация` - {}", organisationSaved);

        Organization organisationUpdated = this.organizationRepository
                .findById(organizationOrigin.getId())
                .orElse(null);

        assertNotNull(organisationUpdated, "Сущности в бд не обнаружено");
        assertTrue(organisationSaved.equals(organisationUpdated), "Сохранилось не то, что мы сохраняли");
        assertTrue(
                !organizationOrigin.getName().equals(organisationUpdated.getName()),
                "Наменование организации не было обновлено"
        );
    }

    private void cleanDb() {

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
}
