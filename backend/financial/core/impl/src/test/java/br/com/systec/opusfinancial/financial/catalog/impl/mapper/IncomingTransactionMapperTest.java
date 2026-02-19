package br.com.systec.opusfinancial.financial.catalog.impl.mapper;

import br.com.systec.opusfinancial.api.vo.CategoryVO;
import br.com.systec.opusfinancial.financial.api.vo.AccountVO;
import br.com.systec.opusfinancial.financial.api.vo.FinancialTransactionVO;
import br.com.systec.opusfinancial.financial.catalog.impl.domain.FinancialTransaction;
import br.com.systec.opusfinancial.financial.catalog.impl.fake.AccountFake;
import br.com.systec.opusfinancial.financial.catalog.impl.fake.CategoryFake;
import br.com.systec.opusfinancial.financial.catalog.impl.fake.FinancialTransactionFake;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class IncomingTransactionMapperTest {

    @Test
    void when_converterIncomingTransaction_to_IncomingTransactionVO() {
        // Given
        FinancialTransaction transactionToConverter = FinancialTransactionFake.toFake();

        // When
        FinancialTransactionVO transactionConverted = IncomingTransactionMapper.of().toVO(transactionToConverter);

        // Then
        assertNotNull(transactionConverted);
        assertEquals(transactionToConverter.getId(), transactionConverted.getId());
        assertEquals(transactionToConverter.getDescription(), transactionConverted.getDescription());
        assertEquals(transactionToConverter.getCategoryTransactionType(), transactionConverted.getCategoryTransactionType());
        assertEquals(transactionToConverter.getProcessed(), transactionConverted.getProcessed());
        assertEquals(transactionToConverter.getDueDate(), transactionConverted.getDueDate());
        assertEquals(transactionToConverter.getAmount(), transactionConverted.getAmount());
        assertEquals(transactionToConverter.getPaymentAt(), transactionConverted.getPaymentAt());
        assertEquals(transactionToConverter.getTransactionType(), transactionConverted.getTransactionType());
        assertEquals(transactionToConverter.getProcessedAt(), transactionConverted.getProcessedAt());
        assertEquals(transactionToConverter.getCreateAt(), transactionConverted.getCreateAt());
        assertNotNull(transactionConverted.getAccount());
        assertEquals(transactionToConverter.getAccountId(), transactionConverted.getAccount().getId());
        assertNotNull(transactionConverted.getCategory());
        assertEquals(transactionToConverter.getCategoryId(), transactionConverted.getCategory().getId());
    }

    @Test
    void when_converterIncomingTransactionVO_to_IncomingTransaction() {
        // Given
        FinancialTransactionVO transactionToConverter = FinancialTransactionFake.toFakeVO();

        // When
        FinancialTransaction transactionConverted = IncomingTransactionMapper.of().toEntity(transactionToConverter);

        // Then
        assertNotNull(transactionConverted);
        assertEquals(transactionToConverter.getId(), transactionConverted.getId());
        assertEquals(transactionToConverter.getDescription(), transactionConverted.getDescription());
        assertEquals(transactionToConverter.getCategoryTransactionType(), transactionConverted.getCategoryTransactionType());
        assertEquals(transactionToConverter.getProcessed(), transactionConverted.getProcessed());
        assertEquals(transactionToConverter.getProcessedAt(), transactionConverted.getProcessedAt());
        assertEquals(transactionToConverter.getDueDate(), transactionConverted.getDueDate());
        assertEquals(transactionToConverter.getAmount(), transactionConverted.getAmount());
        assertEquals(transactionToConverter.getPaymentAt(), transactionConverted.getPaymentAt());
        assertEquals(transactionToConverter.getTransactionType(), transactionConverted.getTransactionType());
        assertEquals(transactionToConverter.getAccount().getId(), transactionConverted.getAccountId());
        assertEquals(transactionToConverter.getCategory().getId(), transactionConverted.getCategoryId());
    }

    @Test
    void when_converterPageTransaction_to_PageTransactionVO() {
        // Given
        UUID accountId = UUID.randomUUID();
        Page<FinancialTransaction> page = new PageImpl<>(List.of(FinancialTransactionFake.toFake()));
        page.getContent().getFirst().setAccountId(accountId);

        List<AccountVO> listFakeAccount = AccountFake.toFakeVOs();
        listFakeAccount.getFirst().setId(accountId);

        List<CategoryVO> listCategoryFake = List.of(CategoryFake.toFake());

        // When
        Page<FinancialTransactionVO> pageConverted = IncomingTransactionMapper.of()
                .toPage(page, listFakeAccount, listCategoryFake);

        // Then
        assertEquals(1, pageConverted.getTotalElements());
        FinancialTransactionVO transactionVO = pageConverted.getContent().getFirst();

        assertNotNull(transactionVO.getAccount());
        assertEquals(listFakeAccount.getFirst().getAccountName(), transactionVO.getAccount().getAccountName());
        assertEquals(listFakeAccount.getFirst().getId(), transactionVO.getAccount().getId());
    }
}
