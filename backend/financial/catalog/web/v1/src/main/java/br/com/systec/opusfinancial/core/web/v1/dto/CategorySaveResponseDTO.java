package br.com.systec.opusfinancial.core.web.v1.dto;

import br.com.systec.opusfinancial.api.vo.CategoryType;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public class CategorySaveResponseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 3853467406431389913L;

    private UUID id;
    private UUID tenantId;
    private UUID parentId;
    private String name;
    private CategoryType categoryType;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getTenantId() {
        return tenantId;
    }

    public void setTenantId(UUID tenantId) {
        this.tenantId = tenantId;
    }

    public UUID getParentId() {
        return parentId;
    }

    public void setParentId(UUID parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryType getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(CategoryType categoryType) {
        this.categoryType = categoryType;
    }
}
