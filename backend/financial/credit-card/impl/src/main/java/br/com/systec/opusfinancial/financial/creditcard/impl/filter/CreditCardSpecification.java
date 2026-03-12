package br.com.systec.opusfinancial.financial.creditcard.impl.filter;

import br.com.systec.opusfinancial.financial.creditcard.api.filter.FilterCreditCard;
import br.com.systec.opusfinancial.financial.creditcard.impl.entity.CreditCardEntity;
import org.springframework.data.jpa.domain.Specification;

public class CreditCardSpecification {

    private CreditCardSpecification() {}

    public static CreditCardSpecification of() {
        return new CreditCardSpecification();
    }

    public Specification<CreditCardEntity> filter(FilterCreditCard filter) {
        Specification<CreditCardEntity> specification = Specification.unrestricted();

        if (filter.getAccountId() != null) {
            specification = specification.and(filterByAccountId(filter));
        }

        if (filter.getKeyword() != null && !filter.getKeyword().isBlank()) {
            specification = specification.and(filterByKeyword(filter));
        }

        return specification;
    }

    private Specification<CreditCardEntity> filterByKeyword(FilterCreditCard filter) {
        String filterEscaped = br.com.systec.opusfinancial.commons.impl.utils.SqlSanitizerUtil.escapeLike(filter.getKeyword());
        String searchPattern = "%" + filterEscaped.toLowerCase() + "%";
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), searchPattern);
    }

    private Specification<CreditCardEntity> filterByAccountId(FilterCreditCard filter) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("accountId"), filter.getAccountId());
    }
}
