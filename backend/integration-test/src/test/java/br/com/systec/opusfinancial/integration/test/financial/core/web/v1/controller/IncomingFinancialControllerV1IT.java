package br.com.systec.opusfinancial.integration.test.financial.core.web.v1.controller;

import br.com.systec.opusfinancial.financial.core.web.v1.dto.IncomingSaveResponseDTO;
import br.com.systec.opusfinancial.financial.core.web.v1.dto.IncomingTransactionInputDTO;
import br.com.systec.opusfinancial.integration.test.AbstractIT;
import br.com.systec.opusfinancial.integration.test.util.IntegrationUtil;
import br.com.systec.opusfinancial.integration.test.util.JsonUtil;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static br.com.systec.opusfinancial.integration.test.util.IntegrationEndpoint.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
public class IncomingFinancialControllerV1IT extends AbstractIT {
    @Autowired
    private MockMvc mockMvc;
    private static UUID incomingSaveId;

    @Test
    @Order(1)
    void whenCreateIncomingTransaction_thenReturn200() throws Exception {
        IncomingTransactionInputDTO incomingFinancialInputDTO = new IncomingTransactionInputDTO();
        incomingFinancialInputDTO.setAccountId(IntegrationUtil.accountId);
        incomingFinancialInputDTO.setAmount(BigDecimal.valueOf(100.0));
        incomingFinancialInputDTO.setDescription("Teste de cadastro de receita");
        incomingFinancialInputDTO.setProcessed(true);
        incomingFinancialInputDTO.setPaymentAt(LocalDate.now());
        incomingFinancialInputDTO.setProcessedAt(LocalDateTime.now());
        incomingFinancialInputDTO.setCategoryId(IntegrationUtil.categoryId);

        String body = JsonUtil.converteObjetoParaString(incomingFinancialInputDTO);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT_INCOMING_CREATE)
                        .header("Authorization", getAuthorizationBearer())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        IncomingSaveResponseDTO responseSave = (IncomingSaveResponseDTO) JsonUtil.convertStringToObject(result.getResponse().getContentAsString(), IncomingSaveResponseDTO.class);

        incomingSaveId = responseSave.getId();
    }

    @Test
    @Order(2)
    void whenFindByFilter_thenReturn200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_INCOMING_FILTER)
                        .header("Authorization", getAuthorizationBearer())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.page.totalElements").value(1))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }


    @Test
    @Order(3)
    void whenFindById_thenReturn200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("%s/%s".formatted(ENDPOINT_INCOMING_V1, incomingSaveId))
                        .header("Authorization", getAuthorizationBearer())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(incomingSaveId.toString()))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    @Order(4)
    void whenSaveIncomingAndUpdateBalanceAccount_thenReturnBalance() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("%s/%s".formatted(ENDPOINT_ACCOUNT_V1, IntegrationUtil.accountId))
                        .header("Authorization", getAuthorizationBearer())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(100.0))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

}
