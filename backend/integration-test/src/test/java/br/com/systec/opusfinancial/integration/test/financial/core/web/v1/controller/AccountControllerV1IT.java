package br.com.systec.opusfinancial.integration.test.financial.core.web.v1.controller;

import br.com.systec.opusfinancial.commons.controller.RestPath;
import br.com.systec.opusfinancial.financial.catalog.impl.domain.Account;
import br.com.systec.opusfinancial.financial.core.web.v1.dto.AccountInfoResponseDTO;
import br.com.systec.opusfinancial.financial.core.web.v1.dto.AccountResponseSaveDTO;
import br.com.systec.opusfinancial.integration.test.AbstractIT;
import br.com.systec.opusfinancial.integration.test.fake.AccountFake;
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
public class AccountControllerV1IT extends AbstractIT {
    private static final String ENDPOINT = RestPath.V1 + "/accounts";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    void whenCreateNewAccount_thenReturn200() throws Exception {
        var accountFake = AccountFake.toAccountFake();
        accountFake.setBankId(IntegrationUtil.bankId);

        String body = JsonUtil.converteObjetoParaString(accountFake);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT + "/create")
                        .header("Authorization", "Bearer "+ IntegrationUtil.accessToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        AccountResponseSaveDTO saveResponse = (AccountResponseSaveDTO) JsonUtil.convertStringToObject(result.getResponse().getContentAsString(), AccountResponseSaveDTO.class);
        IntegrationUtil.accountId = saveResponse.getId();
    }

    @Test
    @Order(2)
    void whenFindById_thenReturn200() throws Exception {
        var accountFake = AccountFake.toAccountFake();
        accountFake.setBankId(IntegrationUtil.bankId);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT + "/"+ IntegrationUtil.accountId)
                        .header("Authorization", "Bearer "+ IntegrationUtil.accessToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        AccountInfoResponseDTO saveResponse = (AccountInfoResponseDTO) JsonUtil.convertStringToObject(result.getResponse().getContentAsString(), AccountInfoResponseDTO.class);
        Assertions.assertThat(saveResponse).isNotNull();
        Assertions.assertThat(saveResponse.getId()).isEqualTo(IntegrationUtil.accountId);
        Assertions.assertThat(saveResponse.getAccountName()).isEqualTo(accountFake.getAccountName());
        Assertions.assertThat(saveResponse.getAccountType()).isEqualTo(accountFake.getAccountType());
        Assertions.assertThat(saveResponse.getBankId()).isEqualTo(accountFake.getBankId());
    }
}
