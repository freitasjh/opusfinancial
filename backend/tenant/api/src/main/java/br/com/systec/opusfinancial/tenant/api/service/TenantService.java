package br.com.systec.opusfinancial.tenant.api.service;

import br.com.systec.opusfinancial.tenant.api.domain.Tenant;
import br.com.systec.opusfinancial.tenant.api.exceptions.TenantException;

import java.util.UUID;

public interface TenantService {

    Tenant create(Tenant tenantVO) throws TenantException;

    Tenant update(Tenant tenantVO) throws TenantException;

    Tenant findById(UUID id) throws TenantException;

}
