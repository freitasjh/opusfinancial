package br.com.systec.opusfinancial.financial.api.filter;

import br.com.systec.opusfinancial.commons.filter.PageParamSearch;
import br.com.systec.opusfinancial.financial.api.vo.TransactionType;

public class IncomingTransactionFilter extends PageParamSearch {

    private final TransactionType transactionType;

    public IncomingTransactionFilter(String keyword, int limit, int page) {
        super(keyword, limit, page);
        this.transactionType = TransactionType.INCOMING;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }
}
