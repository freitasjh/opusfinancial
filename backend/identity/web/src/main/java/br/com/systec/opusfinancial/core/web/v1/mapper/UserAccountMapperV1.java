package br.com.systec.opusfinancial.core.web.v1.mapper;

import br.com.systec.opusfinancial.core.web.v1.dto.UserInfoResponseDTO;
import br.com.systec.opusfinancial.identity.api.vo.UserAccountVO;
import br.com.systec.opusfinancial.core.web.v1.dto.UserAccountCreateDTO;
import br.com.systec.opusfinancial.identity.api.vo.UserVO;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

public class UserAccountMapperV1 {

    private UserAccountMapperV1() {}

    public static UserAccountMapperV1 of() {
        return new UserAccountMapperV1();
    }

    public UserAccountVO toVO(UserAccountCreateDTO accountCreateDTO) {
        UserAccountVO accountVO = new UserAccountVO();
        accountVO.setAccountName(accountCreateDTO.getAccountName());
        accountVO.setDocument(accountCreateDTO.getDocument());
        accountVO.setEmail(accountCreateDTO.getEmail());
        accountVO.setPassword(accountCreateDTO.getPassword());
        accountVO.setName(accountCreateDTO.getName());
        accountVO.setUsername(accountCreateDTO.getUsername());

        return accountVO;
    }
}
