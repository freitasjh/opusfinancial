package br.com.systec.opusfinancial.financial.api.filter;

import br.com.systec.opusfinancial.commons.filter.PageParamSearch;

public class FilterAccount extends PageParamSearch {

    public FilterAccount(String keyword, int limit, int page) {
        super(keyword, limit, page);
    }
}
