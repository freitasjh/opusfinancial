package br.com.systec.opusfinancial.api.service;

import br.com.systec.opusfinancial.api.filter.FilterCategory;
import br.com.systec.opusfinancial.api.vo.CategoryVO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    CategoryVO create(CategoryVO category);

    CategoryVO update(CategoryVO category);

    CategoryVO findById(UUID id);

    Page<CategoryVO> findByFilter(FilterCategory filter);

    List<CategoryVO> findByParentId(UUID parentId);

    void createDefaultCategory(UUID tenantId);
}
