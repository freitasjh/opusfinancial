package br.com.systec.opusfinancial.integration.test.financial.creditcard;

import br.com.systec.opusfinancial.financial.creditcard.web.internal.v1.dto.CreditCardInputDTO;
import br.com.systec.opusfinancial.financial.creditcard.web.internal.v1.dto.CreditCardSaveResponseDTO;
import br.com.systec.opusfinancial.integration.test.AbstractIT;
import br.com.systec.opusfinancial.integration.test.fake.CreditCardFake;
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


import static br.com.systec.opusfinancial.integration.test.util.IntegrationEndpoint.*;
import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
public class CreditCardControllerInternalV1IT extends AbstractIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    void whenCreateCreditCard_thenReturn_200() throws Exception {
        var creditCard = CreditCardFake.toInputDTO();

        String payloadBody = JsonUtil.converteObjetoParaString(creditCard);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT_CREATE_CREDIT_CARD_V1)
                        .header("Authorization", getAuthorizationBearer())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(payloadBody))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        var creditCardResponse = (CreditCardSaveResponseDTO) JsonUtil.convertStringToObject(
                result.getResponse().getContentAsString(),
                CreditCardSaveResponseDTO.class
        );

        assertThat(creditCardResponse).isNotNull();
        assertThat(creditCardResponse.id()).isNotNull();

        IntegrationUtil.creditCardId = creditCardResponse.id();
    }

    @Test
    @Order(2)
    void whenCreateCreditCard_thenReturn_406_validate_error() throws Exception {
        var creditCard = CreditCardFake.toValidateError();
        String payloadBody = JsonUtil.converteObjetoParaString(creditCard);

        mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT_CREATE_CREDIT_CARD_V1)
                        .header("Authorization", getAuthorizationBearer())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(payloadBody))
                .andExpect(MockMvcResultMatchers.status().is(406))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    @Order(3)
    void whenFindCreditCardById_thenReturn_200() throws Exception {
        var creditCardSavedFake = CreditCardFake.toInputDTO();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("%s/%s".formatted(ENDPOINT_CREDIT_CARD_V1, IntegrationUtil.creditCardId))
                        .header("Authorization", getAuthorizationBearer())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        var creditCardResponse = (CreditCardInputDTO) JsonUtil.convertStringToObject(
                result.getResponse().getContentAsString(),
                CreditCardInputDTO.class
        );

        assertThat(creditCardResponse).isNotNull();
        assertThat(creditCardResponse.id()).isEqualTo(IntegrationUtil.creditCardId);
        assertThat(creditCardResponse.name()).isEqualTo(creditCardSavedFake.name());
        assertThat(creditCardResponse.nameCreditCard()).isEqualTo(creditCardSavedFake.nameCreditCard());
        assertThat(creditCardResponse.limit()).isNotZero();
        assertThat(creditCardResponse.brand().name()).isEqualTo(creditCardSavedFake.brand().name());
        assertThat(creditCardResponse.dueDay()).isEqualTo(creditCardSavedFake.dueDay());
        assertThat(creditCardResponse.closingDate()).isEqualTo(creditCardSavedFake.closingDate());
        assertThat(creditCardResponse.accountId()).isEqualTo(creditCardSavedFake.accountId());
        assertThat(creditCardResponse.availableLimit()).isNotZero();
    }
}
