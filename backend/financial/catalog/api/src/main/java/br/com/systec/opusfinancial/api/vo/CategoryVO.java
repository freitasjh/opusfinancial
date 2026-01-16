package br.com.systec.opusfinancial.api.vo;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

public class CategoryVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 8972026015678585541L;

    private UUID id;
    private UUID tenantId;
    private UUID parentId;
    private String name;
    private String colorHex;
    private String iconCode;
    private CategoryType categoryType;
    private BigDecimal spendingLimit;

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

    public CategoryType getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(CategoryType categoryType) {
        this.categoryType = categoryType;
    }

    public BigDecimal getSpendingLimit() {
        return spendingLimit;
    }

    public void setSpendingLimit(BigDecimal spendingLimit) {
        this.spendingLimit = spendingLimit;
    }

    @Override
    public String toString() {
        return "CategoryVO{" +
                "id=" + id +
                ", tenantId=" + tenantId +
                ", parentId=" + parentId +
                ", name='" + name + '\'' +
                ", colorHex='" + colorHex + '\'' +
                ", iconCode='" + iconCode + '\'' +
                ", categoryType=" + categoryType +
                ", spendingLimit=" + spendingLimit +
                '}';
    }
}
