package br.com.systec.opusfinancial.financial.api.service;

import br.com.systec.opusfinancial.financial.api.filter.IncomingTransactionFilter;
import br.com.systec.opusfinancial.financial.api.domain.FinancialTransaction;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface IncomingTransactionService {

    FinancialTransaction save(FinancialTransaction transaction);

    FinancialTransaction update(FinancialTransaction transaction);

    Page<FinancialTransaction> findByFilter(IncomingTransactionFilter filter);

    FinancialTransaction findById(UUID id);
}
