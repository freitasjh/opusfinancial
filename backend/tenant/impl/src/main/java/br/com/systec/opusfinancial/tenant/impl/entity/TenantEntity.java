package br.com.systec.opusfinancial.tenant.impl.entity;

import br.com.systec.opusfinancial.commons.impl.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "tenant")
public class TenantEntity extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 2620104224960861435L;

    @Column(name = "name")
    private String name;
    @Column(name = "owner_id")
    private UUID ownerId;

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
