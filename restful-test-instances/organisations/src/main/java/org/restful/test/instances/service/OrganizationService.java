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
package org.restful.test.instances.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.restful.test.instances.model.detail.OrganizationDetail;
import org.restful.test.instances.model.entity.Organization;
import org.restful.test.instances.repository.OrganizationRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;

import static org.hibernate.validator.internal.util.Contracts.*;


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

    ModelMapper modelMapper;

    public OrganizationDetail create(OrganizationDetail organizationDetailRequest) {
        if (log.isInfoEnabled())
            log.info("Получили запрос на создание сущности - {}", organizationDetailRequest);

        assertNotNull(organizationDetailRequest, "Организация не может быть null");
        Organization organization = this.modelMapper.map(organizationDetailRequest, Organization.class);

        if (log.isInfoEnabled())
            log.info("Создали сущность организации - {}", organization);

        organization = this.organizationRepository.save(organization);
        assertNotNull(organization.getId(), "Не получилось сохранить организацию");
        OrganizationDetail organizationDetailResponse = this.modelMapper.map(organization, OrganizationDetail.class);

        if (log.isInfoEnabled())
            log.info("Возвращаем ответ - {}", organizationDetailResponse);

        return organizationDetailResponse;
    }
}
