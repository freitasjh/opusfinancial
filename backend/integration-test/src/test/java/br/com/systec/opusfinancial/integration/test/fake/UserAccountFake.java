package br.com.systec.opusfinancial.integration.test.fake;

import br.com.systec.opusfinancial.core.web.v1.dto.UserAccountCreateDTO;

public class UserAccountFake {

    public static UserAccountCreateDTO toCreateFake() {
        var accountCreateDTO = new UserAccountCreateDTO();
        accountCreateDTO.setUsername("user01");
        accountCreateDTO.setPassword("user01");
        accountCreateDTO.setName("Account test");
        accountCreateDTO.setName("User test");
        accountCreateDTO.setDocument("1111");
        accountCreateDTO.setEmail("teste@teste.com.br");

        return accountCreateDTO;
    }

}
