package br.com.systec.opusfinancial.security.web.v1.mapper;

import br.com.systec.opusfinancial.security.api.vo.LoginAuthenticateVO;
import br.com.systec.opusfinancial.security.api.vo.LoginVO;
import br.com.systec.opusfinancial.security.web.v1.dto.LoginDTO;
import br.com.systec.opusfinancial.security.web.v1.dto.LoginResponseDTO;

public class LoginMapper {

    private LoginMapper() {}

    public static LoginMapper of() {
        return new LoginMapper();
    }

    public LoginVO toLoginVO(LoginDTO loginDTO) {
        return new LoginVO(loginDTO.getUsername(), loginDTO.getPassword());
    }

    public LoginResponseDTO toLoginResponse(LoginAuthenticateVO loginAuthenticateVO) {
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        loginResponseDTO.setAccessToken(loginAuthenticateVO.getAccessToken());
        loginResponseDTO.setType("Bearer");
        loginResponseDTO.setUserId(loginAuthenticateVO.getUserId());

        return loginResponseDTO;
    }
}
