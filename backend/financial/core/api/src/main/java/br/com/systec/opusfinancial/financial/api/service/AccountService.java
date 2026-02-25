package br.com.systec.opusfinancial.financial.api.service;

import br.com.systec.opusfinancial.financial.api.filter.FilterAccount;
import br.com.systec.opusfinancial.financial.api.vo.AccountVO;
import br.com.systec.opusfinancial.financial.api.vo.TransactionType;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface AccountService {

    AccountVO create(AccountVO account);

    AccountVO update(AccountVO account);

    Page<AccountVO> findByFilter(FilterAccount filter);

    AccountVO findById(UUID id);

    void createDefaultAccount(UUID tenantId);

    List<AccountVO> findByIds(List<UUID> listOfAccountId);

    void updateBalance(UUID accountId, BigDecimal amount, TransactionType transactionType);
}
