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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.hibernate.validator.internal.util.Contracts.*;

/**
 * @project restful-test-instances
 * @created 2020-04-25 14:09
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
public class OrganizationServiceTest {

    @MockBean
    OrganizationRepository organizationRepository;

    @InjectMocks
    @Autowired
    OrganizationService organizationService;

    @Before
    public void beforeEachTest() {
        when(organizationRepository.save(any(Organization.class)))
                .thenReturn(Organization.builder()
                        .id(Long.valueOf(1))
                        .build()
                );
    }

    @Test
    public void createPositiveTest() {
        OrganizationDetail organizationRequest = OrganizationDetail.builder()
                .name("ПИМ-195")
                .build();

        if (log.isInfoEnabled())
            log.info("Создали ДТО `Организация` - {}", organizationRequest);

        OrganizationDetail organizationResponse = this.organizationService.create(organizationRequest);
        verify(organizationRepository).save(any(Organization.class));
        assertNotNull(organizationResponse, "Что-то пошло не так");
        assertTrue(organizationResponse.getId().equals(Long.valueOf(1)), "Иденьтификаторы разные");
    }
}
