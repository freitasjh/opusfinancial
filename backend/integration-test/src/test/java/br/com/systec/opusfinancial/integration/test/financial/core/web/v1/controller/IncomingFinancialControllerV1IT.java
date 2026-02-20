package br.com.systec.opusfinancial.integration.test.financial.core.web.v1.controller;

import br.com.systec.opusfinancial.commons.controller.RestPath;
import br.com.systec.opusfinancial.financial.core.web.v1.dto.IncomingFinancialInputDTO;
import br.com.systec.opusfinancial.integration.test.AbstractIT;
import br.com.systec.opusfinancial.integration.test.util.IntegrationUtil;
import br.com.systec.opusfinancial.integration.test.util.JsonUtil;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
public class IncomingFinancialControllerV1IT extends AbstractIT {
    private static final String ENDPOINT = RestPath.V1 + "/incomings";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    void whenCreateIncomingTransaction_thenReturn200() throws Exception {
        IncomingFinancialInputDTO incomingFinancialInputDTO = new IncomingFinancialInputDTO();
        incomingFinancialInputDTO.setAccountId(IntegrationUtil.accountId);
        incomingFinancialInputDTO.setAmount(BigDecimal.valueOf(100.0));
        incomingFinancialInputDTO.setDescription("Teste de cadastro de receita");
        incomingFinancialInputDTO.setProcessed(true);
        incomingFinancialInputDTO.setPaymentAt(LocalDate.now());
        incomingFinancialInputDTO.setProcessedAt(LocalDateTime.now());
        incomingFinancialInputDTO.setCategoryId(IntegrationUtil.categoryId);

        String body = JsonUtil.converteObjetoParaString(incomingFinancialInputDTO);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT + "/create")
                        .header("Authorization", "Bearer "+ IntegrationUtil.accessToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    @Order(2)
    void whenFindByFilter_thenReturn200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT + "/filter")
                        .header("Authorization", "Bearer "+ IntegrationUtil.accessToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}
