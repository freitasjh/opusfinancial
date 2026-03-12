package br.com.systec.opusfinancial.tenant.impl.service;

import br.com.systec.opusfinancial.tenant.api.domain.Tenant;
import br.com.systec.opusfinancial.tenant.api.exceptions.TenantException;
import br.com.systec.opusfinancial.tenant.api.exceptions.TenantNotFoundException;
import br.com.systec.opusfinancial.tenant.api.service.TenantService;
import br.com.systec.opusfinancial.tenant.impl.entity.TenantEntity;
import br.com.systec.opusfinancial.tenant.impl.mapper.TenantMapper;
import br.com.systec.opusfinancial.tenant.impl.repository.TenantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class TenantServiceImpl implements TenantService {
    private final TenantRepository repository;

    public TenantServiceImpl(TenantRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Tenant create(Tenant tenantVO) {
        TenantEntity tenantToSave = TenantMapper.of().toEntity(tenantVO);
        TenantEntity tenantAfterSave = repository.save(tenantToSave);

        return TenantMapper.of().toVO(tenantAfterSave);
    }

    @Override
    @Transactional
    public Tenant update(Tenant tenantVO) throws TenantException {
        TenantEntity tenant = repository.findById(tenantVO.getId()).orElseThrow(TenantNotFoundException::new);
        TenantEntity tenantBeforeUpdate = TenantMapper.of().toEntity(tenantVO);
        tenantBeforeUpdate.setCreateAt(tenant.getCreateAt());

        TenantEntity tenantAfterUpdate = repository.save(tenantBeforeUpdate);
        return TenantMapper.of().toVO(tenantAfterUpdate);
    }

    @Override
    @Transactional(readOnly = true)
    public Tenant findById(UUID id) throws TenantException {
        TenantEntity tenant = repository.findById(id).orElseThrow(TenantNotFoundException::new);

        return TenantMapper.of().toVO(tenant);
    }
}
