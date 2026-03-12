package br.com.systec.opusfinancial.financial.catalog.impl.mapper;

import br.com.systec.opusfinancial.api.domain.Category;
import br.com.systec.opusfinancial.financial.api.domain.Account;
import br.com.systec.opusfinancial.financial.api.domain.CategoryTransactionType;
import br.com.systec.opusfinancial.financial.api.domain.FinancialTransaction;
import br.com.systec.opusfinancial.financial.api.domain.TransactionType;
import br.com.systec.opusfinancial.financial.catalog.impl.entity.FinancialTransactionEntity;
import br.com.systec.opusfinancial.financial.catalog.impl.fake.AccountFake;
import br.com.systec.opusfinancial.financial.catalog.impl.fake.CategoryFake;
import br.com.systec.opusfinancial.financial.catalog.impl.fake.FinancialTransactionFake;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;
import java.util.List;

class FinancialTransactionMapperTest {

    private final FinancialTransactionDomainMapper mapper = FinancialTransactionDomainMapper.INSTANCE;

    @Test
    void shouldMapIncomingVOToEntity() {
        FinancialTransaction financialTransaction = FinancialTransactionFake.createIncomingTransactionVO();

        FinancialTransactionEntity financialTransactionEntity = mapper.toEntity(financialTransaction, TransactionType.INCOMING, CategoryTransactionType.RECEIVER);

        Assertions.assertNotNull(financialTransactionEntity);
        Assertions.assertEquals(financialTransaction.getId(), financialTransactionEntity.getId());
        Assertions.assertEquals(financialTransaction.getDescription(), financialTransactionEntity.getDescription());
        Assertions.assertEquals(financialTransaction.getProcessed(), financialTransactionEntity.getProcessed());
        Assertions.assertEquals(financialTransaction.getProcessedAt(), financialTransactionEntity.getProcessedAt());
        Assertions.assertEquals(financialTransaction.getDueDate(), financialTransactionEntity.getDueDate());
        Assertions.assertEquals(financialTransaction.getAmount(), financialTransactionEntity.getAmount());
        Assertions.assertEquals(financialTransaction.getPaymentAt(), financialTransactionEntity.getPaymentAt());
        Assertions.assertEquals(TransactionType.INCOMING, financialTransactionEntity.getTransactionType());
        Assertions.assertEquals(CategoryTransactionType.RECEIVER, financialTransactionEntity.getCategoryTransactionType());
        Assertions.assertEquals(financialTransaction.getAccount().getId(), financialTransactionEntity.getAccountId());
        Assertions.assertEquals(financialTransaction.getCategory().getId(), financialTransactionEntity.getCategoryId());
    }

    @Test
    void shouldMapExpenseVOToEntity() {
        FinancialTransaction financialTransaction = FinancialTransactionFake.createExpenseTransactionVO();

        FinancialTransactionEntity entity = mapper.toEntity(financialTransaction, TransactionType.EXPENSE, CategoryTransactionType.PAYMENT);

        Assertions.assertNotNull(entity);
        Assertions.assertEquals(financialTransaction.getId(), entity.getId());
        Assertions.assertEquals(financialTransaction.getDescription(), entity.getDescription());
        Assertions.assertEquals(financialTransaction.getProcessed(), entity.getProcessed());
        Assertions.assertEquals(financialTransaction.getProcessedAt(), entity.getProcessedAt());
        Assertions.assertEquals(financialTransaction.getDueDate(), entity.getDueDate());
        Assertions.assertEquals(financialTransaction.getAmount(), entity.getAmount());
        Assertions.assertEquals(financialTransaction.getPaymentAt(), entity.getPaymentAt());
        Assertions.assertEquals(TransactionType.EXPENSE, entity.getTransactionType());
        Assertions.assertEquals(CategoryTransactionType.PAYMENT, entity.getCategoryTransactionType());
        Assertions.assertEquals(financialTransaction.getAccount().getId(), entity.getAccountId());
        Assertions.assertEquals(financialTransaction.getCategory().getId(), entity.getCategoryId());
    }

    @Test
    void shouldMapIncomingEntityToVO() {
        FinancialTransactionEntity entity = FinancialTransactionFake.createIncomingTransaction();

        FinancialTransaction financialTransaction = mapper.toVO(entity);

        Assertions.assertNotNull(financialTransaction);
        Assertions.assertEquals(entity.getId(), financialTransaction.getId());
        Assertions.assertEquals(entity.getDescription(), financialTransaction.getDescription());
        Assertions.assertEquals(entity.getCategoryTransactionType(), financialTransaction.getCategoryTransactionType());
        Assertions.assertEquals(entity.getProcessed(), financialTransaction.getProcessed());
        Assertions.assertEquals(entity.getDueDate(), financialTransaction.getDueDate());
        Assertions.assertEquals(entity.getAmount(), financialTransaction.getAmount());
        Assertions.assertEquals(entity.getPaymentAt(), financialTransaction.getPaymentAt());
        Assertions.assertEquals(entity.getTransactionType(), financialTransaction.getTransactionType());
        Assertions.assertEquals(entity.getProcessedAt(), financialTransaction.getProcessedAt());
        Assertions.assertEquals(entity.getCreateAt(), financialTransaction.getCreateAt());
        Assertions.assertNotNull(financialTransaction.getAccount());
        Assertions.assertEquals(entity.getAccountId(), financialTransaction.getAccount().getId());
        Assertions.assertNotNull(financialTransaction.getCategory());
        Assertions.assertEquals(entity.getCategoryId(), financialTransaction.getCategory().getId());
    }

    @Test
    void shouldMapExpenseEntityToVO() {
        FinancialTransactionEntity entity = FinancialTransactionFake.createExpenseTransaction();

        FinancialTransaction financialTransaction = mapper.toVO(entity);

        Assertions.assertNotNull(financialTransaction);
        Assertions.assertEquals(entity.getId(), financialTransaction.getId());
        Assertions.assertEquals(entity.getDescription(), financialTransaction.getDescription());
        Assertions.assertEquals(entity.getCategoryTransactionType(), financialTransaction.getCategoryTransactionType());
        Assertions.assertEquals(entity.getProcessed(), financialTransaction.getProcessed());
        Assertions.assertEquals(entity.getDueDate(), financialTransaction.getDueDate());
        Assertions.assertEquals(entity.getAmount(), financialTransaction.getAmount());
        Assertions.assertEquals(entity.getPaymentAt(), financialTransaction.getPaymentAt());
        Assertions.assertEquals(entity.getTransactionType(), financialTransaction.getTransactionType());
        Assertions.assertEquals(entity.getProcessedAt(), financialTransaction.getProcessedAt());
        Assertions.assertEquals(entity.getCreateAt(), financialTransaction.getCreateAt());
        Assertions.assertNotNull(financialTransaction.getAccount());
        Assertions.assertEquals(entity.getAccountId(), financialTransaction.getAccount().getId());
        Assertions.assertNotNull(financialTransaction.getCategory());
        Assertions.assertEquals(entity.getCategoryId(), financialTransaction.getCategory().getId());
    }

    @Test
    void shouldUpdateEntityFromVO() {
        FinancialTransactionEntity updatedEntity = FinancialTransactionFake.createIncomingTransaction();
        FinancialTransaction financialTransaction = FinancialTransactionFake.createExpenseTransactionVO(); // Use different data to verify update

        mapper.updateEntityFromVO(updatedEntity, financialTransaction);

        Assertions.assertNotNull(updatedEntity);
        Assertions.assertEquals(financialTransaction.getDescription(), updatedEntity.getDescription());
        Assertions.assertEquals(financialTransaction.getProcessed(), updatedEntity.getProcessed());
        Assertions.assertEquals(financialTransaction.getProcessedAt(), updatedEntity.getProcessedAt());
        Assertions.assertEquals(financialTransaction.getDueDate(), updatedEntity.getDueDate());
        Assertions.assertEquals(financialTransaction.getAmount(), updatedEntity.getAmount());
        Assertions.assertEquals(financialTransaction.getPaymentAt(), updatedEntity.getPaymentAt());
        Assertions.assertEquals(financialTransaction.getAccount().getId(), updatedEntity.getAccountId());
        Assertions.assertEquals(financialTransaction.getCategory().getId(), updatedEntity.getCategoryId());
    }

    @Test
    void shouldMapPageEntityToPageVO() {
        FinancialTransactionEntity entity = FinancialTransactionFake.createIncomingTransaction();
        Page<FinancialTransactionEntity> page = new PageImpl<>(Collections.singletonList(entity));
        
        Account accountVO = AccountFake.toFakeVO();
        accountVO.setId(entity.getAccountId()); // Ensure ID matches
        List<Account> accounts = Collections.singletonList(accountVO);
        
        Category categoryVO = CategoryFake.toFakeVO();
        categoryVO.setId(entity.getCategoryId()); // Ensure ID matches
        List<Category> categories = Collections.singletonList(categoryVO);

        Page<FinancialTransaction> resultPage = mapper.toPage(page, accounts, categories);

        Assertions.assertNotNull(resultPage);
        Assertions.assertEquals(1, resultPage.getTotalElements());
        FinancialTransaction resultVO = resultPage.getContent().get(0);

        Assertions.assertEquals(entity.getId(), resultVO.getId());
        Assertions.assertEquals(entity.getDescription(), resultVO.getDescription());
        Assertions.assertEquals(accountVO, resultVO.getAccount());
        Assertions.assertEquals(categoryVO, resultVO.getCategory());
    }
}
