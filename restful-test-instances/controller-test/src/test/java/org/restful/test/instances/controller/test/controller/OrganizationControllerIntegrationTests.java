package org.restful.test.instances.controller.test.controller;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
     * The Organization repository.
     */
    @Autowired
    OrganizationRepository organizationRepository;

    /**
     * Before each test.
     */
    @Before
    public void beforeEachTest() {
        this.cleanDb();
    }

    /**
     * After each test.
     */
    @After
    public void afterEachTest() {
        this.cleanDb();
    }

    /**
     * Create positive when request is valid then successful created.
     */
    @Test
    public void create_positive_whenRequestIsValid_thenSuccessfulCreated() {
        OrganizationDetail expectedOrganizationRequest = OrganizationDetail.builder()
                .name(Optional.ofNullable("WCorp"))
                .build();
        OrganizationDetail expectedOrganizationResponse = OrganizationDetail.builder()
                .uid(Optional.ofNullable(1L))
                .name(Optional.ofNullable("WCorp"))
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
        Organization storedOrganization = Organization.builder()
                .name("CCorp")
                .build();
        this.organizationRepository.save(storedOrganization);
        Long expectedOrganizationUidRequest = storedOrganization.getUid();

        OrganizationDetail expectedOrganizationRequest = OrganizationDetail.builder()
                .name(Optional.ofNullable("WCorp"))
                .build();
        OrganizationDetail expectedOrganizationResponse = OrganizationDetail.builder()
                .uid(Optional.ofNullable(expectedOrganizationUidRequest))
                .name(Optional.ofNullable("WCorp"))
                .build();

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
     * Update negative when entity with specified uid is not exists then failure with throw exception.
     */
    @Test
    public void update_negative_whenEntityWithSpecifiedUidIsNotExists_thenFailureWithThrowException() {
        Organization storedOrganization = Organization.builder()
                .name("CCorp")
                .build();
        this.organizationRepository.save(storedOrganization);
        Long expectedOrganizationUidRequest = storedOrganization.getUid();
        this.organizationRepository.deleteById(expectedOrganizationUidRequest);

        OrganizationDetail expectedOrganizationRequest = OrganizationDetail.builder()
                .name(Optional.ofNullable("WCorp"))
                .build();

        ResponseEntity<ExceptionDetail> response = this.restTemplate.exchange(
                String.format("http://localhost:%d/organizations/{uid}", port),
                HttpMethod.PATCH,
                new HttpEntity<OrganizationDetail>(expectedOrganizationRequest),
                ExceptionDetail.class,
                new HashMap<String, Long>(){{
                    put("uid", expectedOrganizationUidRequest);
                }}
        );
        ExceptionDetail actualOrganizationResponse = response.getBody();

        assertTrue(response.getStatusCode().equals(HttpStatus.BAD_REQUEST), "Код статуса не 400 - что-то пошло не так!");
        assertNotNull(actualOrganizationResponse, "Что-то пошло не так");
        assertTrue(
                actualOrganizationResponse.getMessage().contains("не существует"),
                "Ожидаемый и фактический результаты отличаются - что-то пошло не так!"
        );
    }

    /**
     * Delete positive when request is valid then successful deleted.
     */
    @Test
    public void delete_positive_whenRequestIsValid_thenSuccessfulDeleted() {
        Organization storedOrganization = Organization.builder()
                .name("WCorp")
                .build();
        this.organizationRepository.save(storedOrganization);
        Long expectedOrganizationUidRequest = storedOrganization.getUid();
        OrganizationDetail expectedOrganizationResponse = OrganizationDetail.builder()
                .build();

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
     * Delete negative when entity with specified uid is not exists then failure with throw exception.
     */
    @Test
    public void delete_negative_whenEntityWithSpecifiedUidIsNotExists_thenFailureWithThrowException() {
        Organization storedOrganization = Organization.builder()
                .name("CCorp")
                .build();
        this.organizationRepository.save(storedOrganization);
        Long expectedOrganizationUidRequest = storedOrganization.getUid();
        this.organizationRepository.deleteById(expectedOrganizationUidRequest);

        ResponseEntity<ExceptionDetail> response = this.restTemplate.exchange(
                String.format("http://localhost:%d/organizations/{uid}", port),
                HttpMethod.DELETE,
                null,
                ExceptionDetail.class,
                new HashMap<String, Long>(){{
                    put("uid", expectedOrganizationUidRequest);
                }}
        );
        ExceptionDetail actualOrganizationResponse = response.getBody();

        assertTrue(response.getStatusCode().equals(HttpStatus.BAD_REQUEST), "Код статуса не 400 - что-то пошло не так!");
        assertNotNull(actualOrganizationResponse, "Что-то пошло не так");
        assertTrue(
                actualOrganizationResponse.getMessage().contains("не существует"),
                "Ожидаемый и фактический результаты отличаются - что-то пошло не так!"
        );
    }

    /**
     * Find positive when request is valid then successful found.
     */
    @Test
    public void find_positive_whenRequestIsValid_thenSuccessfulFound() {
        List<Organization> storedOrganizations = Arrays.asList(
                Organization.builder()
                        .name("WCorp")
                        .inn("01")
                        .kpp("01")
                        .address(".ell road")
                        .build(),
                Organization.builder()
                        .name("CCorp")
                        .inn("02")
                        .kpp("02")
                        .address("noname str")
                        .build()
        );
        this.organizationRepository.saveAll(storedOrganizations);

        List<Long> expectedUidRequest = Collections.singletonList(storedOrganizations.get(0).getUid());
        List<String> expectedNameRequest = Collections.singletonList(storedOrganizations.get(0).getName());
        List<String> expectedInnRequest = Collections.singletonList(storedOrganizations.get(0).getInn());
        List<String> expectedKppRequest = Collections.singletonList(storedOrganizations.get(0).getKpp());
        List<String> expectedAddressRequest = Collections.singletonList(storedOrganizations.get(0).getAddress());
        List<OrganizationDetail> expectedOrganizationResponse = Collections.singletonList(
                OrganizationDetail.builder()
                        .uid(Optional.ofNullable(storedOrganizations.get(0).getUid()))
                        .name(Optional.ofNullable(storedOrganizations.get(0).getName()))
                        .inn(Optional.ofNullable(storedOrganizations.get(0).getInn()))
                        .kpp(Optional.ofNullable(storedOrganizations.get(0).getKpp()))
                        .address(Optional.ofNullable(storedOrganizations.get(0).getAddress()))
                        .build()
        );

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


        List<OrganizationDetail> expectedOrganizationResponse = Arrays.asList(
                OrganizationDetail.builder()
                        .uid(Optional.ofNullable(1L))
                        .name(Optional.ofNullable("WCorp"))
                        .inn(Optional.ofNullable("01"))
                        .kpp(Optional.ofNullable("01"))
                        .address(Optional.ofNullable(".ell road"))
                        .build(),
                OrganizationDetail.builder()
                        .uid(Optional.ofNullable(2L))
                        .name(Optional.ofNullable("CCorp"))
                        .inn(Optional.ofNullable("02"))
                        .kpp(Optional.ofNullable("02"))
                        .address(Optional.ofNullable("noname str"))
                        .build()
        );

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

    /**
     * Clean db.
     */
    private void cleanDb() {

        if (log.isInfoEnabled())
            log.info(
                    "В таблице было {} записей",
                    organizationRepository.count()
            );

        organizationRepository.deleteAll();

        if (log.isInfoEnabled())
            log.info(
                    "В таблице стало {} записей",
                    organizationRepository.count()
            );
    }
}
