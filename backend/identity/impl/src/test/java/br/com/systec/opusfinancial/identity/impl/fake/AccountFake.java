package br.com.systec.opusfinancial.identity.impl.fake;

import br.com.systec.opusfinancial.identity.api.vo.UserAccountVO;

public class AccountFake {

    public static UserAccountVO toFakeVO() {
        UserAccountVO accountVO = new UserAccountVO();
        accountVO.setName("Usuario Teste");
        accountVO.setEmail("teste@email.com.br");
        accountVO.setPassword("password123");
        accountVO.setAccountName("Empresa Teste");
        accountVO.setUsername("teste");
        accountVO.setDocument("12345678901");
        return accountVO;
    }
}
