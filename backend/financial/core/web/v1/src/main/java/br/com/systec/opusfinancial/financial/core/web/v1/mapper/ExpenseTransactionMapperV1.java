package br.com.systec.opusfinancial.financial.core.web.v1.mapper;

import br.com.systec.opusfinancial.api.vo.CategoryVO;
import br.com.systec.opusfinancial.financial.api.vo.AccountVO;
import br.com.systec.opusfinancial.financial.api.vo.FinancialTransactionVO;
import br.com.systec.opusfinancial.financial.core.web.v1.dto.ExpenseTransactionInputDTO;

public class ExpenseTransactionMapperV1 {

    private ExpenseTransactionMapperV1(){}

    public static ExpenseTransactionMapperV1 of() {
        return new ExpenseTransactionMapperV1();
    }

    public FinancialTransactionVO toVO(ExpenseTransactionInputDTO inputDTO) {
        FinancialTransactionVO expenseTransaction = new FinancialTransactionVO();
        expenseTransaction.setId(inputDTO.getId());
        expenseTransaction.setDescription(inputDTO.getDescription());
        expenseTransaction.setCreateAt(inputDTO.getCreateAt());
        expenseTransaction.setPaymentAt(inputDTO.getPaymentAt());
        expenseTransaction.setAmount(inputDTO.getAmount());
        expenseTransaction.setProcessed(inputDTO.getProcessed());

        if (inputDTO.getAccountId() != null) {
            expenseTransaction.setAccount(new AccountVO());
            expenseTransaction.getAccount().setId(inputDTO.getAccountId());
        }
        if (inputDTO.getCategoryId() != null) {
            expenseTransaction.setCategory(new CategoryVO());
            expenseTransaction.getCategory().setId(inputDTO.getCategoryId());
        }

        return expenseTransaction;
    }
}
