package org.restful.test.instances.db.embedded.test;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.restful.test.instances.commons.categories.IntegrationTestOnEmbedded;
import org.restful.test.instances.commons.categories.IntegrationTestOnReal;
import org.restful.test.instances.model.entity.Employ;
import org.restful.test.instances.model.entity.Organization;
import org.restful.test.instances.model.entity.Position;
import org.restful.test.instances.repository.EmployRepository;
import org.restful.test.instances.repository.OrganizationRepository;
import org.restful.test.instances.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;

/**
 * @project restful-test-instances
 * @created 03.04.2021 09:29
 * <p>
 * @author Alexander A. Kropotin
 */
@Category(IntegrationTestOnEmbedded.class)
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
@AutoConfigureTestDatabase
@AutoConfigureDataJpa
@Slf4j
@NoArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE
)
public class EmployRepositoryTest {

    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    PositionRepository positionRepository;

    @Autowired
    EmployRepository employRepository;

    Organization organizationOrigin = Organization.builder()
            .name("АО Ревизор")
            .build();

    Position positionOrigin = Position.builder()
            .name("Просто отличный парень")
            .code("CG1")
            .build();

    @Before
    public void beforeEachTest() {
        this.cleanDb();
        this.createOrganization();
        this.createPosition();
    }

    @After
    public void afterEachTest() {
        this.cleanDb();
    }

    @Test
    public void save_positive_whenAllIsCorrect_thenSuccessfullySaved() {
        Employ employOrigin = Employ.builder()
                .fullName("Василий Зайцев")
                .organizationUid(this.organizationOrigin.getUid())
                .positionUid(this.positionOrigin.getPositionUid())
                .build();
        log.info("Создали сущность employ - {}", employOrigin);

        this.employRepository.save(employOrigin);
        log.info("Сохранили сущность employ - {}", employOrigin);

        Optional<Employ> employSaved = this.employRepository.findById(employOrigin.getEmployUid());
        log.info("Получили сущность employ из бд - {}", employSaved);

        assertTrue(
                employSaved.isPresent(),
                String.format("Сущности employ по данному идентификатору (%s) не обнаружено", employOrigin.getEmployUid())
        );

        assertTrue(
                employSaved.get().equals(employOrigin),
                "Сущность в бд отличается от сущности, которую сохраняли"
        );
    }

    @Test(expected = TransactionSystemException.class)
    public void save_negative_whenNameIsBlank_thenFailureWithThrowException() {
        Employ employOrigin = Employ.builder()
                .fullName("     ")
                .organizationUid(this.organizationOrigin.getUid())
                .positionUid(this.positionOrigin.getPositionUid())
                .build();
        log.info("Создали сущность employ - {}", employOrigin);

        this.employRepository.save(employOrigin);
    }

    @Test(expected = TransactionSystemException.class)
    public void save_negative_whenNameIsNull_thenFailureWithThrowException() {
        Employ employOrigin = Employ.builder()
                .organizationUid(this.organizationOrigin.getUid())
                .positionUid(this.positionOrigin.getPositionUid())
                .build();
        log.info("Создали сущность employ - {}", employOrigin);

        this.employRepository.save(employOrigin);
    }

    @Test(expected = TransactionSystemException.class)
    public void save_negative_whenNameIsEmpty_thenFailureWithThrowException() {
        Employ employOrigin = Employ.builder()
                .fullName("")
                .organizationUid(this.organizationOrigin.getUid())
                .positionUid(this.positionOrigin.getPositionUid())
                .build();
        log.info("Создали сущность employ - {}", employOrigin);

        this.employRepository.save(employOrigin);
    }

    @Test(expected = TransactionSystemException.class)
    public void save_negative_whenNameIsLessThanRequiredLength_thenFailureWithThrowException() {
        Employ employOrigin = Employ.builder()
                .fullName("1234")
                .organizationUid(this.organizationOrigin.getUid())
                .positionUid(this.positionOrigin.getPositionUid())
                .build();
        log.info("Создали сущность employ - {}", employOrigin);

        this.employRepository.save(employOrigin);
    }

    @Test(expected = TransactionSystemException.class)
    public void save_negative_whenNameIsMoreThanRequiredLength_thenFailureWithThrowException() {
        Employ employOrigin = Employ.builder()
                .fullName(
                        IntStream.range(0, 101)
                                .mapToObj(number -> "+")
                                .collect(Collectors.joining())
                )
                .organizationUid(this.organizationOrigin.getUid())
                .positionUid(this.positionOrigin.getPositionUid())
                .build();
        log.info("Создали сущность employ - {}", employOrigin);

        this.employRepository.save(employOrigin);
    }

    @Test(expected = TransactionSystemException.class)
    public void save_negative_whenOrganizationUidIsNull_thenFailureWithThrowException() {
        Employ employOrigin = Employ.builder()
                .fullName("АО Ревизор")
                .positionUid(this.positionOrigin.getPositionUid())
                .build();
        log.info("Создали сущность employ - {}", employOrigin);

        this.employRepository.save(employOrigin);
    }

    @Test(expected = TransactionSystemException.class)
    public void save_negative_whenPositionUidIsNull_thenFailureWithThrowException() {
        Employ employOrigin = Employ.builder()
                .fullName("АО Ревизор")
                .organizationUid(this.organizationOrigin.getUid())
                .build();
        log.info("Создали сущность employ - {}", employOrigin);

        this.employRepository.save(employOrigin);
    }

    private void cleanDb() {
        log.info(
                "В таблицах:\n- employ было {} записей;\n- organization было {} записей;\n- position было {} записей;",
                this.employRepository.count(),
                this.organizationRepository.count(),
                this.positionRepository.count()
        );
        this.employRepository.deleteAll();
        this.organizationRepository.deleteAll();
        this.positionRepository.deleteAll();
        log.info(
                "В таблицах:\n- employ стало {} записей;\n- organization стало {} записей;\n- position стало {} записей;",
                this.employRepository.count(),
                this.organizationRepository.count(),
                this.positionRepository.count()
        );
    }

    private void createOrganization() {
        this.organizationRepository.save(this.organizationOrigin);
        assertTrue(this.organizationOrigin.getUid() != null, "Организация не сохранилась");
    }

    private void createPosition() {
        this.positionRepository.save(this.positionOrigin);
        assertTrue(this.positionOrigin.getPositionUid() != null, "Должность не сохранилась");
    }
}
