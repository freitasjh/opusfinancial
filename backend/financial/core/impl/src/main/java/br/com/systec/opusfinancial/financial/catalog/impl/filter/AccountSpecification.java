package br.com.systec.opusfinancial.financial.catalog.impl.filter;

import br.com.systec.opusfinancial.commons.api.filter.PageParamSearch;
import br.com.systec.opusfinancial.financial.catalog.impl.entity.AccountEntity;
import org.springframework.data.jpa.domain.Specification;

public class AccountSpecification {

    private AccountSpecification() {}

    public static AccountSpecification of() {
        return new AccountSpecification();
    }

    public Specification<AccountEntity> filter(PageParamSearch filter) {
        if (filter.getKeyword() != null && !filter.getKeyword().isEmpty()) {
            return Specification.allOf(filterByKeyword(filter));
        }

        return Specification.unrestricted();
    }

    private Specification<AccountEntity> filterByKeyword(PageParamSearch filter) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.or(
                        criteriaBuilder.like(root.get("name"), "%"+filter.getKeyword()+"%")
                );
    }
}
