package br.com.systec.opusfinancial.reporting.impl.repository;

import br.com.systec.opusfinancial.commons.impl.security.TenantContext;
import br.com.systec.opusfinancial.reporting.vo.AccountBalanceVO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class AccountReportingRepository  {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<AccountBalanceVO> getSummaryAccountsBalance() {
        String sql = """
                SELECT new br.com.systec.opusfinancial.reporting.vo.AccountBalanceVO(SUM(a.balance), STR(a.accountType)) FROM Account a
                 WHERE a.tenantId = :tenantId
                 GROUP BY a.accountType
                """;

        TypedQuery<AccountBalanceVO> query = entityManager.createQuery(sql, AccountBalanceVO.class);
        query.setParameter("tenantId", TenantContext.getCurrentTenant());

        return query.getResultList();
    }
}
