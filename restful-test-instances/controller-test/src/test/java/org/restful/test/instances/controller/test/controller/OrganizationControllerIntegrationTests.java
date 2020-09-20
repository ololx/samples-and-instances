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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.hibernate.validator.internal.util.Contracts.assertTrue;

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

    /**
     * The Organization repository.
     */
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

    /**
     * Update positive when request is valid then successful updated.
     */
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
                .uid(expectedOrganizationUidRequest)
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

    /**
     * Delete positive when request is valid then successful deleted.
     */
    @Test
    public void delete_positive_whenRequestIsValid_thenSuccessfulDeleted() {
        Long expectedOrganizationUidRequest = 1L;
        OrganizationDetail expectedOrganizationResponse = OrganizationDetail.builder()
                .build();
        Organization storedOrganization = Organization.builder()
                .uid(1L)
                .name("CCorp")
                .build();
        this.organizationRepository.save(storedOrganization);

        ResponseEntity<OrganizationDetail> response = this.restTemplate.exchange(
                String.format("http://localhost:%d/organizations/{uid}", port),
                HttpMethod.DELETE,
                null,
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

    /**
     * Find positive when request is valid then successful found.
     */
    @Test
    public void find_positive_whenRequestIsValid_thenSuccessfulFound() {
        List<Long> expectedUidRequest = Collections.singletonList(1L);
        List<String> expectedNameRequest = Collections.singletonList("WCorp");
        List<String> expectedInnRequest = Collections.singletonList("01");
        List<String> expectedKppRequest = Collections.singletonList("01");
        List<String> expectedAddressRequest = Collections.singletonList(".ell road");
        List<OrganizationDetail> expectedOrganizationResponse = Collections.singletonList(
                OrganizationDetail.builder()
                        .uid(Optional.of(1L))
                        .name(Optional.of("CCorp"))
                        .inn(Optional.of("01"))
                        .kpp(Optional.of("01"))
                        .address(Optional.of(".ell road"))
                        .build()
        );

        List<Organization> storedOrganizations = Arrays.asList(
                Organization.builder()
                        .uid(1L)
                        .name("CCorp")
                        .inn("01")
                        .kpp("01")
                        .address(".ell road")
                        .build(),
                Organization.builder()
                        .uid(2L)
                        .name("CCorp")
                        .inn("02")
                        .kpp("02")
                        .address("noname str")
                        .build()
        );
        this.organizationRepository.saveAll(storedOrganizations);

        ResponseEntity<List<OrganizationDetail>> response = this.restTemplate.exchange(
                UriComponentsBuilder.fromHttpUrl(String.format("http://localhost:%d/organizations", port))
                        .queryParam("uid", expectedUidRequest.stream().map(u -> String.valueOf(u)).toArray(String[]::new))
                        .queryParam("name", expectedNameRequest.stream().toArray(String[]::new))
                        .queryParam("inn", expectedInnRequest.stream().toArray(String[]::new))
                        .queryParam("kpp", expectedKppRequest.stream().toArray(String[]::new))
                        .queryParam("address", expectedAddressRequest.stream().toArray(String[]::new))
                        .build()
                        .encode()
                        .toUri(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<OrganizationDetail>>() {}
        );
        List<OrganizationDetail> actualOrganizationResponse = response.getBody();
        log.info(String.valueOf(actualOrganizationResponse));
        assertTrue(response.getStatusCode().equals(HttpStatus.OK), "Код статуса не 200 - что-то пошло не так!");
        assertNotNull(actualOrganizationResponse, "Что-то пошло не так");
        assertTrue(
                actualOrganizationResponse.equals(expectedOrganizationResponse),
                "Ожидаемый и фактический результаты отличаются - что-то пошло не так!"
        );
    }

    /**
     * Find positive when all request params is null then successful found all.
     */
    @Test
    public void find_positive_whenAllRequestParamsIsNull_thenSuccessfulFoundAll() {
        List<Long> expectedUidRequest = null;
        List<String> expectedNameRequest = null;
        List<String> expectedInnRequest = null;
        List<String> expectedKppRequest = null;
        List<String> expectedAddressRequest = null;
        List<OrganizationDetail> expectedOrganizationResponse = Arrays.asList(
                OrganizationDetail.builder()
                        .uid(Optional.of(1L))
                        .name(Optional.of("WCorp"))
                        .inn(Optional.of("01"))
                        .kpp(Optional.of("01"))
                        .address(Optional.of(".ell road"))
                        .build(),
                OrganizationDetail.builder()
                        .uid(Optional.of(2L))
                        .name(Optional.of("CCorp"))
                        .inn(Optional.of("02"))
                        .kpp(Optional.of("02"))
                        .address(Optional.of("noname str"))
                        .build()
        );

        List<Organization> storedOrganizations = Arrays.asList(
                Organization.builder()
                        .uid(1L)
                        .name("WCorp")
                        .inn("01")
                        .kpp("01")
                        .address(".ell road")
                        .build(),
                Organization.builder()
                        .uid(2L)
                        .name("CCorp")
                        .inn("02")
                        .kpp("02")
                        .address("noname str")
                        .build()
        );
        this.organizationRepository.saveAll(storedOrganizations);

        ResponseEntity<List<OrganizationDetail>> response = this.restTemplate.exchange(
                UriComponentsBuilder.fromHttpUrl(String.format("http://localhost:%d/organizations", port))
                        .build()
                        .encode()
                        .toUri(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<OrganizationDetail>>() {}
        );
        List<OrganizationDetail> actualOrganizationResponse = response.getBody();
        log.info(String.valueOf(actualOrganizationResponse));
        assertTrue(response.getStatusCode().equals(HttpStatus.OK), "Код статуса не 200 - что-то пошло не так!");
        assertNotNull(actualOrganizationResponse, "Что-то пошло не так");
        assertTrue(
                actualOrganizationResponse.equals(expectedOrganizationResponse),
                "Ожидаемый и фактический результаты отличаются - что-то пошло не так!"
        );
    }
}
