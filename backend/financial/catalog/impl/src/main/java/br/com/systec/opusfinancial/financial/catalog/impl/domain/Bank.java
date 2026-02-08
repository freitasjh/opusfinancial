package br.com.systec.opusfinancial.financial.catalog.impl.domain;

import br.com.systec.opusfinancial.commons.entities.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serial;

@Entity
@Table(name = "bank")
public class Bank extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 8656686452916707876L;

    @Column(name = "code")
    private String code;
    @Column(name = "name")
    private String name;
    @Column(name = "logo_url")
    private String logoUrl;

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
