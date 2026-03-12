package br.com.systec.opusfinancial.tenant.impl.repository;

import br.com.systec.opusfinancial.tenant.impl.entity.TenantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TenantRepository extends JpaRepository<TenantEntity, UUID> {
}
