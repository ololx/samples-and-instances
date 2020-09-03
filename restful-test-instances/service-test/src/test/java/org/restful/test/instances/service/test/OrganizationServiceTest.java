package org.restful.test.instances.service.test;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.restful.test.instances.model.detail.OrganizationDetail;
import org.restful.test.instances.model.entity.Organization;
import org.restful.test.instances.repository.OrganizationRepository;
import org.restful.test.instances.service.OrganizationService;
import org.restful.test.instances.service.mapping.CustomModelMapper;
import org.restful.test.instances.service.mapping.OrganizationModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * The type Organization service test.
 *
 * @author Alexander A. Kropotin
 * @project restful -test-instances
 * @created 2020 -04-25 14:09 <p>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
@Slf4j
@NoArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE
)
public class OrganizationServiceTest {

    /**
     * The Organization repository.
     */
    @MockBean
    OrganizationRepository organizationRepository;

    /**
     * The Organization model mapper.
     */
    @MockBean
    OrganizationModelMapper organizationModelMapper;

    /**
     * The Organization service.
     */
    @InjectMocks
    @Autowired
    OrganizationService organizationService;

    /**
     * Before each test.
     *
     * @throws CustomModelMapper.MappingException the mapping exception
     */
    @Before
    public void beforeEachTest() throws CustomModelMapper.MappingException {
        when(organizationRepository.save(any(Organization.class)))
                .thenReturn(Organization.builder()
                        .uid(Long.valueOf(1))
                        .build()
                );
        when(organizationRepository.findById(Long.valueOf(1)))
                .thenReturn(Optional.ofNullable(Organization.builder()
                        .uid(Long.valueOf(1))
                        .build())
                );
        when(organizationRepository.findById(Long.valueOf(2)))
                .thenReturn(Optional.ofNullable(null));
        when(organizationModelMapper.map(any(OrganizationDetail.class), any(Organization.class)))
                .thenReturn(Organization.builder()
                        .uid(Long.valueOf(1))
                        .build()
                );
        when(organizationModelMapper.map(any(Organization.class), any(OrganizationDetail.class)))
                .thenReturn(OrganizationDetail.builder()
                        .uid(Optional.ofNullable(Long.valueOf(1)))
                        .build()
                );
    }

    /**
     * Create positive when request is valid then successful created.
     *
     * @throws CustomModelMapper.MappingException the mapping exception
     */
    @Test
    public void create_positive_whenRequestIsValid_thenSuccessfulCreated() throws CustomModelMapper.MappingException {
        OrganizationDetail createOrganizationRequest = OrganizationDetail.builder()
                .name(Optional.of("WCorp"))
                .build();

        log.info("Создали ДТО `Организация` - {}", createOrganizationRequest);

        OrganizationDetail organizationResponse = this.organizationService.create(createOrganizationRequest);

        verify(organizationModelMapper).map(any(OrganizationDetail.class), any(Organization.class));
        verify(organizationModelMapper).map(any(Organization.class), any(OrganizationDetail.class));
        verify(organizationRepository).save(any(Organization.class));
        assertNotNull(organizationResponse, "Что-то пошло не так");
        assertNotNull(organizationResponse.getUid().orElse(null), "Иденьтификатор null");
        assertTrue(
                organizationResponse.getUid().orElse(null).equals(Long.valueOf(1)),
                "Иденьтификаторы разные"
        );
    }

    /**
     * Update positive request is valid then successful updated.
     *
     * @throws CustomModelMapper.MappingException the mapping exception
     */
    @Test
    public void update_positive_RequestIsValid_thenSuccessfulUpdated() throws CustomModelMapper.MappingException {
        Long uidOrganization = 1L;
        OrganizationDetail updateOrganizationRequest = OrganizationDetail.builder()
                .name(Optional.of("WCorp"))
                .build();

        log.info("Создали ДТО `Организация` - {}", updateOrganizationRequest);

        OrganizationDetail organizationResponse = this.organizationService.update(
                uidOrganization,
                updateOrganizationRequest
        );

        verify(organizationModelMapper).map(any(OrganizationDetail.class), any(Organization.class));
        verify(organizationModelMapper).map(any(Organization.class), any(OrganizationDetail.class));
        verify(organizationRepository).findById(uidOrganization);
        verify(organizationRepository).save(any(Organization.class));
        assertNotNull(organizationResponse, "Что-то пошло не так");
        assertNotNull(organizationResponse.getUid().orElse(null), "Иденьтификатор null");
        assertTrue(
                organizationResponse.getUid().orElse(null).equals(Long.valueOf(1)),
                "Иденьтификаторы разные"
        );
    }

    /**
     * Update negative when entity with specified uid is not exists then failure with throw exception.
     *
     * @throws CustomModelMapper.MappingException the mapping exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void update_negative_whenEntityWithSpecifiedUidIsNotExists_thenFailureWithThrowException()
            throws CustomModelMapper.MappingException {
        Long uidOrganization = 2L;
        OrganizationDetail updateOrganizationRequest = OrganizationDetail.builder()
                .name(Optional.of("WCorp"))
                .build();

        log.info("Создали ДТО `Организация` - {}", updateOrganizationRequest);

        OrganizationDetail organizationResponse = this.organizationService.update(
                uidOrganization,
                updateOrganizationRequest
        );

        verify(organizationRepository).findById(uidOrganization);
    }
}
