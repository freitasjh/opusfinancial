package br.com.systec.opusfinancial.identity.impl.repository;

import br.com.systec.opusfinancial.commons.repository.AbstractRepository;
import br.com.systec.opusfinancial.identity.impl.entities.Tenant;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class TenantRepository extends AbstractRepository<Tenant, UUID> {
}
