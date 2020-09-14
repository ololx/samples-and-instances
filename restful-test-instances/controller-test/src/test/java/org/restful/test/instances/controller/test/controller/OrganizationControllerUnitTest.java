package org.restful.test.instances.controller.test.controller;

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

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
public class OrganizationControllerUnitTest {

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
                .name(java.util.Optional.of("WCorp"))
                .build();
        OrganizationDetail expectedOrganizationResponse = OrganizationDetail.builder()
                .uid(java.util.Optional.of(1L))
                .name(java.util.Optional.of("WCorp"))
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
        assertNotNull(actualOrganizationResponse.getUid().orElse(null), "Идентификатор null");
        assertTrue(
                actualOrganizationResponse.equals(expectedOrganizationResponse),
                "Ожидаемый и фактический результаты отличаются - что-то пошло не так!"
        );
    }
}
