package org.restful.test.instances.service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.restful.test.instances.model.detail.OrganizationDetail;
import org.restful.test.instances.model.entity.Organization;
import org.restful.test.instances.repository.OrganizationRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

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
        assertNotNull(organization.getUid(), "Не получилось сохранить организацию");

        OrganizationDetail createOrganizationResponse = this.objectMapper.updateValue(
                new OrganizationDetail(),
                organization
        );
        log.info("Возвращаем ответ - {}", createOrganizationResponse);

        return createOrganizationResponse;
    }

    public OrganizationDetail update(Long uidOrganization, OrganizationDetail updateOrganizationRequest)
            throws JsonMappingException {
        log.info(
                "Получили запрос на обновлении сущности - {}\n с идентификатором - {}",
                updateOrganizationRequest,
                uidOrganization);

        Organization organization = this.organizationRepository.findById(uidOrganization).orElse(null);
        assertNotNull(
                organization,
                String.format("Организации с таким идентификатором - {} не существует", uidOrganization)
        );
        log.info("Получили сущность организации - {}", organization);

        objectMapper.updateValue(organization, updateOrganizationRequest);
        organizationRepository.save(organization);

        OrganizationDetail updateOrganizationResponse = this.objectMapper.updateValue(
                new OrganizationDetail(),
                organization
        );
        log.info("Возвращаем ответ - {}", updateOrganizationResponse);

        return updateOrganizationResponse;
    }

    public OrganizationDetail delete(Long uidOrganization)
            throws JsonMappingException {
        log.info("Получили запрос на удаление сущности с идентификатором - {}", uidOrganization);

        Organization organization = this.organizationRepository.findById(uidOrganization).orElse(null);
        assertNotNull(
                organization,
                String.format("Организации с таким идентификатором - {} не существует", uidOrganization)
        );
        log.info("Получили сущность организации - {}", organization);

        organizationRepository.delete(organization);

        OrganizationDetail deleteOrganizationResponse = this.objectMapper.updateValue(
                new OrganizationDetail(),
                organization
        );
        log.info("Возвращаем ответ - {}", deleteOrganizationResponse);

        return deleteOrganizationResponse;
    }
}
