package br.com.systec.opusfinancial.financial.catalog.impl.mapper;

import br.com.systec.opusfinancial.api.domain.Category;
import br.com.systec.opusfinancial.financial.catalog.impl.entity.CategoryEntity;
import br.com.systec.opusfinancial.financial.catalog.impl.fake.CategoryFake;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryMapperTest {

    @Test
    void whenConverterCategoryVO_thenReturnCategory() {
        Category categoryToConverter = CategoryFake.toFakeVO();

        CategoryEntity categoryConverted = CategoryMapper.of().toEntity(categoryToConverter);

        Assertions.assertNotNull(categoryConverted);
        Assertions.assertNotNull(categoryConverted.getTenantId());
        assertEquals(categoryConverted.getId(), categoryToConverter.getId());
        assertEquals(categoryConverted.getCategoryName(), categoryToConverter.getName());
        assertEquals(categoryConverted.getTenantId(), categoryToConverter.getTenantId());
        assertEquals(categoryConverted.getColorHex(), categoryToConverter.getColorHex());
        assertEquals(categoryConverted.getIconCode(), categoryToConverter.getIconCode());
        assertEquals(categoryConverted.getParentId(), categoryToConverter.getParentId());
        assertEquals(categoryConverted.getCategoryType(), categoryToConverter.getCategoryType());
        assertEquals(categoryConverted.getSpendingLimit(), categoryToConverter.getSpendingLimit());

    }

    @Test
    void whenConverterCategory_thenReturnCategoryVO() {
        CategoryEntity categoryToConverter = CategoryFake.toFake();

        Category categoryConverted = CategoryMapper.of().toVO(categoryToConverter);

        Assertions.assertNotNull(categoryConverted);
        Assertions.assertNotNull(categoryConverted.getTenantId());
        assertEquals(categoryConverted.getId(), categoryToConverter.getId());
        assertEquals(categoryConverted.getName(), categoryToConverter.getCategoryName());
        assertEquals(categoryConverted.getTenantId(), categoryToConverter.getTenantId());
        assertEquals(categoryConverted.getColorHex(), categoryToConverter.getColorHex());
        assertEquals(categoryConverted.getIconCode(), categoryToConverter.getIconCode());
        assertEquals(categoryConverted.getParentId(), categoryToConverter.getParentId());
        assertEquals(categoryConverted.getCategoryType(), categoryToConverter.getCategoryType());
        assertEquals(categoryConverted.getSpendingLimit(), categoryToConverter.getSpendingLimit());
    }
}
