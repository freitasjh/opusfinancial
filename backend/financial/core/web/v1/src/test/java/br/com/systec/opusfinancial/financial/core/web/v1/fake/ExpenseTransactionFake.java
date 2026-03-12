package br.com.systec.opusfinancial.financial.core.web.v1.fake;

import br.com.systec.opusfinancial.api.domain.Category;
import br.com.systec.opusfinancial.financial.api.domain.Account;
import br.com.systec.opusfinancial.financial.api.domain.CategoryTransactionType;
import br.com.systec.opusfinancial.financial.api.domain.FinancialTransaction;
import br.com.systec.opusfinancial.financial.api.domain.TransactionType;
import br.com.systec.opusfinancial.financial.core.web.v1.dto.ExpenseTransactionInputDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class ExpenseTransactionFake {

    public static ExpenseTransactionInputDTO toInputDTO() {
        ExpenseTransactionInputDTO dto = new ExpenseTransactionInputDTO();
        dto.setId(UUID.randomUUID());
        dto.setDescription("Expense Description");
        dto.setAmount(BigDecimal.valueOf(150.00));
        dto.setDueDate(LocalDate.now().plusDays(5));
        dto.setCreateAt(LocalDateTime.now());
        dto.setPaymentAt(LocalDate.now());
        dto.setProcessed(true);
        dto.setAccountId(UUID.randomUUID());
        dto.setCategoryId(UUID.randomUUID());
        return dto;
    }

    public static FinancialTransaction toVO() {
        FinancialTransaction vo = new FinancialTransaction();
        vo.setId(UUID.randomUUID());
        vo.setDescription("Expense Description VO");
        vo.setCreateAt(LocalDateTime.now());
        vo.setPaymentAt(LocalDate.now());
        vo.setAmount(BigDecimal.valueOf(200.00));
        vo.setProcessed(true);
        vo.setTransactionType(TransactionType.EXPENSE);
        vo.setCategoryTransactionType(CategoryTransactionType.PAYMENT);
        vo.setDueDate(LocalDate.now().plusDays(10));

        Account account = new Account();
        account.setId(UUID.randomUUID());
        account.setAccountName("Test Account");
        vo.setAccount(account);

        Category category = new Category();
        category.setId(UUID.randomUUID());
        category.setName("Test Category");
        vo.setCategory(category);

        return vo;
    }
}
