package br.com.systec.opusfinancial.financial.catalog.impl.fake;

import br.com.systec.opusfinancial.financial.api.domain.Account;

import java.util.List;
import java.util.UUID;

public class AccountFake {

    public static Account toFakeVO() {
        Account accountVO = new Account();
        accountVO.setId(UUID.randomUUID());
        accountVO.setAccountName("Account Name");
        accountVO.setAccountNumber("123456-9");
        return accountVO;
    }

    public static List<Account> toFakeVOs() {
        return List.of(toFakeVO());
    }
}
