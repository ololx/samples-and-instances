package org.restful.test.instances.controller.test.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import liquibase.Liquibase;
import liquibase.exception.DatabaseException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.restful.test.instances.controller.OrganizationController;
import org.restful.test.instances.model.detail.OrganizationDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @project restful-test-instances
 * @created 16.09.2020 14:45
 * <p>
 * @author Alexander A. Kropotin
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

    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    OrganizationController organizationController;

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

        log.info(String.valueOf(actualOrganizationResponse));

        assertNotNull(actualOrganizationResponse, "Что-то пошло не так");
        assertTrue(
                actualOrganizationResponse.equals(expectedOrganizationResponse),
                "Ожидаемый и фактический результаты отличаются - что-то пошло не так!"
        );
    }
}
