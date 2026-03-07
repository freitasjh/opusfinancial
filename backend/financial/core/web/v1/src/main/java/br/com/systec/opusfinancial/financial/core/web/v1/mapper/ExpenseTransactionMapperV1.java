package br.com.systec.opusfinancial.financial.core.web.v1.mapper;

import br.com.systec.opusfinancial.api.vo.CategoryVO;
import br.com.systec.opusfinancial.financial.api.vo.AccountVO;
import br.com.systec.opusfinancial.financial.api.vo.CategoryTransactionType;
import br.com.systec.opusfinancial.financial.api.vo.FinancialTransactionVO;
import br.com.systec.opusfinancial.financial.api.vo.TransactionType;
import br.com.systec.opusfinancial.financial.core.web.v1.dto.ExpenseTransactionInfoDTO;
import br.com.systec.opusfinancial.financial.core.web.v1.dto.ExpenseTransactionInputDTO;
import br.com.systec.opusfinancial.financial.core.web.v1.dto.ExpenseTransactionSaveResponseDTO;
import org.springframework.data.domain.Page;

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
        expenseTransaction.setTransactionType(TransactionType.EXPENSE);
        expenseTransaction.setCategoryTransactionType(CategoryTransactionType.PAYMENT);
        expenseTransaction.setDueDate(inputDTO.getDueDate());

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

    public ExpenseTransactionSaveResponseDTO toSaveResponse(FinancialTransactionVO financialTransaction) {
        ExpenseTransactionSaveResponseDTO saveResponse = new ExpenseTransactionSaveResponseDTO();
        saveResponse.setId(financialTransaction.getId());

        return saveResponse;
    }

    public ExpenseTransactionInfoDTO toInfoDTO(FinancialTransactionVO transactionVO) {
        ExpenseTransactionInfoDTO dto = new ExpenseTransactionInfoDTO();
        dto.setId(transactionVO.getId());
        dto.setDescription(transactionVO.getDescription());
//        dto.setCreateAt(transactionVO.getCreateAt());
        dto.setPaymentAt(transactionVO.getPaymentAt());
        dto.setAmount(transactionVO.getAmount());
        dto.setProcessed(transactionVO.getProcessed());
        dto.setAccount(transactionVO.getAccount().getAccountName());
        dto.setCategory(transactionVO.getCategory().getName());
        dto.setDueDate(transactionVO.getDueDate());

        return dto;
    }

    public ExpenseTransactionInputDTO toDTO(FinancialTransactionVO financialTransactionVO) {
        ExpenseTransactionInputDTO dto = new ExpenseTransactionInputDTO();
        dto.setId(financialTransactionVO.getId());
        dto.setDescription(financialTransactionVO.getDescription());
        dto.setCreateAt(financialTransactionVO.getCreateAt());
        dto.setPaymentAt(financialTransactionVO.getPaymentAt());
        dto.setAmount(financialTransactionVO.getAmount());
        dto.setProcessed(financialTransactionVO.getProcessed());
        dto.setAccountId(financialTransactionVO.getAccount().getId());
        dto.setCategoryId(financialTransactionVO.getCategory().getId());
        dto.setDueDate(financialTransactionVO.getDueDate());

        return dto;
    }

    public Page<ExpenseTransactionInfoDTO> toPageDTO(Page<FinancialTransactionVO> pageResult) {
        return pageResult.map(this::toInfoDTO);
    }
}
