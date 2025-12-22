package br.com.systec.opusfinancial.security.impl.service;

import br.com.systec.opusfinancial.commons.exceptions.BaseException;
import br.com.systec.opusfinancial.security.api.exceptions.SecurityException;
import br.com.systec.opusfinancial.security.api.service.LoginService;
import br.com.systec.opusfinancial.security.api.service.SecurityTokenService;
import br.com.systec.opusfinancial.security.api.vo.LoginAuthenticateVO;
import br.com.systec.opusfinancial.security.api.vo.LoginVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginServiceImpl implements LoginService {
    private static final Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);

    private final AuthenticationManager authenticationManager;
    private final SecurityTokenService securityTokenService;

    public LoginServiceImpl(AuthenticationManager authenticationManager, SecurityTokenService securityTokenService) {
        this.authenticationManager = authenticationManager;
        this.securityTokenService = securityTokenService;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public LoginAuthenticateVO login(LoginVO loginVO) throws BaseException {
        try {
            var userAndPasswordToken = new UsernamePasswordAuthenticationToken(loginVO.getUsername(), loginVO.getPassword());

            var authenticate = authenticationManager.authenticate(userAndPasswordToken);

            return securityTokenService.generateTokenAndRefreshToken(authenticate);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new SecurityException("Ocorreu um erro ao tentar gerar o token", e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public LoginAuthenticateVO refreshToken(String refreshToken) throws SecurityException {
        try {
            return securityTokenService.generateNewAccessTokenByRefreshToken(refreshToken);
        } catch (BaseException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            throw new SecurityException("Ocorreu um erro ao tentar gerar o Refresh token",e);
        }
    }

    @Override
    public void logout(String refreshToken) throws java.lang.SecurityException {

    }
}
