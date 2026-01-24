package br.com.systec.opusfinancial.financial.api.service;

import br.com.systec.opusfinancial.financial.api.filter.FilterAccount;
import br.com.systec.opusfinancial.financial.api.vo.AccountVO;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface AccountService {

    AccountVO create(AccountVO account);

    AccountVO update(AccountVO account);

    Page<AccountVO> findByFilter(FilterAccount filter);

    AccountVO findById(UUID id);

    void createDefaultAccount(UUID tenantId);

}
