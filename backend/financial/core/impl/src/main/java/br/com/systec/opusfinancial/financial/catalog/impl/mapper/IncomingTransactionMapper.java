package br.com.systec.opusfinancial.financial.catalog.impl.mapper;

import br.com.systec.opusfinancial.api.vo.CategoryVO;
import br.com.systec.opusfinancial.financial.api.vo.AccountVO;
import br.com.systec.opusfinancial.financial.api.vo.CategoryTransactionType;
import br.com.systec.opusfinancial.financial.api.vo.FinancialTransactionVO;
import br.com.systec.opusfinancial.financial.api.vo.TransactionType;
import br.com.systec.opusfinancial.financial.catalog.impl.domain.FinancialTransaction;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

public class IncomingTransactionMapper {

    private IncomingTransactionMapper() {
    }

    public static IncomingTransactionMapper of() {
        return new IncomingTransactionMapper();
    }

    public FinancialTransaction toEntity(FinancialTransactionVO transactionVO) {
        FinancialTransaction transaction = new FinancialTransaction();
        transaction.setId(transactionVO.getId());
        transaction.setDescription(transactionVO.getDescription());
        transaction.setProcessed(transactionVO.getProcessed());
        transaction.setProcessedAt(transactionVO.getProcessedAt());
        transaction.setDueDate(transactionVO.getDueDate());
        transaction.setAmount(transactionVO.getAmount());
        transaction.setPaymentAt(transactionVO.getPaymentAt());
        transaction.setTransactionType(TransactionType.INCOMING);
        transaction.setCategoryTransactionType(CategoryTransactionType.RECEIVER);

        if (transactionVO.getAccount() != null) {
            transaction.setAccountId(transactionVO.getAccount().getId());
        }

        if (transactionVO.getCategory() != null) {
            transaction.setCategoryId(transactionVO.getCategory().getId());
        }

        return transaction;
    }

    public FinancialTransactionVO toVO(FinancialTransaction transaction) {
        FinancialTransactionVO transactionVO = new FinancialTransactionVO();
        transactionVO.setId(transaction.getId());
        transactionVO.setDescription(transaction.getDescription());
        transactionVO.setCategoryTransactionType(transaction.getCategoryTransactionType());
        transactionVO.setProcessed(transaction.getProcessed());
        transactionVO.setDueDate(transaction.getDueDate());
        transactionVO.setAmount(transaction.getAmount());
        transactionVO.setPaymentAt(transaction.getPaymentAt());
        transactionVO.setTransactionType(transaction.getTransactionType());
        transactionVO.setProcessedAt(transaction.getProcessedAt());
        transactionVO.setCreateAt(transaction.getCreateAt());

        if (transaction.getAccountId() != null) {
            transactionVO.setAccount(new AccountVO());
            transactionVO.getAccount().setId(transaction.getAccountId());
        }

        if (transaction.getCategoryId() != null) {
            transactionVO.setCategory(new CategoryVO());
            transactionVO.getCategory().setId(transaction.getCategoryId());
        }

        return transactionVO;
    }

    public FinancialTransaction updateEntityFromVO(FinancialTransaction transaction, FinancialTransactionVO transactionVO) {
        transaction.setDescription(transactionVO.getDescription());
        transaction.setProcessed(transactionVO.getProcessed());
        transaction.setProcessedAt(transactionVO.getProcessedAt());
        transaction.setDueDate(transactionVO.getDueDate());
        transaction.setAmount(transactionVO.getAmount());
        transaction.setPaymentAt(transactionVO.getPaymentAt());
        transaction.setTransactionType(TransactionType.INCOMING);
        transaction.setCategoryTransactionType(CategoryTransactionType.RECEIVER);

        if (transactionVO.getAccount() != null && !Objects.equals(transactionVO.getAccount().getId(), transaction.getAccountId())) {
            transaction.setAccountId(transactionVO.getAccount().getId());
        }

        if (transactionVO.getCategory() != null && !Objects.equals(transactionVO.getCategory().getId(), transaction.getCategoryId())) {
            transaction.setCategoryId(transactionVO.getCategory().getId());
        }

        return transaction;
    }

    public Page<FinancialTransactionVO> toPage(Page<FinancialTransaction> pageResult, List<AccountVO> listOfAccount,
                                               List<CategoryVO> listOfCategory) {
        Map<UUID, AccountVO> accountMap = listOfAccount.stream()
                .filter(item -> item.getId() != null)
                .collect(Collectors.toMap(AccountVO::getId, Function.identity(), (existing, replacement) -> existing));
        Map<UUID, CategoryVO> categoryMap = listOfCategory.stream()
                .filter(item -> item.getId() != null)
                .collect(Collectors.toMap(CategoryVO::getId, Function.identity(), (existing, replacement) -> existing));

        return pageResult.map(item -> {
            AccountVO account = accountMap.get(item.getAccountId());
            CategoryVO categoryVO = categoryMap.get(item.getCategoryId());

            FinancialTransactionVO financialTransactionVO = toVO(item);
            financialTransactionVO.setAccount(account);
            financialTransactionVO.setCategory(categoryVO);

            return financialTransactionVO;
        });
    }
}
