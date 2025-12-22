package br.com.systec.opusfinancial.identity.api.services;

import br.com.systec.opusfinancial.identity.api.exceptions.TenantException;
import br.com.systec.opusfinancial.identity.api.vo.TenantVO;

import java.util.UUID;

public interface TenantService {

    TenantVO create(TenantVO tenantVO) throws TenantException;

    TenantVO update(TenantVO tenantVO) throws TenantException;

    TenantVO findById(UUID id) throws TenantException;

}
