package br.com.systec.opusfinancial.financial.catalog.impl.domain;

import br.com.systec.opusfinancial.api.vo.CategoryType;
import br.com.systec.opusfinancial.commons.entities.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "category")
public class Category extends AbstractEntity {
    @Serial
    private static final long serialVersionUID = 2343907804713181330L;

    @Column(name = "category_name")
    private String categoryName;
    @Column(name = "color_hex")
    private String colorHex;
    @Column(name = "icon_code")
    private String iconCode;
    @Column(name = "parent_id")
    private UUID parentId;
    @Enumerated(EnumType.STRING)
    @Column(name = "category_type")
    private CategoryType categoryType;
    @Column(name = "spending_limit")
    private BigDecimal spendingLimit;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public UUID getParentId() {
        return parentId;
    }

    public void setParentId(UUID parentId) {
        this.parentId = parentId;
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
}
