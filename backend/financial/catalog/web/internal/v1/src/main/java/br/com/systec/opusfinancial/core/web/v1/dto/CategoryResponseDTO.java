package br.com.systec.opusfinancial.core.web.v1.dto;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public class CategoryResponseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 8324840013128890021L;

    private UUID id;
    private UUID parentId;
    private UUID tenantId;
    private String name;
    private String colorHex;
    private String iconCode;
    private String categoryType;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getParentId() {
        return parentId;
    }

    public void setParentId(UUID parentId) {
        this.parentId = parentId;
    }

    public UUID getTenantId() {
        return tenantId;
    }

    public void setTenantId(UUID tenantId) {
        this.tenantId = tenantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColorHex() {
        return colorHex;
    }

    public void setColorHex(String colorHex) {
        this.colorHex = colorHex;
    }

    public String getIconCode() {
        return iconCode;
    }

    public void setIconCode(String iconCode) {
        this.iconCode = iconCode;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }
}
