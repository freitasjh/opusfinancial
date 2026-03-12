package br.com.systec.opusfinancial.financial.catalog.impl.mapper;

import br.com.systec.opusfinancial.api.domain.Bank;
import br.com.systec.opusfinancial.financial.api.domain.Account;
import br.com.systec.opusfinancial.financial.catalog.impl.entity.AccountEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public class AccountMapper {

    private AccountMapper() {}

    public static AccountMapper of() {
        return new AccountMapper();
    }

    public AccountEntity toEntity(Account accountVO) {
        AccountEntity account = new AccountEntity();
        account.setId(accountVO.getId());
        account.setTenantId(accountVO.getTenantId());
        account.setAccountName(accountVO.getAccountName());
        account.setBalance(accountVO.getBalance());
        account.setAccountType(accountVO.getAccountType());
        account.setAgency(accountVO.getAgency());
        account.setAccountNumber(accountVO.getAccountNumber());

        if (accountVO.getBank() != null) {
            account.setBankId(accountVO.getBank().getId());
        }

        return account;
    }

    public Account toVO(AccountEntity account) {
        Account accountVO = new Account();
        accountVO.setId(account.getId());
        accountVO.setAccountName(account.getAccountName());
        accountVO.setAccountType(account.getAccountType());
        accountVO.setBalance(account.getBalance());

        return accountVO;
    }

    public Page<Account> toPageVO(Page<AccountEntity> result, List<Bank> listBank) {
        return result.map(item -> {
            Bank bankVO = listBank.stream()
                    .filter(bank -> bank.getId().equals(item.getBankId()))
                    .findFirst()
                    .orElse(null);
            Account accountVO = toVO(item);
            accountVO.setBank(bankVO);
            return accountVO;
        });
    }

    public List<Account> toList(List<AccountEntity> listOfAccount) {
        return listOfAccount.stream().map(this::toVO).toList();
    }
}
