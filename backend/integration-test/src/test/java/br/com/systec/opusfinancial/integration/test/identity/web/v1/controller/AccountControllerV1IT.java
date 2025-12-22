package br.com.systec.opusfinancial.integration.test.identity.web.v1.controller;

import br.com.systec.opusfinancial.OpusfinancialApplication;
import br.com.systec.opusfinancial.commons.controller.RestPath;
import br.com.systec.opusfinancial.integration.test.AbstractIT;
import br.com.systec.opusfinancial.integration.test.fake.AccountFake;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@Order(1)
@SpringBootTest(classes = OpusfinancialApplication.class)
@ActiveProfiles("integration")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountControllerV1IT extends AbstractIT {
    private static final String ENDPOINT = RestPath.V1 + "/accounts";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    void whenCreateAccount_thenReturn204() throws Exception {
        var accountFake = AccountFake.toCreateFake();

        String body = JsonUtil.converteObjetoParaString(accountFake);

        mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT + "/create")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body))
                .andExpect(MockMvcResultMatchers.status().is(204))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

}
