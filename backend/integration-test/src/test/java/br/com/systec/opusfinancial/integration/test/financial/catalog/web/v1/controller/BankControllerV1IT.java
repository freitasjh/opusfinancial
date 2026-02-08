package br.com.systec.opusfinancial.integration.test.financial.catalog.web.v1.controller;

import br.com.systec.opusfinancial.commons.controller.RestPath;
import br.com.systec.opusfinancial.core.web.v1.dto.BankFindResponseDTO;
import br.com.systec.opusfinancial.integration.test.AbstractIT;
import br.com.systec.opusfinancial.integration.test.util.IntegrationUtil;
import br.com.systec.opusfinancial.integration.test.util.JsonUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
public class BankControllerV1IT extends AbstractIT {
    private static final String ENDPOINT = RestPath.V1 + "/banks";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    void whenFindByFilter_thenReturnSuccess_200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT + "/filter")
                        .header("Authorization", "Bearer "+ IntegrationUtil.accessToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.page.totalElements").value(22))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    @Order(2)
    void whenFindByBankCode_thenReturnSuccess_200() throws Exception {
        MvcResult result =  mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT + "/code/748")
                        .header("Authorization", "Bearer "+ IntegrationUtil.accessToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        BankFindResponseDTO bankFindResponseDTO = (BankFindResponseDTO) JsonUtil.convertStringToObject(result.getResponse().getContentAsString(), BankFindResponseDTO.class);

        Assertions.assertThat(bankFindResponseDTO).isNotNull();
        Assertions.assertThat(bankFindResponseDTO.getName()).isEqualTo("Sicredi");

        IntegrationUtil.bankId = bankFindResponseDTO.getId();
    }

    @Test
    @Order(3)
    void whenFindById_thenReturnSuccess_200() throws Exception {
        MvcResult result =  mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT + "/" + IntegrationUtil.bankId)
                        .header("Authorization", "Bearer "+ IntegrationUtil.accessToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        BankFindResponseDTO bankFindResponseDTO = (BankFindResponseDTO) JsonUtil.convertStringToObject(result.getResponse().getContentAsString(), BankFindResponseDTO.class);

        Assertions.assertThat(bankFindResponseDTO).isNotNull();
        Assertions.assertThat(bankFindResponseDTO.getName()).isEqualTo("Sicredi");
    }
}
