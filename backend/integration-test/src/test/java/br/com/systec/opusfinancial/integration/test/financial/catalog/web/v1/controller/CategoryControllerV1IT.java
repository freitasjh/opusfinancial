package br.com.systec.opusfinancial.integration.test.financial.catalog.web.v1.controller;

import br.com.systec.opusfinancial.OpusfinancialApplication;
import br.com.systec.opusfinancial.commons.controller.RestPath;
import br.com.systec.opusfinancial.core.web.v1.dto.CategoryInputDTO;
import br.com.systec.opusfinancial.core.web.v1.dto.CategoryResponseDTO;
import br.com.systec.opusfinancial.core.web.v1.dto.CategorySaveResponseDTO;
import br.com.systec.opusfinancial.integration.test.AbstractIT;
import br.com.systec.opusfinancial.integration.test.fake.CategoryFake;
import br.com.systec.opusfinancial.integration.test.util.IntegrationUtil;
import br.com.systec.opusfinancial.integration.test.util.JsonUtil;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;

@Order(3)
@SpringBootTest(classes = OpusfinancialApplication.class)
@ActiveProfiles("integration")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryControllerV1IT extends AbstractIT {
    private static final String ENDPOINT = RestPath.V1 + "/categories";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    void whenCreateCategory_thenReturnSuccess200() throws Exception {
        CategoryInputDTO categoryToSave = CategoryFake.fakeInputDTO();

        String body = JsonUtil.converteObjetoParaString(categoryToSave);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT + "/create")
                        .header("Authorization", "Bearer "+ IntegrationUtil.accessToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        CategorySaveResponseDTO responseDTO = (CategorySaveResponseDTO) JsonUtil.convertStringToObject(result.getResponse().getContentAsString(),
                CategorySaveResponseDTO.class);

        assertThat(responseDTO.getId()).isNotNull();
        assertThat(responseDTO.getName()).isEqualTo(categoryToSave.getName());
        IntegrationUtil.categoryId = responseDTO.getId();
    }

    @Test
    @Order(2)
    void whenFindCategoryById_thenReturnCategoryFullInformation_200() throws Exception {
        CategoryInputDTO categoryToSave = CategoryFake.fakeInputDTO();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT + "/"+IntegrationUtil.categoryId.toString())
                        .header("Authorization", "Bearer "+ IntegrationUtil.accessToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        CategoryResponseDTO responseDTO = (CategoryResponseDTO) JsonUtil.convertStringToObject(result.getResponse().getContentAsString(), CategoryResponseDTO.class);

        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getTenantId()).isNotNull();
        assertThat(responseDTO.getId()).isEqualTo(IntegrationUtil.categoryId);
        assertThat(responseDTO.getName()).isEqualTo(categoryToSave.getName());
        assertThat(responseDTO.getColorHex()).isEqualTo(categoryToSave.getColorHex());
        assertThat(responseDTO.getIconCode()).isEqualTo(categoryToSave.getIconCode());
    }

    @Test
    @Order(3)
    void whenFindCategoryByFilter_thenReturnPaginatedResult() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT + "/filter")
                        .header("Authorization", "Bearer "+ IntegrationUtil.accessToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.page.totalElements").value(13))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}
