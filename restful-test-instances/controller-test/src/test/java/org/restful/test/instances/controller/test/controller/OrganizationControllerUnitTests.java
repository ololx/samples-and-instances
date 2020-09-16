package org.restful.test.instances.controller.test.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.restful.test.instances.controller.OrganizationController;
import org.restful.test.instances.model.detail.OrganizationDetail;
import org.restful.test.instances.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @project restful-test-instances
 * @created 2020-09-14 13:46
 * <p>
 * @author Alexander A. Kropotin
 *
 * The type Organization controller unit test.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(OrganizationController.class)
@Slf4j
@NoArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE
)
public class OrganizationControllerUnitTests {

    /**
     * The Mvc.
     */
    @Autowired
    MockMvc mvc;

    /**
     * The Object mapper.
     */
    @Autowired
    ObjectMapper objectMapper;

    /**
     * The Organization service.
     */
    @MockBean
    OrganizationService organizationService;

    /**
     * The Processing controller.
     */
    @InjectMocks
    @Autowired
    OrganizationController processingController;

    /**
     * Create positive when request is valid then successful created.
     *
     * @throws Exception the exception
     */
    @Test
    public void create_positive_whenRequestIsValid_thenSuccessfulCreated() throws Exception {
        OrganizationDetail expectedOrganizationRequest = OrganizationDetail.builder()
                .name(Optional.of("WCorp"))
                .build();
        OrganizationDetail expectedOrganizationResponse = OrganizationDetail.builder()
                .uid(Optional.of(1L))
                .name(Optional.of("WCorp"))
                .build();
        when(organizationService.create(expectedOrganizationRequest)).thenReturn(expectedOrganizationResponse);

        MvcResult mvcResult = mvc.perform(
                post("/organizations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(expectedOrganizationRequest))
        )
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        OrganizationDetail actualOrganizationResponse = this.objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                OrganizationDetail.class
        );

        verify(organizationService).create(any(OrganizationDetail.class));
        verify(organizationService).create(expectedOrganizationRequest);
        assertNotNull(actualOrganizationResponse, "Что-то пошло не так");
        assertTrue(
                actualOrganizationResponse.equals(expectedOrganizationResponse),
                "Ожидаемый и фактический результаты отличаются - что-то пошло не так!"
        );
    }

    /**
     * Update positive when request is valid then successful updated.
     *
     * @throws Exception the exception
     */
    @Test
    public void update_positive_whenRequestIsValid_thenSuccessfulUpdated() throws Exception {
        Long expectedOrganizationUidRequest = 1L;
        OrganizationDetail expectedOrganizationRequest = OrganizationDetail.builder()
                .uid(Optional.of(expectedOrganizationUidRequest))
                .name(Optional.of("WCorp"))
                .build();
        OrganizationDetail expectedOrganizationResponse = OrganizationDetail.builder()
                .uid(Optional.of(1L))
                .name(Optional.of("WCorp"))
                .build();
        when(organizationService.update(expectedOrganizationUidRequest, expectedOrganizationRequest))
                .thenReturn(expectedOrganizationResponse);

        MvcResult mvcResult = mvc.perform(
                patch(String.format("/organizations/%d", expectedOrganizationUidRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(expectedOrganizationRequest))
        )
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        OrganizationDetail actualOrganizationResponse = this.objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                OrganizationDetail.class
        );

        verify(organizationService).update(anyLong(), any(OrganizationDetail.class));
        verify(organizationService).update(expectedOrganizationUidRequest, expectedOrganizationRequest);
        assertNotNull(actualOrganizationResponse, "Что-то пошло не так");
        assertTrue(
                actualOrganizationResponse.equals(expectedOrganizationResponse),
                "Ожидаемый и фактический результаты отличаются - что-то пошло не так!"
        );
    }

    /**
     * Delete positive when request is valid then successful deleted.
     *
     * @throws Exception the exception
     */
    @Test
    public void update_positive_whenRequestIsValid_thenSuccessfulDeleted() throws Exception {
        Long expectedOrganizationUidRequest = 1L;
        OrganizationDetail expectedOrganizationResponse = OrganizationDetail.builder()
                .uid(null)
                .name(null)
                .build();
        when(organizationService.delete(expectedOrganizationUidRequest))
                .thenReturn(expectedOrganizationResponse);

        MvcResult mvcResult = mvc.perform(
                delete(String.format("/organizations/%d", expectedOrganizationUidRequest))
        )
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        OrganizationDetail actualOrganizationResponse = this.objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                OrganizationDetail.class
        );

        verify(organizationService).delete(anyLong());
        verify(organizationService).delete(expectedOrganizationUidRequest);
        assertNotNull(actualOrganizationResponse, "Что-то пошло не так");
        assertTrue(
                actualOrganizationResponse.equals(expectedOrganizationResponse),
                "Ожидаемый и фактический результаты отличаются - что-то пошло не так!"
        );
    }

    /**
     * Find positive when request is valid then successful found.
     *
     * @throws Exception the exception
     */
    @Test
    public void find_positive_whenRequestIsValid_thenSuccessfulFound() throws Exception {
        List<Long> expectedUidRequest = Collections.singletonList(1L);
        List<String> expectedNameRequest = Collections.singletonList("WCorp");
        List<String> expectedInnRequest = Collections.singletonList("01");
        List<String> expectedKppRequest = Collections.singletonList("01");
        List<String> expectedAddressRequest = Collections.singletonList(".ell road");
        List<OrganizationDetail> expectedOrganizationResponse = Collections.singletonList(
                OrganizationDetail.builder()
                        .uid(Optional.ofNullable(expectedUidRequest.get(0)))
                        .name(Optional.ofNullable(expectedNameRequest.get(0)))
                        .inn(Optional.ofNullable(expectedInnRequest.get(0)))
                        .kpp(Optional.ofNullable(expectedKppRequest.get(0)))
                        .address(Optional.ofNullable(expectedAddressRequest.get(0)))
                        .build()
        );
        when(organizationService.find(anyList(), anyList(), anyList(), anyList(), anyList()))
                .thenReturn(expectedOrganizationResponse);

        MvcResult mvcResult = mvc.perform(
                get("/organizations")
                .param("uid", expectedUidRequest.stream().map(p -> String.valueOf(p)).toArray(String[]::new))
                .param("name", expectedNameRequest.stream().toArray(String[]::new))
                .param("inn", expectedInnRequest.stream().toArray(String[]::new))
                .param("kpp", expectedKppRequest.stream().toArray(String[]::new))
                .param("address", expectedAddressRequest.stream().toArray(String[]::new))
        )
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        List<OrganizationDetail> actualOrganizationResponse = this.objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                new TypeReference<List<OrganizationDetail>>(){}
        );

        verify(organizationService).find(anyList(), anyList(), anyList(), anyList(), anyList());
        verify(organizationService).find(
                expectedUidRequest,
                expectedNameRequest,
                expectedInnRequest,
                expectedKppRequest,
                expectedAddressRequest
        );
        assertNotNull(actualOrganizationResponse, "Что-то пошло не так");
        assertTrue(
                actualOrganizationResponse.equals(expectedOrganizationResponse),
                "Ожидаемый и фактический результаты отличаются - что-то пошло не так!"
        );
    }
}
