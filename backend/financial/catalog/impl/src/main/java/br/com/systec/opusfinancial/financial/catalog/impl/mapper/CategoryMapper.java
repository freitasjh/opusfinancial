package br.com.systec.opusfinancial.financial.catalog.impl.mapper;

import br.com.systec.opusfinancial.api.vo.CategoryVO;
import br.com.systec.opusfinancial.financial.catalog.impl.domain.Category;

public class CategoryMapper {

    private CategoryMapper() {}

    public static CategoryMapper of() {
        return new CategoryMapper();
    }

    public Category toEntity(CategoryVO categoryVO) {
        Category category = new Category();
        category.setId(categoryVO.getId());
        category.setCategoryName(categoryVO.getName());
        category.setColorHex(categoryVO.getColorHex());
        category.setIconCode(categoryVO.getIconCode());
        category.setTenantId(categoryVO.getTenantId());
        category.setParentId(categoryVO.getParentId());
        category.setCategoryType(categoryVO.getCategoryType());
        category.setSpendingLimit(categoryVO.getSpendingLimit());

        return category;
    }

    public CategoryVO toVO(Category category) {
        CategoryVO categoryVO = new CategoryVO();
        categoryVO.setId(category.getId());
        categoryVO.setName(category.getCategoryName());
        categoryVO.setColorHex(category.getColorHex());
        categoryVO.setIconCode(category.getIconCode());
        categoryVO.setTenantId(category.getTenantId());
        categoryVO.setParentId(category.getParentId());
        categoryVO.setCategoryType(category.getCategoryType());
        categoryVO.setSpendingLimit(category.getSpendingLimit());

        return categoryVO;
    }
}
