package br.com.systec.opusfinancial.integration.test.fake;

import br.com.systec.opusfinancial.financial.api.vo.AccountType;
import br.com.systec.opusfinancial.financial.core.web.v1.dto.AccountInputDTO;

import java.math.BigDecimal;

public class AccountFake {

    public static AccountInputDTO toAccountFake() {
        AccountInputDTO accountInputDTO = new AccountInputDTO();
        accountInputDTO.setBalance(BigDecimal.valueOf(0.0));
        accountInputDTO.setAccountName("Conta teste");
        accountInputDTO.setAccountType(AccountType.WALLET);

        return accountInputDTO;
    }
}
