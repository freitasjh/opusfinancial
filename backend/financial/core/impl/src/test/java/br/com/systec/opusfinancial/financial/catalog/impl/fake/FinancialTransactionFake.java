package br.com.systec.opusfinancial.financial.catalog.impl.fake;

import br.com.systec.opusfinancial.api.vo.CategoryVO;
import br.com.systec.opusfinancial.financial.api.vo.CategoryTransactionType;
import br.com.systec.opusfinancial.financial.api.vo.FinancialTransactionVO;
import br.com.systec.opusfinancial.financial.api.vo.TransactionType;
import br.com.systec.opusfinancial.financial.catalog.impl.entity.FinancialTransaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class FinancialTransactionFake {

    public static FinancialTransaction createIncomingTransaction() {
        FinancialTransaction financialTransaction = new FinancialTransaction();
        financialTransaction.setId(UUID.randomUUID());
        financialTransaction.setCreateAt(LocalDateTime.now());
        financialTransaction.setAccountId(UUID.randomUUID());
        financialTransaction.setCategoryId(UUID.randomUUID());
        financialTransaction.setTransactionType(TransactionType.INCOMING);
        financialTransaction.setProcessedAt(LocalDateTime.now());
        financialTransaction.setPaymentAt(LocalDate.now());
        financialTransaction.setAmount(BigDecimal.valueOf(100));
        financialTransaction.setProcessed(true);
        financialTransaction.setDescription("Incoming Transaction Test");
        financialTransaction.setCategoryTransactionType(CategoryTransactionType.RECEIVER);
        financialTransaction.setDueDate(LocalDate.now());

        return financialTransaction;
    }

    public static FinancialTransaction createExpenseTransaction() {
        FinancialTransaction financialTransaction = new FinancialTransaction();
        financialTransaction.setId(UUID.randomUUID());
        financialTransaction.setCreateAt(LocalDateTime.now());
        financialTransaction.setAccountId(UUID.randomUUID());
        financialTransaction.setCategoryId(UUID.randomUUID());
        financialTransaction.setTransactionType(TransactionType.EXPENSE);
        financialTransaction.setProcessedAt(LocalDateTime.now());
        financialTransaction.setPaymentAt(LocalDate.now());
        financialTransaction.setAmount(BigDecimal.valueOf(250));
        financialTransaction.setProcessed(false);
        financialTransaction.setDescription("Expense Transaction Test");
        financialTransaction.setCategoryTransactionType(CategoryTransactionType.PAYMENT);
        financialTransaction.setDueDate(LocalDate.now().plusDays(15));

        return financialTransaction;
    }

    public static FinancialTransactionVO createIncomingTransactionVO() {
        FinancialTransactionVO financialTransaction = new FinancialTransactionVO();
        financialTransaction.setId(UUID.randomUUID());
        financialTransaction.setCreateAt(LocalDateTime.now());
        financialTransaction.setAccount(AccountFake.toFakeVO());
        CategoryVO categoryVO = CategoryFake.toFakeVO();
        financialTransaction.setCategory(categoryVO);
        financialTransaction.setTransactionType(TransactionType.INCOMING);
        financialTransaction.setProcessedAt(LocalDateTime.now());
        financialTransaction.setPaymentAt(LocalDate.now());
        financialTransaction.setAmount(BigDecimal.valueOf(100));
        financialTransaction.setProcessed(true);
        financialTransaction.setDescription("Incoming Transaction VO Test");
        financialTransaction.setCategoryTransactionType(CategoryTransactionType.RECEIVER);
        financialTransaction.setDueDate(LocalDate.now());

        return financialTransaction;
    }

    public static FinancialTransactionVO createExpenseTransactionVO() {
        FinancialTransactionVO financialTransaction = new FinancialTransactionVO();
        financialTransaction.setId(UUID.randomUUID());
        financialTransaction.setCreateAt(LocalDateTime.now());
        financialTransaction.setAccount(AccountFake.toFakeVO());
        CategoryVO categoryVO = CategoryFake.toFakeVO();
        financialTransaction.setCategory(categoryVO);
        financialTransaction.setTransactionType(TransactionType.EXPENSE);
        financialTransaction.setProcessedAt(LocalDateTime.now());
        financialTransaction.setPaymentAt(LocalDate.now());
        financialTransaction.setAmount(BigDecimal.valueOf(300));
        financialTransaction.setProcessed(false);
        financialTransaction.setDescription("Expense Transaction VO Test");
        financialTransaction.setCategoryTransactionType(CategoryTransactionType.PAYMENT);
        financialTransaction.setDueDate(LocalDate.now().plusDays(20));

        return financialTransaction;
    }
}
