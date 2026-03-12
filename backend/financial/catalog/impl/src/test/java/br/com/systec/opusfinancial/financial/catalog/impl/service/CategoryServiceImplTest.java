package br.com.systec.opusfinancial.financial.catalog.impl.service;

import br.com.systec.opusfinancial.api.exceptions.CategoryNotFoundException;
import br.com.systec.opusfinancial.api.domain.Category;
import br.com.systec.opusfinancial.financial.catalog.impl.entity.CategoryEntity;
import br.com.systec.opusfinancial.financial.catalog.impl.fake.CategoryFake;
import br.com.systec.opusfinancial.financial.catalog.impl.repository.CategoryRepository;
import br.com.systec.opusfinancial.i18n.I18nTranslate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository repository;

    @InjectMocks
    private CategoryServiceImpl service;
    @Mock
    private ResourceBundleMessageSource messageSource; // Mock do messageSource
    @InjectMocks
    private I18nTranslate i18nTranslate;

    @Test
    void whenCreateCategory_thenReturnSuccessAndCategoryVO() {
        CategoryEntity categorySaveReturn = CategoryFake.toFake();
        categorySaveReturn.setSpendingLimit(BigDecimal.valueOf(0));

        Mockito.when(repository.save(ArgumentMatchers.any(CategoryEntity.class))).thenReturn(categorySaveReturn);

        Category categorySaved = service.create(CategoryFake.toFakeVO());

        assertThat(categorySaved).isNotNull();
        assertThat(categorySaved.getId()).isNotNull();
        assertThat(categorySaved.getName()).isEqualTo(categorySaveReturn.getCategoryName());
        assertThat(categorySaved.getCategoryType()).isEqualTo(categorySaveReturn.getCategoryType());
        assertThat(categorySaved.getSpendingLimit()).isNotNull();
        assertThat(categorySaved.getSpendingLimit()).isEqualTo(categorySaveReturn.getSpendingLimit());

        Mockito.verify(repository).save(ArgumentMatchers.any(CategoryEntity.class));
    }

    @Test
    void whenUpdateCategory_thenReturnSuccessAndCategoryVO() {
        UUID categoryId = UUID.randomUUID();
        UUID tenantId = UUID.randomUUID();

        CategoryEntity categoryFindReturn = CategoryFake.toFake();
        categoryFindReturn.setId(categoryId);
        categoryFindReturn.setTenantId(tenantId);

        Category categoryToUpdate = CategoryFake.toFakeVO();
        categoryToUpdate.setTenantId(categoryId);
        categoryToUpdate.setId(categoryId);

        Mockito.when(repository.findById(ArgumentMatchers.any(UUID.class))).thenReturn(Optional.of(categoryFindReturn));
        Mockito.when(repository.save(ArgumentMatchers.any(CategoryEntity.class))).thenReturn(categoryFindReturn);

        Category categoryUpdated = service.update(categoryToUpdate);

        assertThat(categoryUpdated).isNotNull();
        assertThat(categoryUpdated.getId()).isNotNull();
        assertThat(categoryUpdated.getId()).isEqualTo(categoryToUpdate.getId());
        assertThat(categoryUpdated.getTenantId()).isNotNull();
        assertThat(categoryUpdated.getName()).isEqualTo(categoryFindReturn.getCategoryName());

        Mockito.verify(repository).findById(ArgumentMatchers.any(UUID.class));
        Mockito.verify(repository).save(ArgumentMatchers.any(CategoryEntity.class));
    }

    @Test
    void whenUpdateCategory_thenReturnCategoryNotFoundException() {
        Mockito.when(repository.findById(ArgumentMatchers.any(UUID.class))).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> service.update(CategoryFake.toFakeVO()))
                .isInstanceOf(CategoryNotFoundException.class);

        Mockito.verify(repository).findById(ArgumentMatchers.any(UUID.class));
        Mockito.verify(repository, Mockito.never()).save(ArgumentMatchers.any(CategoryEntity.class));
    }

    @Test
    void whenFindCategoryById_thenReturnCategoryVO() {
        CategoryEntity categoryFindReturn = CategoryFake.toFake();

        Mockito.when(repository.findById(ArgumentMatchers.any(UUID.class))).thenReturn(Optional.of(categoryFindReturn));

        Category categoryReturn = service.findById(categoryFindReturn.getId());

        assertThat(categoryReturn).isNotNull();
        assertThat(categoryReturn.getId()).isNotNull();
        assertThat(categoryReturn.getId()).isEqualTo(categoryFindReturn.getId());
        assertThat(categoryReturn.getName()).isEqualTo(categoryFindReturn.getCategoryName());

        Mockito.verify(repository).findById(ArgumentMatchers.any(UUID.class));
    }

    @Test
    void whenFindCategoryById_thenReturnCategoryNotFoundException() {
        Mockito.when(repository.findById(ArgumentMatchers.any(UUID.class))).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> service.findById(UUID.randomUUID()))
                .isInstanceOf(CategoryNotFoundException.class);

        Mockito.verify(repository).findById(ArgumentMatchers.any(UUID.class));
    }
}
