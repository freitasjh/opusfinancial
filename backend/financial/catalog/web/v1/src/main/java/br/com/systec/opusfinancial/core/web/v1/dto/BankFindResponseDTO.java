package br.com.systec.opusfinancial.core.web.v1.dto;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public class BankFindResponseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -6160994752635578477L;

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
