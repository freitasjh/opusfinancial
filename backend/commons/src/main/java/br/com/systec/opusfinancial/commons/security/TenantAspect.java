package br.com.systec.opusfinancial.commons.security;

import jakarta.persistence.EntityManager;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Aspect
@Component
public class TenantAspect {
    private static final Logger log = LoggerFactory.getLogger(TenantAspect.class);
    private final EntityManager entityManager;

    public TenantAspect(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Before("execution(* br.com.systec.opusfinancial..repository..*(..)) || " +
            "execution(* org.springframework.data.jpa.repository.JpaRepository+.*(..)) || " +
            "execution(* org.springframework.data.jpa.repository.JpaSpecificationExecutor+.*(..))")
    public void enableTenantFilter() {
        UUID tenantId = TenantContext.getTenant();
        if (tenantId != null) {
            log.warn("@@@ Setando o tenant no filtro {}", tenantId);
            Session session = entityManager.unwrap(Session.class);
            session.enableFilter("tenantFilter").setParameter("tenantId", tenantId);
        }
    }

}
