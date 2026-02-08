package br.com.systec.opusfinancial.api.vo;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public class BankVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 675337914880025373L;

    private UUID id;
    private String code;
    private String name;
    private String logoUrl;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

}
