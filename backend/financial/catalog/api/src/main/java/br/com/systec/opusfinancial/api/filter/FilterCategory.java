package br.com.systec.opusfinancial.api.filter;

import br.com.systec.opusfinancial.commons.filter.PageParamSearch;

public class FilterCategory extends PageParamSearch {

    public FilterCategory(String keyword, int limit, int page) {
        super(keyword, limit, page);
    }
}
