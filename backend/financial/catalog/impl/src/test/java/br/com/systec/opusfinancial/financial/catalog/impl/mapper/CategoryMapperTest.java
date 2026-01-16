package br.com.systec.opusfinancial.financial.catalog.impl.mapper;

import br.com.systec.opusfinancial.api.vo.CategoryVO;
import br.com.systec.opusfinancial.financial.catalog.impl.domain.Category;
import br.com.systec.opusfinancial.financial.catalog.impl.fake.CategoryFake;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryMapperTest {

    @Test
    void whenConverterCategoryVO_thenReturnCategory() {
        CategoryVO categoryToConverter = CategoryFake.toFakeVO();

        Category categoryConverted = CategoryMapper.of().toEntity(categoryToConverter);

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
        Category categoryToConverter = CategoryFake.toFake();

        CategoryVO categoryConverted = CategoryMapper.of().toVO(categoryToConverter);

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
