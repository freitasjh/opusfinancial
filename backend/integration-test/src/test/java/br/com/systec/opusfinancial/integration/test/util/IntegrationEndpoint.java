package br.com.systec.opusfinancial.integration.test.util;

import br.com.systec.opusfinancial.commons.controller.RestPath;

import static br.com.systec.opusfinancial.commons.controller.RestPath.*;

public class IntegrationEndpoint {
    public static final String ENDPOINT_USER_ACCOUNT = "%s/user-accounts".formatted(V1);
    public static final String ENDPOINT_AUTH = "%s/auth".formatted(V1);

    //Bancos endpoint
    public static final String ENDPOINT_BANK = "%s/banks".formatted(RestPath.V1);
    public static final String ENDPOINT_BANK_FILTER = "%s/banks/filter".formatted(V1);
    public static final String ENDPOINT_BANK_CREATE = "%s/banks/create".formatted(V1);
    public static final String ENDPOINT_BANK_UPDATE = "%s/banks/update".formatted(V1);

    public static final String ENDPOINT_CATEGORIES = "%s/categories".formatted(V1);
    public static final String ENDPOINT_CATEGORIES_FILTER = "%s/categories/filter".formatted(V1);
    public static final String ENDPOINT_CATEGORIES_CREATE = "%s/categories/create".formatted(V1);

    public static final String ENDPOINT_ACCOUNT_V1 = "%s/accounts".formatted(V1);
    public static final String ENDPOINT_ACCOUNT_CREATE = "%s/accounts/create".formatted(V1);
    public static final String ENDPOINT_ACCOUNT_FILTER = "%s/accounts/filter".formatted(V1);

    public static final String ENDPOINT_INCOMING_V1 = "%s/incomings".formatted(V1);
    public static final String ENDPOINT_INCOMING_FILTER = "%s/incomings/filter".formatted(V1);
    public static final String ENDPOINT_INCOMING_CREATE = "%s/incomings/create".formatted(V1);

    public static final String ENDPOINT_EXPENSE_CREATE = "%s/exepenses/create".formatted(V1);

    public static final String ENDPOINT_DASHBOARD_V1 = "%s/dashboards".formatted(V1);
    public static final String ENDPOINT_DASHBOARD_ACCOUNT_SUMMARY = "%s/dashboards/account-summary-balance".formatted(V1);

    public static String getAuthorizationBearer() {
        return "Bearer %s".formatted(IntegrationUtil.accessToken);
    }
}
