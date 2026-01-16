package br.com.systec.opusfinancial.financial.catalog.impl.mapper;

import br.com.systec.opusfinancial.api.vo.BankVO;
import br.com.systec.opusfinancial.financial.api.vo.AccountVO;
import br.com.systec.opusfinancial.financial.catalog.impl.domain.Account;
import org.springframework.data.domain.Page;

import java.util.List;

public class AccountMapper {

    private AccountMapper() {}

    public static AccountMapper of() {
        return new AccountMapper();
    }

    public Account toEntity(AccountVO accountVO) {
        Account account = new Account();
        account.setId(accountVO.getId());
        account.setTenantId(accountVO.getTenantId());
        account.setAccountName(accountVO.getAccountName());
        account.setBalance(accountVO.getBalance());
        account.setAccountType(accountVO.getAccountType());

        if (accountVO.getBank() != null) {
            account.setBankId(accountVO.getBank().getId());
        }

        return account;
    }

    public AccountVO toVO(Account account) {
        AccountVO accountVO = new AccountVO();
        accountVO.setId(account.getId());
        accountVO.setAccountName(account.getAccountName());
        accountVO.setAccountType(account.getAccountType());
        accountVO.setBalance(account.getBalance());

        return accountVO;
    }

    public Page<AccountVO> toPageVO(Page<Account> result, List<BankVO> listBank) {
        return result.map(item -> {
            BankVO bankVO = listBank.stream()
                    .filter(bank -> bank.getId().equals(item.getBankId()))
                    .findFirst()
                    .orElse(null);
            AccountVO accountVO = toVO(item);
            accountVO.setBank(bankVO);
            return accountVO;
        });
    }
}
