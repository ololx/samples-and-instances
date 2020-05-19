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
import org.restful.test.instances.model.entity.Organization;
import org.restful.test.instances.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hibernate.validator.internal.util.Contracts.*;

/**
 * @project restful-test-instances
 * @created 2020-04-18 12:19
 * <p>
 * @author Alexander A. Kropotin
 */
@ActiveProfiles("test")
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
    public void save_positive_whenNameNotNull_thenSuccessfulSaved() {
        Organization organizationOrigin = Organization.builder()
                .name("ПИМ-195")
                .build();
        log.info("Создали сущность `Организация` - {}", organizationOrigin);

        organizationRepository.save(organizationOrigin);
        log.info("Сохранили сущность `Организация` - {}", organizationOrigin);

        Organization organizationSaved = this.organizationRepository
                .findById(organizationOrigin.getId())
                .orElse(null);
        log.info("Получили из бд сущность `Организация` - {}", organizationSaved);

        assertNotNull(organizationSaved, "Сущности в бд не обнаружено");
        assertTrue(
                organizationOrigin.equals(organizationSaved),
                "Сущность в бд не такая же как сущность, которую сохраняли"
        );
    }

    @Test(expected = ConstraintViolationException.class)
    public void save_negative_nameNull_thenFailureWithThrowException() {
        Organization organizationOrigin = new Organization();
        log.info("Создали сущность `Организация` - {}", organizationOrigin);

        organizationRepository.save(organizationOrigin);
    }

    @Test(expected = ConstraintViolationException.class)
    public void save_negative_whenNameEmpty_thenFailureWithThrowException() {
        Organization organizationOrigin = Organization.builder()
                .name("")
                .build();
        log.info("Создали сущность `Организация` - {}", organizationOrigin);

        organizationRepository.save(organizationOrigin);
    }

    @Test(expected = ConstraintViolationException.class)
    public void save_negative_whenNameBlank_thenFailureWithThrowException() {
        Organization organizationOrigin = Organization.builder()
                .name(" ")
                .build();
        log.info("Создали сущность `Организация` - {}", organizationOrigin);

        organizationRepository.save(organizationOrigin);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void save_negative_whenNameDuplicate_thenFailureWithThrowException() {
        Organization organizationOrigin = Organization.builder()
                .name("ПИМ-195")
                .build();
        log.info("Создали сущность `Организация` - {}", organizationOrigin);
        organizationRepository.save(organizationOrigin);
        log.info("Сохранили сущность `Организация` - {}", organizationOrigin);

        Organization organizationOriginDuplicate = Organization.builder()
                .name("ПИМ-195")
                .build();
        log.info("Создали сущность-дубликат `Организация` - {}", organizationOriginDuplicate);

        organizationRepository.save(organizationOriginDuplicate);
    }

    @Test(expected = ConstraintViolationException.class)
    public void save_negative_whenNameLong_thenFailureWithThrowException() {
        Organization organizationOrigin = Organization.builder()
                .name(Stream.iterate(1,  i -> i++)
                        .limit(21)
                        .map(eachIterator -> "*")
                        .collect(Collectors.joining())
                )
                .build();
        log.info("Создали сущность `Организация` - {}", organizationOrigin);
        organizationRepository.save(organizationOrigin);
    }

    @Test(expected = ConstraintViolationException.class)
    public void save_negative_whenInnLong_thenFailureWithThrowException() {
        Organization organizationOrigin = Organization.builder()
                .name("ПИМ-195")
                .inn(Stream.iterate(1,  i -> i++)
                        .limit(11)
                        .map(eachIterator -> "*")
                        .collect(Collectors.joining())
                )
                .build();
        log.info("Создали сущность `Организация` - {}", organizationOrigin);
        organizationRepository.save(organizationOrigin);
    }

    @Test(expected = ConstraintViolationException.class)
    public void save_negative_whenKppLong_thenFailureWithThrowException() {
        Organization organizationOrigin = Organization.builder()
                .name("ПИМ-195")
                .kpp(Stream.iterate(1,  i -> i++)
                        .limit(9)
                        .map(eachIterator -> "*")
                        .collect(Collectors.joining())
                )
                .build();
        log.info("Создали сущность `Организация` - {}", organizationOrigin);
        organizationRepository.save(organizationOrigin);
    }

    @Test
    public void update_positive_Name_thenSuccessfulUpdated() {
        Organization organizationOrigin = Organization.builder()
                .name("ПИМ-190")
                .build();
        log.info("Создали сущность `Организация` - {}", organizationOrigin);
        organizationRepository.save(organizationOrigin);
        log.info("Сохранили сущность `Организация` - {}", organizationOrigin);

        Organization organizationSaved = this.organizationRepository
                .findById(organizationOrigin.getId())
                .orElse(null);
        organizationSaved.setName("ПИМ-195");
        this.organizationRepository.save(organizationSaved);
        log.info("Обновили сущность `Организация` - {}", organizationSaved);

        Organization organizationUpdated = this.organizationRepository
                .findById(organizationOrigin.getId())
                .orElse(null);
        log.info("Получили из бд сущность `Организация` - {}", organizationUpdated);

        assertNotNull(organizationUpdated, "Сущности в бд не обнаружено");
        assertTrue(organizationSaved.equals(organizationUpdated), "Сохранилось не то, что мы сохраняли");
        assertTrue(
                !organizationOrigin.getName().equals(organizationUpdated.getName()),
                "Наменование организации не было обновлено"
        );
    }

    @Test
    public void findById_positive_whenIdNotNullAndOrganizationExists_thenSuccessfulReturnOrganization() {
        Organization organizationOrigin = Organization.builder()
                .name("ПИМ-195")
                .build();
        log.info("Создали сущность `Организация` - {}", organizationOrigin);
        organizationRepository.save(organizationOrigin);
        log.info("Сохранили сущность `Организация` - {}", organizationOrigin);

        Organization organizationSaved = this.organizationRepository
                .findById(organizationOrigin.getId())
                .orElse(null);
        log.info("Получили из бд сущность `Организация` - {}", organizationSaved);

        assertNotNull(organizationSaved, "Сущности в бд не обнаружено");
    }

    @Test
    public void findById_positive_whenIdNotNullAndOrganizationNotExists_thenSuccessfulReturnNull() {
        Organization organizationSaved = this.organizationRepository
                .findById(Long.valueOf(1))
                .orElse(null);
        log.info("Получили из бд сущность `Организация` - {}", organizationSaved);

        assertTrue(organizationSaved == null, "Получили сущность которой не должно было быть");
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void findById_negative_whenIdNull_thenFailureThrowException() {
        Organization organizationOrigin = Organization.builder()
                .name("ПИМ-195")
                .build();
        log.info("Создали сущность `Организация` - {}", organizationOrigin);
        organizationRepository.save(organizationOrigin);
        log.info("Сохранили сущность `Организация` - {}", organizationOrigin);

        Organization organizationSaved = this.organizationRepository
                .findById(null)
                .orElse(null);
        log.info("Получили из бд сущность `Организация` - {}", organizationSaved);

        assertNotNull(organizationSaved, "Сущности в бд не обнаружено");
    }

    @Test
    public void findAll_positive_whenOrganizationsExists_thenSuccessfulReturnOrganizations() {
        int organizationsCount = 5;
        List<Organization> organisationsOrigin = new ArrayList<>(){{
            for (int i = 1; i <= organizationsCount; i++) {
                add(
                        Organization.builder()
                                .name(String.format("ПИМ-19%s", i))
                                .build()
                );
            }
        }};
        log.info("Создали сущности `Организация` - {}", organisationsOrigin);
        organizationRepository.saveAll(organisationsOrigin);
        log.info("Сохранили сущности `Организация` - {}", organisationsOrigin);

        List<Organization> organizationsSaved = this.organizationRepository.findAll();
        log.info("Получили из бд сущности `Организация` - {}", organizationsSaved);

        assertNotNull(organizationsSaved, "Сущности в бд не обнаружено");
        assertTrue(
                organizationsSaved.size() == organizationsCount,
                "получили меньше сущностей, чем сохранили"
        );
        organisationsOrigin.forEach(eachOrganization -> {
            assertTrue(
                    organizationsSaved.contains(eachOrganization),
                    "Сущность в бд не такая же как сущность, которую сохраняли"
            );
        });
    }

    @Test
    public void findAll_positive_whenOrganizationsNotExists_thenSuccessfulReturnNull() {
        List<Organization> organizationsSaved = this.organizationRepository.findAll();
        log.info("Получили из бд сущности `Организация` - {}", organizationsSaved);

        assertTrue(organizationsSaved != null, "Получили сущности которых не должно было быть");
    }

    @Test
    public void deleteById_positive_whenIdNotNull_thenSuccessfulDeleteOrganization() {
        Organization organizationOrigin = Organization.builder()
                .name("ПИМ-195")
                .build();
        log.info("Создали сущность `Организация` - {}", organizationOrigin);
        organizationRepository.save(organizationOrigin);
        log.info("Сохранили сущность `Организация` - {}", organizationOrigin);

        this.organizationRepository.deleteById(organizationOrigin.getId());
        log.info("Удалили из бд сущность `Организация` - {}", organizationOrigin);

        Organization organizationDeleted = this.organizationRepository
                .findById(organizationOrigin.getId())
                .orElse(null);
        log.info("Получили из бд сущность `Организация` - {}", organizationDeleted);

        assertTrue(organizationDeleted == null, "Сущность из бд не удалена");
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void deleteById_positive_whenIdNotNullButNotExists_thenFailureThrowException() {
        Organization organizationOrigin = Organization.builder()
                .name("ПИМ-195")
                .build();
        log.info("Создали сущность `Организация` - {}", organizationOrigin);
        organizationRepository.save(organizationOrigin);
        log.info("Сохранили сущность `Организация` - {}", organizationOrigin);

        this.organizationRepository.deleteById(Long.valueOf(101));
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void deleteById_negative_whenIdNull_thenFailureThrowException() {
        Organization organizationOrigin = Organization.builder()
                .name("ПИМ-195")
                .build();
        log.info("Создали сущность `Организация` - {}", organizationOrigin);
        organizationRepository.save(organizationOrigin);
        log.info("Сохранили сущность `Организация` - {}", organizationOrigin);

        this.organizationRepository.deleteById(null);
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
