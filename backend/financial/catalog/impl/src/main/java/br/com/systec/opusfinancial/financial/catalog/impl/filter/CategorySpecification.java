package br.com.systec.opusfinancial.financial.catalog.impl.filter;

import br.com.systec.opusfinancial.api.filter.FilterCategory;
import br.com.systec.opusfinancial.financial.catalog.impl.entity.CategoryEntity;
import org.springframework.data.jpa.domain.Specification;

public class CategorySpecification {

    private CategorySpecification() {
    }

    public static CategorySpecification of() {
        return new CategorySpecification();
    }

    public Specification<CategoryEntity> filter(FilterCategory filter) {
        Specification<CategoryEntity> spec = filterByNotParent();

        if (filter.getKeyword() != null && !filter.getKeyword().isEmpty()) {
            return spec.and(filterByKeyword(filter));
        }

        if (filter.getCategoryType() != null) {
            return spec.and(filterByCategoryType(filter));
        }

        return spec;
    }

    private Specification<CategoryEntity> filterByKeyword(FilterCategory filter) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.or(
                        criteriaBuilder.like(root.get("categoryName"), "%" + filter.getKeyword() + "%")
                );
    }

    private Specification<CategoryEntity> filterByNotParent() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isNull(root.get("parentId"));
    }

    private Specification<CategoryEntity> filterByCategoryType(FilterCategory filter) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("categoryType"), filter.getCategoryType());
    }
}
