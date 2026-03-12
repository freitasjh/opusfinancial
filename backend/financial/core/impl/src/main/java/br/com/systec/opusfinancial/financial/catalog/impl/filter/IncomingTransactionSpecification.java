package br.com.systec.opusfinancial.financial.catalog.impl.filter;

import br.com.systec.opusfinancial.financial.api.filter.IncomingTransactionFilter;
import br.com.systec.opusfinancial.financial.catalog.impl.entity.FinancialTransactionEntity;
import org.springframework.data.jpa.domain.Specification;

public class IncomingTransactionSpecification {

    private IncomingTransactionSpecification() {}

    public static IncomingTransactionSpecification of() {
        return new IncomingTransactionSpecification();
    }

    public Specification<FinancialTransactionEntity> filter(IncomingTransactionFilter filter) {
        Specification<FinancialTransactionEntity> specification = filterByType(filter);

        if (filter.getKeyword() != null && !filter.getKeyword().isEmpty()) {
            specification = specification.and(filterByKeyword(filter));
        }
        return specification;
    }

    private Specification<FinancialTransactionEntity> filterByKeyword(IncomingTransactionFilter filter) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.or(
                  criteriaBuilder.like(root.get("description"), "%" + filter.getKeyword() + "%")
                );
    }

    private Specification<FinancialTransactionEntity> filterByType(IncomingTransactionFilter filter) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("transactionType"), filter.getTransactionType());
    }
}
