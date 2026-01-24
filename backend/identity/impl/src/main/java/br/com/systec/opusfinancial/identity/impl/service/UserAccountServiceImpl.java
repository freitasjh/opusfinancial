package br.com.systec.opusfinancial.identity.impl.service;

import br.com.systec.opusfinancial.commons.exceptions.BaseException;
import br.com.systec.opusfinancial.commons.messaging.EventPublisher;
import br.com.systec.opusfinancial.commons.messaging.MessagingConstants;
import br.com.systec.opusfinancial.commons.messaging.vo.EventPublisherVO;
import br.com.systec.opusfinancial.identity.api.services.TenantService;
import br.com.systec.opusfinancial.identity.api.services.UserAccountService;
import br.com.systec.opusfinancial.identity.api.services.UserService;
import br.com.systec.opusfinancial.identity.api.vo.TenantVO;
import br.com.systec.opusfinancial.identity.api.vo.UserAccountVO;
import br.com.systec.opusfinancial.identity.api.vo.UserVO;
import br.com.systec.opusfinancial.identity.impl.mapper.TenantMapper;
import br.com.systec.opusfinancial.identity.impl.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserAccountServiceImpl implements UserAccountService {
    private static final Logger log = LoggerFactory.getLogger(UserAccountServiceImpl.class);

    private final UserService userService;
    private final TenantService tenantService;
    private final EventPublisher eventPublisher;


    public UserAccountServiceImpl(UserService userService, TenantService tenantService, EventPublisher eventPublisher) {
        this.userService = userService;
        this.tenantService = tenantService;
        this.eventPublisher = eventPublisher;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void create(UserAccountVO accountVO) throws BaseException {
        try {
            UserVO userBeforeCreate = UserMapper.of().accountToUserVO(accountVO);
            UserVO userAfterCreate = userService.create(userBeforeCreate);

            TenantVO tenantToCreate = TenantMapper.of().accountToTenant(accountVO);
            tenantToCreate.setOwnerId(userAfterCreate.getId());
            tenantToCreate.setName(accountVO.getName());
            TenantVO tenantAfterCreate = tenantService.create(tenantToCreate);

            userAfterCreate.setTenantId(tenantAfterCreate.getId());

            log.warn("@@@ Salvando o tenant no usuario @@@");
            userService.saveTenantId(userAfterCreate.getId(), tenantAfterCreate.getId());

            eventPublisher.publish(MessagingConstants.USER_EVENTS_TOPIC, new EventPublisherVO(tenantAfterCreate.getId()));

        } catch (BaseException e) {
            log.error(e.getMessage(), e);
            throw e;
        }catch (Exception e) {
            log.error("Ocorreu um erro ao tentar criar a conta", e);
            throw new BaseException(e);
        }
    }


}
