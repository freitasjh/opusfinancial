package br.com.systec.opusfinancial.api.filter;

import br.com.systec.opusfinancial.api.vo.CategoryType;
import br.com.systec.opusfinancial.commons.filter.PageParamSearch;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class FilterCategory extends PageParamSearch {
    private static final String COLUMN_ORDER_NAME = "categoryName";
    private CategoryType categoryType;

    public FilterCategory(String keyword, int limit, int page) {
        super(keyword, limit, page);
    }

    public CategoryType getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(CategoryType categoryType) {
        this.categoryType = categoryType;
    }

    @Override
    public Pageable getPageable() {
        return PageRequest.of(page, limit, createSort(Sort.Direction.ASC, COLUMN_ORDER_NAME));
    }
}
