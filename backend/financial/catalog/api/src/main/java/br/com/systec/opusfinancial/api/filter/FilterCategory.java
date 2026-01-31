package br.com.systec.opusfinancial.api.filter;

import br.com.systec.opusfinancial.commons.filter.PageParamSearch;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class FilterCategory extends PageParamSearch {
    private static final String COLUMN_ORDER_NAME = "categoryName";

    public FilterCategory(String keyword, int limit, int page) {
        super(keyword, limit, page);
    }

    @Override
    public Pageable getPageable() {
        return PageRequest.of(page, limit, createSort(Sort.Direction.ASC, COLUMN_ORDER_NAME));
    }
}
