package br.com.systec.opusfinancial.financial.creditcard.api.filter;

import br.com.systec.opusfinancial.commons.api.filter.PageParamSearch;

import java.util.UUID;

public class FilterCreditCard extends PageParamSearch {

    private UUID accountId;

    public FilterCreditCard(String keyword, int limit, int page) {
        super(keyword, limit, page);
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }
}
