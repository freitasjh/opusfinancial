package br.com.systec.opusfinancial.integration.test.fake;

import br.com.systec.opusfinancial.web.v1.dto.AccountCreateDTO;

public class AccountFake {

    public static AccountCreateDTO toCreateFake() {
        var accountCreateDTO = new AccountCreateDTO();
        accountCreateDTO.setUsername("teste");
        accountCreateDTO.setPassword("admin");
        accountCreateDTO.setName("Conta teste");
        accountCreateDTO.setName("teste");
        accountCreateDTO.setDocument("1111");
        accountCreateDTO.setEmail("teste@teste.com.br");

        return accountCreateDTO;
    }

}
