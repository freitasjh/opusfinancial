package br.com.systec.opusfinancial.web.v1.mapper;

import br.com.systec.opusfinancial.identity.api.vo.AccountVO;
import br.com.systec.opusfinancial.web.v1.dto.AccountCreateDTO;

public class AccountControllerMapperV1 {

    private AccountControllerMapperV1() {}

    public static AccountControllerMapperV1 of() {
        return new AccountControllerMapperV1();
    }

    public AccountVO toVO(AccountCreateDTO accountCreateDTO) {
        AccountVO accountVO = new AccountVO();
        accountVO.setAccountName(accountCreateDTO.getAccountName());
        accountVO.setDocument(accountCreateDTO.getDocument());
        accountVO.setEmail(accountCreateDTO.getEmail());
        accountVO.setPassword(accountCreateDTO.getPassword());
        accountVO.setName(accountCreateDTO.getName());
        accountVO.setUsername(accountCreateDTO.getUsername());

        return accountVO;
    }
}
