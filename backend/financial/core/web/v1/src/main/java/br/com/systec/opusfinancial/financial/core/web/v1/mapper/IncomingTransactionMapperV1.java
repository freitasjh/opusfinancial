package br.com.systec.opusfinancial.financial.core.web.v1.mapper;

import br.com.systec.opusfinancial.api.domain.Category;
import br.com.systec.opusfinancial.financial.api.domain.Account;
import br.com.systec.opusfinancial.financial.api.domain.FinancialTransaction;
import br.com.systec.opusfinancial.financial.core.web.v1.dto.IncomingTransactionInputDTO;
import br.com.systec.opusfinancial.financial.core.web.v1.dto.IncomingInformationResponseDTO;
import br.com.systec.opusfinancial.financial.core.web.v1.dto.IncomingSaveResponseDTO;
import org.springframework.data.domain.Page;

public class IncomingTransactionMapperV1 {

    private IncomingTransactionMapperV1() {
    }

    public static IncomingTransactionMapperV1 of() {
        return new IncomingTransactionMapperV1();
    }

    public FinancialTransaction toVO(IncomingTransactionInputDTO inputDTO) {
        FinancialTransaction financialTransaction = new FinancialTransaction();
        financialTransaction.setId(inputDTO.getId());
        financialTransaction.setDescription(inputDTO.getDescription());
        financialTransaction.setAmount(inputDTO.getAmount());
        financialTransaction.setPaymentAt(inputDTO.getPaymentAt());
        financialTransaction.setProcessedAt(inputDTO.getProcessedAt());
        financialTransaction.setProcessed(inputDTO.isProcessed());

        financialTransaction.setAccount(new Account());
        financialTransaction.getAccount().setId(inputDTO.getAccountId());
        financialTransaction.setCategory(new Category());
        financialTransaction.getCategory().setId(inputDTO.getCategoryId());

        return financialTransaction;
    }

    public IncomingSaveResponseDTO toSaveResponse(FinancialTransaction financialTransactionVO) {
        IncomingSaveResponseDTO saveResponse = new IncomingSaveResponseDTO();
        saveResponse.setId(financialTransactionVO.getId());

        return saveResponse;
    }

    public IncomingInformationResponseDTO toInformationDTO(FinancialTransaction transaction) {

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

    public Page<IncomingInformationResponseDTO> toPage(Page<FinancialTransaction> pageResult) {
        return pageResult.map(this::toInformationDTO);
    }
}
