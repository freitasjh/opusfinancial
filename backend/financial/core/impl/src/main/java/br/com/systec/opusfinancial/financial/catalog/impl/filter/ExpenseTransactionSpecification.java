package br.com.systec.opusfinancial.financial.catalog.impl.filter;

import br.com.systec.opusfinancial.financial.api.filter.ExpenseTransactionFilter;
import br.com.systec.opusfinancial.financial.catalog.impl.entity.FinancialTransactionEntity;
import org.springframework.data.jpa.domain.Specification;

public class ExpenseTransactionSpecification {

    private ExpenseTransactionSpecification() {}

    public static ExpenseTransactionSpecification of() {
        return new ExpenseTransactionSpecification();
    }

    public Specification<FinancialTransactionEntity> filter(ExpenseTransactionFilter filter) {
        Specification<FinancialTransactionEntity> specification = filterByType(filter);

        if (filter.getKeyword() != null && !filter.getKeyword().isEmpty()) {
            specification = specification.and(filterByKeyword(filter));
        }
        return specification;
    }

    private Specification<FinancialTransactionEntity> filterByKeyword(ExpenseTransactionFilter filter) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.or(
                        criteriaBuilder.like(root.get("description"), "%" + filter.getKeyword() + "%")
                );
    }


    private Specification<FinancialTransactionEntity> filterByType(ExpenseTransactionFilter filter) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("transactionType"), filter.getTransactionType());
    }
}
