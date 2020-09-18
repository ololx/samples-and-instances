package org.restful.test.instances.controller.test.controller;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.restful.test.instances.controller.OrganizationController;
import org.restful.test.instances.model.detail.ExceptionDetail;
import org.restful.test.instances.model.detail.OrganizationDetail;
import org.restful.test.instances.model.entity.Organization;
import org.restful.test.instances.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The type Organization controller integration tests.
 *
 * @author Alexander A. Kropotin
 * @project restful -test-instances
 * @created 16.09.2020 14:45 <p>
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
@NoArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE
)
public class OrganizationControllerIntegrationTests {

    /**
     * The Port.
     */
    @LocalServerPort
    int port;

    /**
     * The Rest template.
     */
    @Autowired
    TestRestTemplate restTemplate;

    /**
     * The Organization controller.
     */
    @Autowired
    OrganizationController organizationController;

    @Autowired
    OrganizationRepository organizationRepository;

    /**
     * Create positive when request is valid then successful created.
     */
    @Test
    public void create_positive_whenRequestIsValid_thenSuccessfulCreated() {
        OrganizationDetail expectedOrganizationRequest = OrganizationDetail.builder()
                .name(Optional.of("WCorp"))
                .build();
        OrganizationDetail expectedOrganizationResponse = OrganizationDetail.builder()
                .uid(Optional.of(1L))
                .name(Optional.of("WCorp"))
                .build();

        ResponseEntity<OrganizationDetail> response = this.restTemplate.exchange(
                String.format("http://localhost:%d/organizations", port),
                HttpMethod.POST,
                new HttpEntity<OrganizationDetail>(expectedOrganizationRequest),
                OrganizationDetail.class
        );
        OrganizationDetail actualOrganizationResponse = response.getBody();

        assertTrue(response.getStatusCode().equals(HttpStatus.CREATED), "Код статуса не 201 - что-то пошло не так!");
        assertNotNull(actualOrganizationResponse, "Что-то пошло не так");
        assertTrue(
                actualOrganizationResponse.equals(expectedOrganizationResponse),
                "Ожидаемый и фактический результаты отличаются - что-то пошло не так!"
        );
    }

    /**
     * Create negative when name is null then failure with throw exception.
     */
    @Test
    public void create_negative_whenNameIsNull_thenFailureWithThrowException() {
        OrganizationDetail expectedOrganizationRequest = OrganizationDetail.builder()
                .build();
        ResponseEntity<ExceptionDetail> response = this.restTemplate.exchange(
                String.format("http://localhost:%d/organizations", port),
                HttpMethod.POST,
                new HttpEntity<OrganizationDetail>(expectedOrganizationRequest),
                ExceptionDetail.class
        );
        ExceptionDetail actualOrganizationResponse = response.getBody();

        assertTrue(response.getStatusCode().equals(HttpStatus.BAD_REQUEST), "Код статуса не 400 - что-то пошло не так!");
        assertNotNull(actualOrganizationResponse, "Что-то пошло не так");
        assertTrue(
                actualOrganizationResponse.getMessage().contains("Наименование организации должно быть задано"),
                "Ожидаемый и фактический результаты отличаются - что-то пошло не так!"
        );
    }

    @Test
    public void update_positive_whenRequestIsValid_thenSuccessfulUpdated() {
        Long expectedOrganizationUidRequest = 1L;
        OrganizationDetail expectedOrganizationRequest = OrganizationDetail.builder()
                .uid(Optional.of(expectedOrganizationUidRequest))
                .name(Optional.of("WCorp"))
                .build();
        OrganizationDetail expectedOrganizationResponse = OrganizationDetail.builder()
                .uid(Optional.of(1L))
                .name(Optional.of("WCorp"))
                .build();
        Organization storedOrganization = Organization.builder()
                .uid(1L)
                .name("CCorp")
                .build();
        this.organizationRepository.save(storedOrganization);

        ResponseEntity<OrganizationDetail> response = this.restTemplate.exchange(
                String.format("http://localhost:%d/organizations/{uid}", port),
                HttpMethod.PATCH,
                new HttpEntity<OrganizationDetail>(expectedOrganizationRequest),
                OrganizationDetail.class,
                new HashMap<String, Long>(){{
                    put("uid", expectedOrganizationUidRequest);
                }}
        );
        OrganizationDetail actualOrganizationResponse = response.getBody();

        assertTrue(response.getStatusCode().equals(HttpStatus.OK), "Код статуса не 200 - что-то пошло не так!");
        assertNotNull(actualOrganizationResponse, "Что-то пошло не так");
        assertTrue(
                actualOrganizationResponse.equals(expectedOrganizationResponse),
                "Ожидаемый и фактический результаты отличаются - что-то пошло не так!"
        );
    }
}
