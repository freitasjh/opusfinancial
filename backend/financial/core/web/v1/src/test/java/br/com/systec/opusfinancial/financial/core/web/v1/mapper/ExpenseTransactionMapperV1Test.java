package br.com.systec.opusfinancial.financial.core.web.v1.mapper;

import br.com.systec.opusfinancial.financial.api.vo.CategoryTransactionType;
import br.com.systec.opusfinancial.financial.api.vo.FinancialTransactionVO;
import br.com.systec.opusfinancial.financial.api.vo.TransactionType;
import br.com.systec.opusfinancial.financial.core.web.v1.dto.ExpenseTransactionInfoDTO;
import br.com.systec.opusfinancial.financial.core.web.v1.dto.ExpenseTransactionInputDTO;
import br.com.systec.opusfinancial.financial.core.web.v1.dto.ExpenseTransactionSaveResponseDTO;
import br.com.systec.opusfinancial.financial.core.web.v1.fake.ExpenseTransactionFake;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;

class ExpenseTransactionMapperV1Test {

    private final ExpenseTransactionMapperV1 mapper = ExpenseTransactionMapperV1.of();

    @Test
    void shouldMapInputDTOToVO() {
        ExpenseTransactionInputDTO inputDTO = ExpenseTransactionFake.toInputDTO();

        FinancialTransactionVO result = mapper.toVO(inputDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(inputDTO.getId(), result.getId());
        Assertions.assertEquals(inputDTO.getDescription(), result.getDescription());
        Assertions.assertEquals(inputDTO.getCreateAt(), result.getCreateAt());
        Assertions.assertEquals(inputDTO.getPaymentAt(), result.getPaymentAt());
        Assertions.assertEquals(inputDTO.getAmount(), result.getAmount());
        Assertions.assertEquals(inputDTO.getProcessed(), result.getProcessed());
        Assertions.assertEquals(TransactionType.EXPENSE, result.getTransactionType());
        Assertions.assertEquals(CategoryTransactionType.PAYMENT, result.getCategoryTransactionType());
        Assertions.assertEquals(inputDTO.getDueDate(), result.getDueDate());
        Assertions.assertNotNull(result.getAccount());
        Assertions.assertEquals(inputDTO.getAccountId(), result.getAccount().getId());
        Assertions.assertNotNull(result.getCategory());
        Assertions.assertEquals(inputDTO.getCategoryId(), result.getCategory().getId());
    }

    @Test
    void shouldMapVOToSaveResponse() {
        FinancialTransactionVO vo = ExpenseTransactionFake.toVO();

        ExpenseTransactionSaveResponseDTO result = mapper.toSaveResponse(vo);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(vo.getId(), result.getId());
    }

    @Test
    void shouldMapVOToInfoDTO() {
        FinancialTransactionVO vo = ExpenseTransactionFake.toVO();

        ExpenseTransactionInfoDTO result = mapper.toInfoDTO(vo);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(vo.getId(), result.getId());
        Assertions.assertEquals(vo.getDescription(), result.getDescription());
        Assertions.assertEquals(vo.getPaymentAt(), result.getPaymentAt());
        Assertions.assertEquals(vo.getAmount(), result.getAmount());
        Assertions.assertEquals(vo.getProcessed(), result.getProcessed());
        Assertions.assertEquals(vo.getAccount().getAccountName(), result.getAccount());
        Assertions.assertEquals(vo.getCategory().getName(), result.getCategory());
    }

    @Test
    void shouldMapVOToInputDTO() {
        FinancialTransactionVO vo = ExpenseTransactionFake.toVO();

        ExpenseTransactionInputDTO result = mapper.toDTO(vo);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(vo.getId(), result.getId());
        Assertions.assertEquals(vo.getDescription(), result.getDescription());
        Assertions.assertEquals(vo.getCreateAt(), result.getCreateAt());
        Assertions.assertEquals(vo.getPaymentAt(), result.getPaymentAt());
        Assertions.assertEquals(vo.getAmount(), result.getAmount());
        Assertions.assertEquals(vo.getProcessed(), result.getProcessed());
        Assertions.assertEquals(vo.getAccount().getId(), result.getAccountId());
        Assertions.assertEquals(vo.getCategory().getId(), result.getCategoryId());
    }

    @Test
    void shouldMapPageVOToPageInfoDTO() {
        FinancialTransactionVO vo = ExpenseTransactionFake.toVO();
        Page<FinancialTransactionVO> page = new PageImpl<>(Collections.singletonList(vo));

        Page<ExpenseTransactionInfoDTO> result = mapper.toPageDTO(page);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getTotalElements());
        ExpenseTransactionInfoDTO infoDTO = result.getContent().getFirst();
        Assertions.assertEquals(vo.getId(), infoDTO.getId());
        Assertions.assertEquals(vo.getDescription(), infoDTO.getDescription());
        Assertions.assertEquals(vo.getPaymentAt(), infoDTO.getPaymentAt());
        Assertions.assertEquals(vo.getAmount(), infoDTO.getAmount());
        Assertions.assertEquals(vo.getProcessed(), infoDTO.getProcessed());
        Assertions.assertEquals(vo.getAccount().getAccountName(), infoDTO.getAccount());
        Assertions.assertEquals(vo.getCategory().getName(), infoDTO.getCategory());
    }
}
