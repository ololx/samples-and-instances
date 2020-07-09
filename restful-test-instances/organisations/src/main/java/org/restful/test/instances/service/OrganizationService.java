package org.restful.test.instances.service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.restful.test.instances.model.detail.OrganizationDetail;
import org.restful.test.instances.model.entity.Organization;
import org.restful.test.instances.repository.OrganizationRepository;
import org.springframework.stereotype.Service;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;


/**
 * @project restful-test-instances
 * @created 2020-04-25 13:46
 * <p>
 * @author Alexander A. Kropotin
 */
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
@Service
public final class OrganizationService {

    OrganizationRepository organizationRepository;

    ObjectMapper objectMapper;

    public OrganizationDetail create(OrganizationDetail createOrganizationRequest) throws JsonMappingException {
        log.info("Получили запрос на создание сущности - {}", createOrganizationRequest);

        assertNotNull(createOrganizationRequest, "Организация не может быть null");
        Organization organization = this.objectMapper.updateValue(new Organization(), createOrganizationRequest);

        log.info("Создали сущность организации - {}", organization);

        organization = this.organizationRepository.save(organization);
        assertNotNull(organization.getId(), "Не получилось сохранить организацию");
        OrganizationDetail createOrganizationResponse = this.objectMapper.updateValue(
                new OrganizationDetail(),
                organization
        );

        log.info("Возвращаем ответ - {}", createOrganizationResponse);

        return createOrganizationResponse;
    }
}
