package br.com.systec.opusfinancial.reporting.api.service;

import br.com.systec.opusfinancial.reporting.vo.AccountBalanceVO;

import java.util.List;

public interface AccountReportingService {

    List<AccountBalanceVO> getSummaryAccountBalance();
}
