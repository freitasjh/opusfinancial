package br.com.systec.opusfinancial.financial.catalog.impl.fake;

import br.com.systec.opusfinancial.financial.api.vo.AccountVO;

import java.util.List;
import java.util.UUID;

public class AccountFake {

    public static AccountVO toFakeVO() {
        AccountVO accountVO = new AccountVO();
        accountVO.setId(UUID.randomUUID());
        accountVO.setAccountName("Account Name");
        accountVO.setAccountNumber("123456-9");
        return accountVO;
    }

    public static List<AccountVO> toFakeVOs() {
        return List.of(toFakeVO());
    }
}
