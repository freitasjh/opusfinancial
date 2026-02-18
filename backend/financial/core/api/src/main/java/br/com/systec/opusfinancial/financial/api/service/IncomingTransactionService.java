package br.com.systec.opusfinancial.financial.api.service;

import br.com.systec.opusfinancial.financial.api.filter.IncomingTransactionFilter;
import br.com.systec.opusfinancial.financial.api.vo.FinancialTransactionVO;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface IncomingTransactionService {

    FinancialTransactionVO save(FinancialTransactionVO transaction);

    FinancialTransactionVO update(FinancialTransactionVO transaction);

    Page<FinancialTransactionVO> findByFilter(IncomingTransactionFilter filter);

    FinancialTransactionVO findById(UUID id);
}
