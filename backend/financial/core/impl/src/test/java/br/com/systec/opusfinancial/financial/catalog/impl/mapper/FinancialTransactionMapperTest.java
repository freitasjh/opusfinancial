package br.com.systec.opusfinancial.financial.catalog.impl.mapper;

import br.com.systec.opusfinancial.api.vo.CategoryVO;
import br.com.systec.opusfinancial.financial.api.vo.AccountVO;
import br.com.systec.opusfinancial.financial.api.vo.CategoryTransactionType;
import br.com.systec.opusfinancial.financial.api.vo.FinancialTransactionVO;
import br.com.systec.opusfinancial.financial.api.vo.TransactionType;
import br.com.systec.opusfinancial.financial.catalog.impl.entity.FinancialTransaction;
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

    private final FinancialTransactionMapper mapper = FinancialTransactionMapper.of();

    @Test
    void shouldMapIncomingVOToEntity() {
        FinancialTransactionVO vo = FinancialTransactionFake.createIncomingTransactionVO();

        FinancialTransaction entity = mapper.toEntity(vo, TransactionType.INCOMING, CategoryTransactionType.RECEIVER);

        Assertions.assertNotNull(entity);
        Assertions.assertEquals(vo.getId(), entity.getId());
        Assertions.assertEquals(vo.getDescription(), entity.getDescription());
        Assertions.assertEquals(vo.getProcessed(), entity.getProcessed());
        Assertions.assertEquals(vo.getProcessedAt(), entity.getProcessedAt());
        Assertions.assertEquals(vo.getDueDate(), entity.getDueDate());
        Assertions.assertEquals(vo.getAmount(), entity.getAmount());
        Assertions.assertEquals(vo.getPaymentAt(), entity.getPaymentAt());
        Assertions.assertEquals(TransactionType.INCOMING, entity.getTransactionType());
        Assertions.assertEquals(CategoryTransactionType.RECEIVER, entity.getCategoryTransactionType());
        Assertions.assertEquals(vo.getAccount().getId(), entity.getAccountId());
        Assertions.assertEquals(vo.getCategory().getId(), entity.getCategoryId());
    }

    @Test
    void shouldMapExpenseVOToEntity() {
        FinancialTransactionVO vo = FinancialTransactionFake.createExpenseTransactionVO();

        FinancialTransaction entity = mapper.toEntity(vo, TransactionType.EXPENSE, CategoryTransactionType.PAYMENT);

        Assertions.assertNotNull(entity);
        Assertions.assertEquals(vo.getId(), entity.getId());
        Assertions.assertEquals(vo.getDescription(), entity.getDescription());
        Assertions.assertEquals(vo.getProcessed(), entity.getProcessed());
        Assertions.assertEquals(vo.getProcessedAt(), entity.getProcessedAt());
        Assertions.assertEquals(vo.getDueDate(), entity.getDueDate());
        Assertions.assertEquals(vo.getAmount(), entity.getAmount());
        Assertions.assertEquals(vo.getPaymentAt(), entity.getPaymentAt());
        Assertions.assertEquals(TransactionType.EXPENSE, entity.getTransactionType());
        Assertions.assertEquals(CategoryTransactionType.PAYMENT, entity.getCategoryTransactionType());
        Assertions.assertEquals(vo.getAccount().getId(), entity.getAccountId());
        Assertions.assertEquals(vo.getCategory().getId(), entity.getCategoryId());
    }

    @Test
    void shouldMapIncomingEntityToVO() {
        FinancialTransaction entity = FinancialTransactionFake.createIncomingTransaction();

        FinancialTransactionVO vo = mapper.toVO(entity);

        Assertions.assertNotNull(vo);
        Assertions.assertEquals(entity.getId(), vo.getId());
        Assertions.assertEquals(entity.getDescription(), vo.getDescription());
        Assertions.assertEquals(entity.getCategoryTransactionType(), vo.getCategoryTransactionType());
        Assertions.assertEquals(entity.getProcessed(), vo.getProcessed());
        Assertions.assertEquals(entity.getDueDate(), vo.getDueDate());
        Assertions.assertEquals(entity.getAmount(), vo.getAmount());
        Assertions.assertEquals(entity.getPaymentAt(), vo.getPaymentAt());
        Assertions.assertEquals(entity.getTransactionType(), vo.getTransactionType());
        Assertions.assertEquals(entity.getProcessedAt(), vo.getProcessedAt());
        Assertions.assertEquals(entity.getCreateAt(), vo.getCreateAt());
        Assertions.assertNotNull(vo.getAccount());
        Assertions.assertEquals(entity.getAccountId(), vo.getAccount().getId());
        Assertions.assertNotNull(vo.getCategory());
        Assertions.assertEquals(entity.getCategoryId(), vo.getCategory().getId());
    }

    @Test
    void shouldMapExpenseEntityToVO() {
        FinancialTransaction entity = FinancialTransactionFake.createExpenseTransaction();

        FinancialTransactionVO vo = mapper.toVO(entity);

        Assertions.assertNotNull(vo);
        Assertions.assertEquals(entity.getId(), vo.getId());
        Assertions.assertEquals(entity.getDescription(), vo.getDescription());
        Assertions.assertEquals(entity.getCategoryTransactionType(), vo.getCategoryTransactionType());
        Assertions.assertEquals(entity.getProcessed(), vo.getProcessed());
        Assertions.assertEquals(entity.getDueDate(), vo.getDueDate());
        Assertions.assertEquals(entity.getAmount(), vo.getAmount());
        Assertions.assertEquals(entity.getPaymentAt(), vo.getPaymentAt());
        Assertions.assertEquals(entity.getTransactionType(), vo.getTransactionType());
        Assertions.assertEquals(entity.getProcessedAt(), vo.getProcessedAt());
        Assertions.assertEquals(entity.getCreateAt(), vo.getCreateAt());
        Assertions.assertNotNull(vo.getAccount());
        Assertions.assertEquals(entity.getAccountId(), vo.getAccount().getId());
        Assertions.assertNotNull(vo.getCategory());
        Assertions.assertEquals(entity.getCategoryId(), vo.getCategory().getId());
    }

    @Test
    void shouldUpdateEntityFromVO() {
        FinancialTransaction entity = FinancialTransactionFake.createIncomingTransaction();
        FinancialTransactionVO vo = FinancialTransactionFake.createExpenseTransactionVO(); // Use different data to verify update

        FinancialTransaction updatedEntity = mapper.updateEntityFromVO(entity, vo);

        Assertions.assertNotNull(updatedEntity);
        Assertions.assertEquals(vo.getDescription(), updatedEntity.getDescription());
        Assertions.assertEquals(vo.getProcessed(), updatedEntity.getProcessed());
        Assertions.assertEquals(vo.getProcessedAt(), updatedEntity.getProcessedAt());
        Assertions.assertEquals(vo.getDueDate(), updatedEntity.getDueDate());
        Assertions.assertEquals(vo.getAmount(), updatedEntity.getAmount());
        Assertions.assertEquals(vo.getPaymentAt(), updatedEntity.getPaymentAt());
        Assertions.assertEquals(vo.getAccount().getId(), updatedEntity.getAccountId());
        Assertions.assertEquals(vo.getCategory().getId(), updatedEntity.getCategoryId());
    }

    @Test
    void shouldMapPageEntityToPageVO() {
        FinancialTransaction entity = FinancialTransactionFake.createIncomingTransaction();
        Page<FinancialTransaction> page = new PageImpl<>(Collections.singletonList(entity));
        
        AccountVO accountVO = AccountFake.toFakeVO();
        accountVO.setId(entity.getAccountId()); // Ensure ID matches
        List<AccountVO> accounts = Collections.singletonList(accountVO);
        
        CategoryVO categoryVO = CategoryFake.toFakeVO();
        categoryVO.setId(entity.getCategoryId()); // Ensure ID matches
        List<CategoryVO> categories = Collections.singletonList(categoryVO);

        Page<FinancialTransactionVO> resultPage = mapper.toPage(page, accounts, categories);

        Assertions.assertNotNull(resultPage);
        Assertions.assertEquals(1, resultPage.getTotalElements());
        FinancialTransactionVO resultVO = resultPage.getContent().get(0);

        Assertions.assertEquals(entity.getId(), resultVO.getId());
        Assertions.assertEquals(entity.getDescription(), resultVO.getDescription());
        Assertions.assertEquals(accountVO, resultVO.getAccount());
        Assertions.assertEquals(categoryVO, resultVO.getCategory());
    }
}
