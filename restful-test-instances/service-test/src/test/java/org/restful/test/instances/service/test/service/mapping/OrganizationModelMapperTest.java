package org.restful.test.instances.service.test.service.mapping;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.restful.test.instances.commons.categories.UnitTest;
import org.restful.test.instances.model.detail.OrganizationDetail;
import org.restful.test.instances.model.entity.Organization;
import org.restful.test.instances.service.mapping.CustomModelMapper;
import org.restful.test.instances.service.mapping.SimpleModelMapper;

import java.util.Collections;
import java.util.List;
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
@Category(UnitTest.class)
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
    SimpleModelMapper organizationModelMapper;

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
    public void map_positive_whenMapDetailIntoEntityAndBoothIsNotNull_thenSuccessfulMapped()
            throws CustomModelMapper.MappingException, JsonMappingException {
        OrganizationDetail source = OrganizationDetail.builder()
                .uid(Optional.ofNullable(Long.valueOf(1)))
                .build();
        Organization destination = new Organization();
        log.info(
                "Создали сущность источника и результата копирования:\nистоник - {}\nрезультат - {}",
                source,
                destination
        );

        destination = this.organizationModelMapper.map(source, destination);
        log.info(
                "Скопировали свойства из источника:\nистоник - {}\nрезультат - {}",
                source,
                destination
        );

        verify(objectMapper).updateValue(any(Organization.class), any(OrganizationDetail.class));
        assertTrue(
                destination.getUid().equals(source.getUid().orElse(null)),
                "Сущности разные - свойства не скопировались"
        );
    }

    /**
     * Map positive when source and destination is object and not null then successful mapped.
     *
     * @throws CustomModelMapper.MappingException     the mapping exception
     * @throws JsonMappingException the json mapping exception
     */
    @Test
    public void map_positive_whenMapEntityIntoDetailAndBoothIsNotNull_thenSuccessfulMapped()
            throws CustomModelMapper.MappingException, JsonMappingException {
        Organization source = Organization.builder()
                .uid(Long.valueOf(1))
                .build();
        OrganizationDetail destination = new OrganizationDetail();
        log.info(
                "Создали сущность источника и результата копирования:\nистоник - {}\nрезультат - {}",
                source,
                destination
        );

        destination = this.organizationModelMapper.map(source, destination);
        log.info(
                "Скопировали свойства из источника:\nистоник - {}\nрезультат - {}",
                source,
                destination
        );

        verify(objectMapper).updateValue(any(OrganizationDetail.class), any(Organization.class));
        assertTrue(
                destination.getUid().orElse(null).equals(source.getUid()),
                "Сущности разные - свойства не скопировались"
        );
    }

    /**
     * Map positive when map entity collection into detail collection and booth is not null then successful mapped.
     *
     * @throws CustomModelMapper.MappingException     the mapping exception
     * @throws JsonMappingException the json mapping exception
     */
    @Test
    public void map_positive_whenMapEntityCollectionIntoDetailCollectionAndBoothIsNotNull_thenSuccessfulMapped()
            throws CustomModelMapper.MappingException, JsonMappingException {
        List<Organization> sources = Collections.singletonList(
                Organization.builder()
                        .uid(Long.valueOf(1))
                        .build()
        );
        List<OrganizationDetail> destinations = Collections.EMPTY_LIST;
        log.info(
                "Создали сущности источника и результата копирования:\nистоник - {}\nрезультат - {}",
                sources,
                destinations
        );

        destinations = this.organizationModelMapper.map(sources, OrganizationDetail.class);
        log.info(
                "Скопировали свойства из источника:\nистоник - {}\nрезультат - {}",
                sources,
                destinations
        );

        verify(objectMapper).updateValue(any(OrganizationDetail.class), any(Organization.class));
        assertTrue(
                destinations.size() == sources.size(),
                "Количества сущностей разные - свойства не скопировались"
        );
    }

    /**
     * Map negative when map entity collection into detail collection and type class is null then failure throws exception.
     *
     * @throws CustomModelMapper.MappingException the mapping exception
     */
    @Test(expected = CustomModelMapper.MappingException.class)
    public void map_negative_whenMapEntityCollectionIntoDetailCollectionAndTypeClassISNull_thenFailureThrowsException()
            throws CustomModelMapper.MappingException {
        List<Organization> sources = Collections.singletonList(
                Organization.builder()
                        .uid(Long.valueOf(1))
                        .build()
        );
        List<OrganizationDetail> destinations = Collections.EMPTY_LIST;
        log.info(
                "Создали сущности источника и результата копирования:\nистоник - {}\nрезультат - {}",
                sources,
                destinations
        );

        this.organizationModelMapper.map(sources, null);
    }
}
