package org.restful.test.instances.service.test.service.mapping;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.restful.test.instances.model.detail.OrganizationDetail;
import org.restful.test.instances.model.entity.Organization;
import org.restful.test.instances.service.mapping.CustomModelMapper;
import org.restful.test.instances.service.mapping.OrganizationModelMapper;

import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * The type Organization model mapper test.
 *
 * @author Alexander A. Kropotin
 * @project restful -test-instances
 * @created 03.09.2020 10:39 <p>
 */
@RunWith(MockitoJUnitRunner.class)
@Slf4j
@NoArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE
)
public class OrganizationModelMapperTest {

    /**
     * The Object mapper.
     */
    @Mock
    ObjectMapper objectMapper;

    /**
     * The Organization model mapper.
     */
    @InjectMocks
    OrganizationModelMapper organizationModelMapper;

    /**
     * Before each test.
     *
     * @throws JsonMappingException the json mapping exception
     */
    @Before
    public void beforeEachTest() throws JsonMappingException {
        when(objectMapper.updateValue(any(Organization.class), any(OrganizationDetail.class)))
                .thenReturn(Organization.builder()
                        .uid(Long.valueOf(1))
                        .build()
                );
        when(objectMapper.updateValue(any(OrganizationDetail.class), any(Organization.class)))
                .thenReturn(OrganizationDetail.builder()
                        .uid(Optional.ofNullable(Long.valueOf(1)))
                        .build()
                );
    }

    /**
     * Map positive when source and destination is object and not null then successful mapped.
     *
     * @throws CustomModelMapper.MappingException     the mapping exception
     * @throws JsonMappingException the json mapping exception
     */
    @Test
    public void map_positive_whenSourceAndDestinationIsObjectAndNotNull_thenSuccessfulMapped()
            throws CustomModelMapper.MappingException, JsonMappingException {
        OrganizationDetail source = OrganizationDetail.builder()
                .uid(Optional.ofNullable(Long.valueOf(1)))
                .build();
        Organization destination = new Organization();
        log.info(
                "Created source and destination objects:\nsource - {}\ndestination - {}",
                source,
                destination
        );

        destination = this.organizationModelMapper.map(source, destination);
        log.info(
                "Mapped source into destination objects:\nsource - {}\ndestination - {}",
                source,
                destination
        );

        verify(objectMapper).updateValue(any(Organization.class), any(OrganizationDetail.class));
        assertTrue(
                destination.getUid().equals(source.getUid().orElse(null)),
                "Сущности разные - свойства не скопировались"
        );
    }
}
