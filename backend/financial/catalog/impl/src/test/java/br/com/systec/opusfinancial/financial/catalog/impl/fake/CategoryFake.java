package br.com.systec.opusfinancial.financial.catalog.impl.fake;

import br.com.systec.opusfinancial.api.domain.CategoryType;
import br.com.systec.opusfinancial.api.domain.Category;
import br.com.systec.opusfinancial.financial.catalog.impl.entity.CategoryEntity;

import java.util.UUID;

public class CategoryFake {

    public static CategoryEntity toFake() {
        CategoryEntity category = new CategoryEntity();
        category.setId(UUID.randomUUID());
        category.setTenantId(UUID.randomUUID());
        category.setIconCode("icon");
        category.setColorHex("asdada");
        category.setCategoryName("Categoria de teste");
        category.setParentId(UUID.randomUUID());
        category.setCategoryType(CategoryType.REVENUE);

        return category;
    }

    public static Category toFakeVO() {
        Category category = new Category();
        category.setId(UUID.randomUUID());
        category.setParentId(UUID.randomUUID());
        category.setTenantId(UUID.randomUUID());
        category.setIconCode("icon");
        category.setColorHex("asdada");
        category.setName("Categoria de teste");
        category.setCategoryType(CategoryType.REVENUE);

        return category;
    }
}
