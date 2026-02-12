package br.com.systec.opusfinancial.financial.core.web.v1.mapper;

import br.com.systec.opusfinancial.financial.api.vo.AccountVO;
import br.com.systec.opusfinancial.financial.core.web.v1.dto.AccountInfoResponseDTO;
import br.com.systec.opusfinancial.financial.core.web.v1.dto.AccountInputDTO;
import br.com.systec.opusfinancial.financial.core.web.v1.dto.AccountResponseDTO;
import br.com.systec.opusfinancial.financial.core.web.v1.dto.AccountResponseSaveDTO;
import org.springframework.data.domain.Page;

public class AccountMapperV1 {

    private AccountMapperV1() {}

    public static AccountMapperV1 of() {
        return new AccountMapperV1();
    }

    public AccountVO toVO(AccountInputDTO inputDTO) {
        AccountVO accountVO = new AccountVO();
        accountVO.setAccountName(inputDTO.getAccountName());
        accountVO.setAccountType(inputDTO.getAccountType());
        accountVO.setBalance(inputDTO.getBalance());
        accountVO.setBankId(inputDTO.getBankId());
        accountVO.setAgency(inputDTO.getAgency());
        accountVO.setAccountNumber(inputDTO.getAccountNumber());

        return accountVO;
    }

    public AccountResponseSaveDTO toSaveResponse(AccountVO accountVO) {
        AccountResponseSaveDTO accountResponseSaveDTO = new AccountResponseSaveDTO();
        accountResponseSaveDTO.setId(accountVO.getId());
        accountResponseSaveDTO.setAccountName(accountVO.getAccountName());

        return accountResponseSaveDTO;
    }

    public AccountInfoResponseDTO toInfoDTO(AccountVO accountVO) {
        AccountInfoResponseDTO accountInfoResponseDTO = new AccountInfoResponseDTO();
        accountInfoResponseDTO.setId(accountVO.getId());
        accountInfoResponseDTO.setAccountName(accountVO.getAccountName());
        accountInfoResponseDTO.setAccountType(accountVO.getAccountType());
        accountInfoResponseDTO.setBalance(accountVO.getBalance());
        accountInfoResponseDTO.setAgency(accountVO.getAgency());
        accountInfoResponseDTO.setAccountNumber(accountVO.getAccountNumber());

        if (accountVO.getBank() != null) {
            accountInfoResponseDTO.setBankId(accountVO.getBank().getId());
            accountInfoResponseDTO.setBankName(accountVO.getBank().getName());
        }

        return accountInfoResponseDTO;
    }

    public AccountResponseDTO toResponseDTO(AccountVO accountVO) {
        AccountResponseDTO accountResponse = new AccountResponseDTO();
        accountResponse.setId(accountVO.getId());
        accountResponse.setAccountName(accountVO.getAccountName());
        accountResponse.setAccountType(accountVO.getAccountType().name());

        if (accountVO.getBank() != null) {
            accountResponse.setBank(accountVO.getBank().getName());
        }

        return accountResponse;
    }

    public Page<AccountResponseDTO> toPageResonse(Page<AccountVO> resultPage) {
        return resultPage.map(this::toResponseDTO);
    }

}
