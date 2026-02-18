package br.com.systec.opusfinancial.financial.catalog.impl.fake;

import br.com.systec.opusfinancial.api.vo.CategoryVO;
import br.com.systec.opusfinancial.financial.api.vo.CategoryTransactionType;
import br.com.systec.opusfinancial.financial.api.vo.FinancialTransactionVO;
import br.com.systec.opusfinancial.financial.api.vo.TransactionType;
import br.com.systec.opusfinancial.financial.catalog.impl.domain.FinancialTransaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public class FinancialTransactionFake {

    public static FinancialTransaction toFake() {
        FinancialTransaction financialTransaction = new FinancialTransaction();
        financialTransaction.setId(UUID.randomUUID());
        financialTransaction.setCreateAt(new Date());
        financialTransaction.setAccountId(UUID.randomUUID());
        financialTransaction.setCategoryId(UUID.randomUUID());
        financialTransaction.setTransactionType(TransactionType.INCOMING);
        financialTransaction.setProcessedAt(LocalDateTime.now());
        financialTransaction.setPaymentAt(LocalDate.now());
        financialTransaction.setAmount(BigDecimal.valueOf(100));
        financialTransaction.setProcessed(true);
        financialTransaction.setProcessedAt(LocalDateTime.now());
        financialTransaction.setDescription("Teste de Transação");
        financialTransaction.setCategoryTransactionType(CategoryTransactionType.RECEIVER);
        financialTransaction.setDueDate(LocalDate.now());

        return financialTransaction;
    }

    public static FinancialTransactionVO toFakeVO() {
        FinancialTransactionVO financialTransaction = new FinancialTransactionVO();
        financialTransaction.setId(UUID.randomUUID());
        financialTransaction.setCreateAt(new Date());
        financialTransaction.setAccount(AccountFake.toFakeVO());
        CategoryVO categoryVO = new CategoryVO();
        categoryVO.setId(UUID.randomUUID());
        financialTransaction.setCategory(categoryVO);
        financialTransaction.setTransactionType(TransactionType.INCOMING);
        financialTransaction.setProcessedAt(LocalDateTime.now());
        financialTransaction.setPaymentAt(LocalDate.now());
        financialTransaction.setAmount(BigDecimal.valueOf(100));
        financialTransaction.setProcessed(true);
        financialTransaction.setProcessedAt(LocalDateTime.now());
        financialTransaction.setDescription("Teste de Transação");
        financialTransaction.setCategoryTransactionType(CategoryTransactionType.RECEIVER);
        financialTransaction.setDueDate(LocalDate.now());

        return financialTransaction;
    }
}
