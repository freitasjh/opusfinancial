package br.com.systec.opusfinancial.financial.catalog.impl.fake;

import br.com.systec.opusfinancial.api.vo.CategoryType;
import br.com.systec.opusfinancial.api.vo.CategoryVO;
import br.com.systec.opusfinancial.financial.catalog.impl.domain.Category;

import java.util.UUID;

public class CategoryFake {

    public static Category toFake() {
        Category category = new Category();
        category.setId(UUID.randomUUID());
        category.setTenantId(UUID.randomUUID());
        category.setIconCode("icon");
        category.setColorHex("asdada");
        category.setCategoryName("Categoria de teste");
        category.setParentId(UUID.randomUUID());
        category.setCategoryType(CategoryType.INCOMING);

        return category;
    }

    public static CategoryVO toFakeVO() {
        CategoryVO category = new CategoryVO();
        category.setId(UUID.randomUUID());
        category.setParentId(UUID.randomUUID());
        category.setTenantId(UUID.randomUUID());
        category.setIconCode("icon");
        category.setColorHex("asdada");
        category.setName("Categoria de teste");
        category.setCategoryType(CategoryType.INCOMING);

        return category;
    }
}
