package br.com.systec.opusfinancial.financial.catalog.impl.fake;

import br.com.systec.opusfinancial.api.domain.Category;
import br.com.systec.opusfinancial.financial.api.domain.CategoryTransactionType;
import br.com.systec.opusfinancial.financial.api.domain.FinancialTransaction;
import br.com.systec.opusfinancial.financial.api.domain.TransactionType;
import br.com.systec.opusfinancial.financial.catalog.impl.entity.FinancialTransactionEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class FinancialTransactionFake {

    public static FinancialTransactionEntity createIncomingTransaction() {
        FinancialTransactionEntity financialTransaction = new FinancialTransactionEntity();
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

    public static FinancialTransactionEntity createExpenseTransaction() {
        FinancialTransactionEntity financialTransaction = new FinancialTransactionEntity();
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

    public static FinancialTransaction createIncomingTransactionVO() {
        FinancialTransaction financialTransaction = new FinancialTransaction();
        financialTransaction.setId(UUID.randomUUID());
        financialTransaction.setCreateAt(LocalDateTime.now());
        financialTransaction.setAccount(AccountFake.toFakeVO());
        Category categoryVO = CategoryFake.toFakeVO();
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

    public static FinancialTransaction createExpenseTransactionVO() {
        FinancialTransaction financialTransaction = new FinancialTransaction();
        financialTransaction.setId(UUID.randomUUID());
        financialTransaction.setCreateAt(LocalDateTime.now());
        financialTransaction.setAccount(AccountFake.toFakeVO());
        Category categoryVO = CategoryFake.toFakeVO();
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
