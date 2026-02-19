package br.com.systec.opusfinancial.commons.filter;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public abstract class PageParamSearch {

    protected String keyword;
    protected int limit;
    protected int page;
    protected String sortArgument;

    protected PageParamSearch() {
        this.keyword = "";
        this.limit = 30;
        this.page = 0;
        this.sortArgument = "";
    }

    protected PageParamSearch(String keyword, int limit, int page) {
        this.keyword = keyword;
        this.limit = Math.min(limit, 200);
        this.page = page;
    }

    public String getKeyword() {
        return keyword;
    }

    public Pageable getPageable() {
        return PageRequest.of(page, limit);
    }

    public String getSortArgument() {
        return sortArgument;
    }

    public Sort createSort(Sort.Direction direction, String... args) {
        return Sort.by(direction, args);
    }

}
