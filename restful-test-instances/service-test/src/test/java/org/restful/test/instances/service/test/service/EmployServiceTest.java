package org.restful.test.instances.service.test.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.restful.test.instances.commons.categories.UnitTest;
import org.restful.test.instances.model.detail.EmployDetail;
import org.restful.test.instances.model.entity.Employ;
import org.restful.test.instances.repository.EmployRepository;
import org.restful.test.instances.service.EmployService;
import org.restful.test.instances.service.mapping.CustomModelMapper;
import org.restful.test.instances.service.mapping.SimpleModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;

import static org.hibernate.validator.internal.util.Contracts.*;

/**
 * @project restful-test-instances
 * @created 15.05.2021 10:53
 * <p>
 * @author Alexander A. Kropotin
 */
@Category(UnitTest.class)
@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
@Slf4j
@NoArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE
)
public class EmployServiceTest {

    @MockBean
    EmployRepository employRepository;

    @MockBean
    SimpleModelMapper simpleModelMapper;

    @InjectMocks
    @Autowired
    EmployService employService;

    @Before
    public void beforeEachTest() throws CustomModelMapper.MappingException {
        when(simpleModelMapper.map(any(EmployDetail.class), any(Employ.class)))
                .thenReturn(Employ.builder().build());
        when(simpleModelMapper.map(any(Employ.class), any(EmployDetail.class)))
                .thenReturn(
                        EmployDetail.builder()
                                .employUid(Optional.ofNullable(1L))
                                .build()
                );
        when(employRepository.save(any(Employ.class)))
                .thenReturn(
                        Employ.builder()
                                .employUid(1l)
                                .build()
                );
    }

    @Test
    public void create_positive_whenRequestIsValid_thenSuccessfulCreateNewEmploy() throws CustomModelMapper.MappingException {
        EmployDetail createEmployRequest = EmployDetail.builder()
                .organizationUid(Optional.ofNullable(1L))
                .positionUid(Optional.ofNullable((short) 1))
                .fullName(Optional.ofNullable("Василий Зайцев"))
                .build();
        log.info("Создали ДТО сотрудника - {}", createEmployRequest);

        EmployDetail createEmployResponse = this.employService.create(createEmployRequest);

        verify(simpleModelMapper).map(any(EmployDetail.class), any(Employ.class));
        verify(simpleModelMapper).map(any(Employ.class), any(EmployDetail.class));
        verify(employRepository).save(any(Employ.class));

        assertNotNull(createEmployResponse, "Что-то не так: сущность не сохранилась");
        assertTrue(createEmployResponse.getEmployUid().isPresent(), "Что-то не так: идентификатор пустой");
    }
}
