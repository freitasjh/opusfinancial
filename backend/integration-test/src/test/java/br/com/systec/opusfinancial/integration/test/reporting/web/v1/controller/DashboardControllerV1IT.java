package br.com.systec.opusfinancial.integration.test.reporting.web.v1.controller;

import br.com.systec.opusfinancial.integration.test.AbstractIT;
import br.com.systec.opusfinancial.integration.test.util.IntegrationUtil;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static br.com.systec.opusfinancial.integration.test.util.IntegrationEndpoint.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
public class DashboardControllerV1IT extends AbstractIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    void when_getSummaryBalanceAccount_thenReturn200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_DASHBOARD_ACCOUNT_SUMMARY)
                        .header("Authorization", getAuthorizationBearer())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}
