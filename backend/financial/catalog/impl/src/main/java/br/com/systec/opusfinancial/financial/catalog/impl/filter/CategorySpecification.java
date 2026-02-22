package br.com.systec.opusfinancial.financial.catalog.impl.filter;

import br.com.systec.opusfinancial.api.filter.FilterCategory;
import br.com.systec.opusfinancial.commons.filter.PageParamSearch;
import br.com.systec.opusfinancial.financial.catalog.impl.domain.Category;
import org.springframework.data.jpa.domain.Specification;

import java.util.logging.Filter;

public class CategorySpecification {

    private CategorySpecification() {
    }

    public static CategorySpecification of() {
        return new CategorySpecification();
    }

    public Specification<Category> filter(FilterCategory filter) {
        Specification<Category> spec = filterByNotParent();

        if (filter.getKeyword() != null && !filter.getKeyword().isEmpty()) {
            return spec.and(filterByKeyword(filter));
        }

        if (filter.getCategoryType() != null) {
            return spec.and(filterByCategoryType(filter));
        }

        return spec;
    }

    private Specification<Category> filterByKeyword(FilterCategory filter) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.or(
                        criteriaBuilder.like(root.get("categoryName"), "%" + filter.getKeyword() + "%")
                );
    }

    private Specification<Category> filterByNotParent() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isNull(root.get("parentId"));
    }

    private Specification<Category> filterByCategoryType(FilterCategory filter) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("categoryType"), filter.getCategoryType());
    }
}
