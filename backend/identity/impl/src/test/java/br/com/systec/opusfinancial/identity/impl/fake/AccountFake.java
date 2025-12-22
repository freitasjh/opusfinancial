package br.com.systec.opusfinancial.identity.impl.fake;

import br.com.systec.opusfinancial.identity.api.vo.AccountVO;

public class AccountFake {

    public static AccountVO toFakeVO() {
        AccountVO accountVO = new AccountVO();
        accountVO.setName("Usuario Teste");
        accountVO.setEmail("teste@email.com.br");
        accountVO.setPassword("password123");
        accountVO.setAccountName("Empresa Teste");
        accountVO.setUsername("teste");
        accountVO.setDocument("12345678901");
        return accountVO;
    }
}
