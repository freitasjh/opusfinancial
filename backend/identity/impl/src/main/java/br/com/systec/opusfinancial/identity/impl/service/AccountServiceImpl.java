package br.com.systec.opusfinancial.identity.impl.service;

import br.com.systec.opusfinancial.commons.exceptions.BaseException;
import br.com.systec.opusfinancial.identity.api.services.AccountService;
import br.com.systec.opusfinancial.identity.api.services.TenantService;
import br.com.systec.opusfinancial.identity.api.services.UserService;
import br.com.systec.opusfinancial.identity.api.vo.AccountVO;
import br.com.systec.opusfinancial.identity.api.vo.TenantVO;
import br.com.systec.opusfinancial.identity.api.vo.UserVO;
import br.com.systec.opusfinancial.identity.impl.mapper.TenantMapper;
import br.com.systec.opusfinancial.identity.impl.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountServiceImpl implements AccountService {
    private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

    private final UserService userService;
    private final TenantService tenantService;

    public AccountServiceImpl(UserService userService, TenantService tenantService) {
        this.userService = userService;
        this.tenantService = tenantService;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void create(AccountVO accountVO) throws BaseException {
        try {
            UserVO userBeforeCreate = UserMapper.of().accountToUserVO(accountVO);
            UserVO userAfterCreate = userService.create(userBeforeCreate);

            TenantVO tenantBeforeCreate = TenantMapper.of().accountToTenant(accountVO);
            tenantBeforeCreate.setOwnerId(userAfterCreate.getId());
            tenantService.create(tenantBeforeCreate);

        } catch (BaseException e) {
            log.error(e.getMessage(), e);
            throw e;
        }catch (Exception e) {
            log.error("Ocorreu um erro ao tentar criar a conta", e);
            throw new BaseException(e);
        }
    }
}
