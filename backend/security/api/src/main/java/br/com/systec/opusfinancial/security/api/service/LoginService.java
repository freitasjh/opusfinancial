package br.com.systec.opusfinancial.security.api.service;

import br.com.systec.opusfinancial.security.api.vo.LoginAuthenticateVO;
import br.com.systec.opusfinancial.security.api.vo.LoginVO;

public interface LoginService {

    LoginAuthenticateVO login(LoginVO loginVO) throws SecurityException;

    LoginAuthenticateVO refreshToken(String refreshToken) throws SecurityException;

    void logout(String refreshToken) throws SecurityException;
}
