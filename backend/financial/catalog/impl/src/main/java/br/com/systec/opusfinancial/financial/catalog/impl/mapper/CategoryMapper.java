package br.com.systec.opusfinancial.financial.catalog.impl.mapper;

import br.com.systec.opusfinancial.api.domain.Category;
import br.com.systec.opusfinancial.financial.catalog.impl.entity.CategoryEntity;

import java.util.List;

public class CategoryMapper {

    private CategoryMapper() {}

    public static CategoryMapper of() {
        return new CategoryMapper();
    }

    public CategoryEntity toEntity(Category categoryVO) {
        CategoryEntity category = new CategoryEntity();
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

    public Category toVO(CategoryEntity category) {
        Category categoryVO = new Category();
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

    public List<Category> toList(List<CategoryEntity> listOfCategory) {
        return listOfCategory.stream().map(this::toVO).toList();
    }
}
