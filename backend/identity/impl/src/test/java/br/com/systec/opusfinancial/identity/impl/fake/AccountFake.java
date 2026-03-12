package br.com.systec.opusfinancial.identity.impl.fake;

import br.com.systec.opusfinancial.identity.api.domain.UserAccount;

public class AccountFake {

    public static UserAccount toFakeVO() {
        UserAccount accountVO = new UserAccount();
        accountVO.setName("Usuario Teste");
        accountVO.setEmail("teste@email.com.br");
        accountVO.setPassword("password123");
        accountVO.setAccountName("Empresa Teste");
        accountVO.setUsername("teste");
        accountVO.setDocument("12345678901");
        return accountVO;
    }
}
