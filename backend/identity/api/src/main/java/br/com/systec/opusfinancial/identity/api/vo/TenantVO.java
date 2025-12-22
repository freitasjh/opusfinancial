package br.com.systec.opusfinancial.identity.api.vo;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public class TenantVO implements Serializable {
    @Serial
    private static final long serialVersionUID = -8224033134898797369L;

    private UUID id;
    private String name;
    private UUID ownerId;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }
}
