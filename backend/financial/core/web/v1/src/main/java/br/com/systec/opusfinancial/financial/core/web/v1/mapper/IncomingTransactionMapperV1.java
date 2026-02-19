package br.com.systec.opusfinancial.financial.core.web.v1.mapper;

import br.com.systec.opusfinancial.api.vo.CategoryVO;
import br.com.systec.opusfinancial.financial.api.vo.AccountVO;
import br.com.systec.opusfinancial.financial.api.vo.FinancialTransactionVO;
import br.com.systec.opusfinancial.financial.core.web.v1.dto.IncomingFinancialInputDTO;
import br.com.systec.opusfinancial.financial.core.web.v1.dto.IncomingInformationResponseDTO;
import br.com.systec.opusfinancial.financial.core.web.v1.dto.IncomingSaveResponseDTO;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public class IncomingTransactionMapperV1 {

    private IncomingTransactionMapperV1() {
    }

    public static IncomingTransactionMapperV1 of() {
        return new IncomingTransactionMapperV1();
    }

    public FinancialTransactionVO toVO(IncomingFinancialInputDTO inputDTO) {
        FinancialTransactionVO financialTransaction = new FinancialTransactionVO();
        financialTransaction.setId(inputDTO.getId());
        financialTransaction.setDescription(inputDTO.getDescription());
        financialTransaction.setAmount(inputDTO.getAmount());
        financialTransaction.setPaymentAt(inputDTO.getPaymentAt());
        financialTransaction.setProcessedAt(inputDTO.getProcessedAt());
        financialTransaction.setProcessed(inputDTO.isProcessed());

        financialTransaction.setAccount(new AccountVO());
        financialTransaction.getAccount().setId(inputDTO.getAccountId());
        financialTransaction.setCategory(new CategoryVO());
        financialTransaction.getCategory().setId(inputDTO.getCategoryId());

        return financialTransaction;
    }

    public IncomingSaveResponseDTO toSaveResponse(FinancialTransactionVO financialTransactionVO) {
        IncomingSaveResponseDTO saveResponse = new IncomingSaveResponseDTO();
        saveResponse.setId(financialTransactionVO.getId());

        return saveResponse;
    }

    public IncomingInformationResponseDTO toInformationDTO(FinancialTransactionVO transaction) {

        IncomingInformationResponseDTO incomingInformationResponseDTO = new IncomingInformationResponseDTO();
        incomingInformationResponseDTO.setId(transaction.getId());
        incomingInformationResponseDTO.setDescription(transaction.getDescription());
        incomingInformationResponseDTO.setAmount(transaction.getAmount());
        incomingInformationResponseDTO.setPaymentAt(transaction.getPaymentAt());

        if (transaction.getAccount() != null) {
            incomingInformationResponseDTO.setAccount(transaction.getAccount().getAccountName());
        }
        if (transaction.getCategory() != null) {
            incomingInformationResponseDTO.setCategory(transaction.getCategory().getName());
        }

        return incomingInformationResponseDTO;
    }

    public Page<IncomingInformationResponseDTO> toPage(Page<FinancialTransactionVO> pageResult) {
        return pageResult.map(this::toInformationDTO);
    }
}
