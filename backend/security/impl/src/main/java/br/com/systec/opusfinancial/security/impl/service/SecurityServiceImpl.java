package br.com.systec.opusfinancial.security.impl.service;

import br.com.systec.opusfinancial.commons.exceptions.BaseException;
import br.com.systec.opusfinancial.identity.api.vo.UserVO;
import br.com.systec.opusfinancial.security.api.exceptions.SecurityException;
import br.com.systec.opusfinancial.security.api.service.SecurityService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Transactional(propagation = Propagation.SUPPORTS)
    public UUID getCurrentUserId() throws BaseException  {
        return null;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public UserVO getCurrentUser() throws BaseException {
        return null;
    }

    private UserVO getCurrentUserInteral() throws BaseException {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            throw new SecurityException("Usuário não autenticado");
        }

        return (UserVO) authentication.getPrincipal();
    }
}
