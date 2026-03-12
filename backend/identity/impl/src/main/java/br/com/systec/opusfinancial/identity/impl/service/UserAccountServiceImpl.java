package br.com.systec.opusfinancial.identity.impl.service;

import br.com.systec.opusfinancial.commons.api.exceptions.BaseException;
import br.com.systec.opusfinancial.commons.jms.vo.EventPublisherVO;
import br.com.systec.opusfinancial.commons.jms.vo.factory.EventPublisher;
import br.com.systec.opusfinancial.commons.jms.vo.factory.MessagingConstants;
import br.com.systec.opusfinancial.identity.api.services.UserAccountService;
import br.com.systec.opusfinancial.identity.api.services.UserService;
import br.com.systec.opusfinancial.identity.api.domain.UserAccount;
import br.com.systec.opusfinancial.identity.api.domain.User;
import br.com.systec.opusfinancial.identity.impl.mapper.UserMapper;
import br.com.systec.opusfinancial.tenant.api.domain.Tenant;
import br.com.systec.opusfinancial.tenant.api.service.TenantService;
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
    public void create(UserAccount accountVO) throws BaseException {
        try {
            User userBeforeCreate = UserMapper.of().accountToUserVO(accountVO);
            User userAfterCreate = userService.create(userBeforeCreate);

            Tenant tenantToCreate = UserMapper.of().accountToTenant(accountVO);
            tenantToCreate.setOwnerId(userAfterCreate.getId());
            tenantToCreate.setName(accountVO.getName());
            Tenant tenantAfterCreate = tenantService.create(tenantToCreate);

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
