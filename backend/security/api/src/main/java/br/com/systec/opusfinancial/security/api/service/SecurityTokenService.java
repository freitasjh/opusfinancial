package br.com.systec.opusfinancial.security.api.service;

import br.com.systec.opusfinancial.commons.exceptions.BaseException;
import br.com.systec.opusfinancial.security.api.vo.LoginAuthenticateVO;
import org.springframework.security.core.Authentication;

public interface SecurityTokenService {

    LoginAuthenticateVO generateTokenAndRefreshToken(Authentication authentication) throws SecurityException;

    LoginAuthenticateVO generateNewAccessTokenByRefreshToken(String refreshToken) throws BaseException;

    void validateTokenRevoke(String tokenJWT) throws BaseException;

    String getSubject(String tokenJWT) throws SecurityException;
}
