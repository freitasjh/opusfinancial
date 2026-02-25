package br.com.systec.opusfinancial.reporting.impl.service;

import br.com.systec.opusfinancial.reporting.api.service.AccountReportingService;
import br.com.systec.opusfinancial.reporting.impl.repository.AccountReportingRepository;
import br.com.systec.opusfinancial.reporting.vo.AccountBalanceVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountReportingServiceImpl implements AccountReportingService {

    private final AccountReportingRepository repository;

    public AccountReportingServiceImpl(AccountReportingRepository repository) {
        this.repository = repository;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<AccountBalanceVO> getSummaryAccountBalance() {
        return repository.getSummaryAccountsBalance();
    }
}
