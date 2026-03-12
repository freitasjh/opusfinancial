package br.com.systec.opusfinancial.commons.api.domain;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public abstract class BaseDomain implements Serializable {

    @Serial
    private static final long serialVersionUID = -7056555280501262385L;
    protected UUID id;
    protected LocalDateTime createAt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }
}
