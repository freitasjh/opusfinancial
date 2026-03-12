package br.com.systec.opusfinancial.financial.api.service;

import br.com.systec.opusfinancial.financial.api.filter.FilterAccount;
import br.com.systec.opusfinancial.financial.api.domain.Account;
import br.com.systec.opusfinancial.financial.api.domain.TransactionType;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface AccountService {

    Account create(Account account);

    Account update(Account account);

    Page<Account> findByFilter(FilterAccount filter);

    Account findById(UUID id);

    void createDefaultAccount(UUID tenantId);

    List<Account> findByIds(List<UUID> listOfAccountId);

    void updateBalance(UUID accountId, BigDecimal amount, TransactionType transactionType);
}
