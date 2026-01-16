package br.com.systec.opusfinancial.integration.test.security.web.v1.controller;

import br.com.systec.opusfinancial.OpusfinancialApplication;
import br.com.systec.opusfinancial.commons.controller.RestPath;
import br.com.systec.opusfinancial.integration.test.AbstractIT;
import br.com.systec.opusfinancial.integration.test.util.IntegrationUtil;
import br.com.systec.opusfinancial.integration.test.util.JsonUtil;
import br.com.systec.opusfinancial.security.web.v1.dto.LoginDTO;
import br.com.systec.opusfinancial.security.web.v1.dto.LoginResponseDTO;
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

@Order(1)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginControllerV1IT extends AbstractIT {
    private static final String ENDPOINT = RestPath.V1 + "/auth";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenLoginDefaultAccount_thenReturnSuccess() throws Exception {
        var loginDTO = new LoginDTO();
        loginDTO.setUsername("user01");
        loginDTO.setPassword("user01");

        String body = JsonUtil.converteObjetoParaString(loginDTO);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT+"/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        LoginResponseDTO loginResponseDTO = (LoginResponseDTO) JsonUtil.convertStringToObject(result.getResponse().getContentAsString(), LoginResponseDTO.class);
        IntegrationUtil.accessToken = loginResponseDTO.getAccessToken();
    }
}
