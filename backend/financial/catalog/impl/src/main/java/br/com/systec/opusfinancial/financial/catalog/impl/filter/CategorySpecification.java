package br.com.systec.opusfinancial.financial.catalog.impl.filter;

import br.com.systec.opusfinancial.commons.filter.PageParamSearch;
import br.com.systec.opusfinancial.financial.catalog.impl.domain.Category;
import org.springframework.data.jpa.domain.Specification;

public class CategorySpecification {

    private CategorySpecification() {}

    public static CategorySpecification of() {
        return new CategorySpecification();
    }

    public Specification<Category> filter(PageParamSearch filter) {
        Specification<Category> spec = filterByNotParent();

        if(filter.getKeyword() != null && !filter.getKeyword().isEmpty()) {
            return spec.and(filterByKeyword(filter));
        }

        return spec;
    }

    private Specification<Category> filterByKeyword(PageParamSearch filter) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.or(
                        criteriaBuilder.like(root.get("categoryName"), "%" + filter.getKeyword() + "%")
                );
    }

    private Specification<Category> filterByNotParent() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isNull(root.get("parentId"));
    }
}
