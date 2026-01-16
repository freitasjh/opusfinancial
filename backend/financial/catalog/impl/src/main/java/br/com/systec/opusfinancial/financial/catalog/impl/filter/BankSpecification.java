package br.com.systec.opusfinancial.financial.catalog.impl.filter;

import br.com.systec.opusfinancial.commons.filter.PageParamSearch;
import br.com.systec.opusfinancial.financial.catalog.impl.domain.Bank;
import org.springframework.data.jpa.domain.Specification;

public class BankSpecification {

    private BankSpecification() {}

    public static BankSpecification of() {
        return new BankSpecification();
    }

    public Specification<Bank> filter(PageParamSearch filter) {
        if (filter.getKeyword() != null && !filter.getKeyword().isEmpty()) {
            return Specification.allOf(filterByKeyword(filter));
        }

        return Specification.unrestricted();
    }

    private Specification<Bank> filterByKeyword(PageParamSearch filter) {
        return (root, query, criteriaBuilder) ->
             criteriaBuilder.or(
                    criteriaBuilder.like(root.get("name"), "%"+filter.getKeyword()+"%")
            );

    }
}
