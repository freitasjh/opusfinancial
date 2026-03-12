package br.com.systec.opusfinancial.reporting.impl.repository;

import br.com.systec.opusfinancial.commons.impl.security.TenantContext;
import br.com.systec.opusfinancial.reporting.vo.ExpenseReportingVO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ExpenseReportingRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<ExpenseReportingVO> findLastTenExpenses() {
        String sql = """
                SELECT
                	expense.id AS expense_id,
                	expense.description AS expense_description,
                	expense.amount AS expense_amount,
                	acc.account_name AS expense_account,
                	cat.category_name AS expense_category,
                	expense.processed AS expense_processed,
                	expense.create_at AS expense_create_at,
                	expense.payment_at AS expense_payment_at,
                	expense.due_date AS expense_due_date
                FROM
                	financial_transaction expense
                JOIN account acc ON
                	acc.id = expense.account_id
                JOIN category cat ON
                	cat.id = expense.category_id
                WHERE
                	expense.transaction_type = 'EXPENSE'
                AND
                    expense.tenant_id = :tenantId
                ORDER BY
                	expense.create_at DESC
                """;
        Query query = entityManager.createNativeQuery(sql, Tuple.class);
        query.setParameter("tenantId", TenantContext.getCurrentTenant());
        query.setMaxResults(10);

        List<Tuple> results = query.getResultList();

        return results.stream().map(ExpenseReportingVO::new).toList();
    }
}
