package br.com.systec.opusfinancial.identity.impl.service;

import br.com.systec.opusfinancial.commons.exceptions.BaseException;
import br.com.systec.opusfinancial.identity.api.exceptions.TenantException;
import br.com.systec.opusfinancial.identity.api.exceptions.TenantNotFoundException;
import br.com.systec.opusfinancial.identity.api.services.TenantService;
import br.com.systec.opusfinancial.identity.api.vo.TenantVO;
import br.com.systec.opusfinancial.identity.impl.entities.Tenant;
import br.com.systec.opusfinancial.identity.impl.mapper.TenantMapper;
import br.com.systec.opusfinancial.identity.impl.repository.TenantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class TenantServiceImpl implements TenantService {
    private static final Logger log = LoggerFactory.getLogger(TenantServiceImpl.class);
    private final TenantRepository repository;

    public TenantServiceImpl(TenantRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public TenantVO create(TenantVO tenantVO) throws TenantException {
        try {
            Tenant tenantBeforeSave = TenantMapper.of().toEntity(tenantVO);
            Tenant tenantAfterSave = repository.save(tenantBeforeSave);

            return TenantMapper.of().toVO(tenantAfterSave);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar salvar o tenant", e);
            throw new TenantException(e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public TenantVO update(TenantVO tenantVO) throws TenantException {
        try {
            Tenant tenant = repository.findById(tenantVO.getId()).orElseThrow(TenantNotFoundException::new);
            Tenant tenantBeforeUpdate = TenantMapper.of().toEntity(tenantVO);
            tenantBeforeUpdate.setCreateAt(tenant.getCreateAt());

            Tenant tenantAfterUpdate = repository.update(tenantBeforeUpdate);
            return TenantMapper.of().toVO(tenantAfterUpdate);
        } catch (BaseException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar atualizar o tenant", e);
            throw new TenantException(e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public TenantVO findById(UUID id) throws TenantException {
        try {
            Tenant tenant = repository.findById(id).orElseThrow(TenantNotFoundException::new);
            return TenantMapper.of().toVO(tenant);
        } catch (BaseException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e){
            log.error("Ocorreu um erro ao tentar pesquisar o tenant", e);
            throw new TenantException(e);
        }
    }
}
