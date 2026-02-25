package br.com.systec.opusfinancial.reporting.vo;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

public record AccountBalanceVO(BigDecimal balance,
                               String accountType)
        implements Serializable {

    @Serial
    private static final long serialVersionUID = -884381640380214899L;
}
