package br.com.systec.opusfinancial.financial.api.service;

import br.com.systec.opusfinancial.financial.api.filter.ExpenseTransactionFilter;
import br.com.systec.opusfinancial.financial.api.domain.FinancialTransaction;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ExpenseTransactionService {

    FinancialTransaction create(FinancialTransaction financialTransaction);

    void delete(UUID expenseTransactionId);

    Page<FinancialTransaction> findByFilter(ExpenseTransactionFilter filter);

    FinancialTransaction findById(UUID expenseId);
}
