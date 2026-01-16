package br.com.systec.opusfinancial.api.filter;

import br.com.systec.opusfinancial.commons.filter.PageParamSearch;

public class FilterBank extends PageParamSearch {

    public FilterBank(String keyword, int limit, int page) {
        super(keyword, limit, page);
    }
}
