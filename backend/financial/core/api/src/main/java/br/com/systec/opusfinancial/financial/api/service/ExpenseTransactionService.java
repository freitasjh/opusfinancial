package br.com.systec.opusfinancial.financial.api.service;

import br.com.systec.opusfinancial.financial.api.filter.ExpenseTransactionFilter;
import br.com.systec.opusfinancial.financial.api.vo.FinancialTransactionVO;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ExpenseTransactionService {

    FinancialTransactionVO create(FinancialTransactionVO financialTransaction);

    void delete(UUID expenseTransactionId);

    Page<FinancialTransactionVO> findByFilter(ExpenseTransactionFilter filter);

    FinancialTransactionVO findById(UUID expenseId);
}
