package br.com.systec.opusfinancial.api.service;

import br.com.systec.opusfinancial.api.filter.FilterCategory;
import br.com.systec.opusfinancial.api.domain.Category;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    Category create(Category category);

    Category update(Category category);

    Category findById(UUID id);

    Page<Category> findByFilter(FilterCategory filter);

    List<Category> findByParentId(UUID parentId);

    void createDefaultCategory(UUID tenantId);

    List<Category> findByIds(List<UUID> ids);
}
