package br.com.systec.opusfinancial.financial.catalog.impl.service;

import br.com.systec.opusfinancial.api.exceptions.CategoryNotFoundException;
import br.com.systec.opusfinancial.api.filter.FilterCategory;
import br.com.systec.opusfinancial.api.service.CategoryService;
import br.com.systec.opusfinancial.api.domain.Category;
import br.com.systec.opusfinancial.api.domain.ListCategory;
import br.com.systec.opusfinancial.commons.api.exceptions.BaseException;
import br.com.systec.opusfinancial.financial.catalog.impl.entity.CategoryEntity;
import br.com.systec.opusfinancial.financial.catalog.impl.filter.CategorySpecification;
import br.com.systec.opusfinancial.financial.catalog.impl.mapper.CategoryMapper;
import br.com.systec.opusfinancial.financial.catalog.impl.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {
    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);
    private final CategoryRepository repository;

    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public Category create(Category category) {
        CategoryEntity categoryBeforeSave = CategoryMapper.of().toEntity(category);
        CategoryEntity categoryAfterSave = repository.save(categoryBeforeSave);

        return CategoryMapper.of().toVO(categoryAfterSave);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Category update(Category category) {
        CategoryEntity categorySaved = repository.findById(category.getId()).orElseThrow(CategoryNotFoundException::new);

        CategoryEntity categoryToUpdate = CategoryMapper.of().toEntity(category);
        categoryToUpdate.setCreateAt(categorySaved.getCreateAt());

        CategoryEntity categoryAfterUpdate = repository.save(categoryToUpdate);

        return CategoryMapper.of().toVO(categoryAfterUpdate);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Category findById(UUID id) {
        CategoryEntity categoryReturn = repository.findById(id).orElseThrow(CategoryNotFoundException::new);

        return CategoryMapper.of().toVO(categoryReturn);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Category> findByFilter(FilterCategory filter) {
        Specification<CategoryEntity> specification = CategorySpecification.of().filter(filter);
        Page<CategoryEntity> result = repository.findAll(specification, filter.getPageable());

        return result.map(CategoryMapper.of()::toVO);
    }

    @Override
    @Transactional
    public void createDefaultCategory(UUID tenantId) {
        ObjectMapper objectMapper = JsonMapper.builder().enable(SerializationFeature.INDENT_OUTPUT).build();

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream("json/category-insert.json")) {
            ListCategory listOfCategories = objectMapper.readValue(inputStream, ListCategory.class);
            for (Category category : listOfCategories.getCategories()) {
                category.setTenantId(tenantId);
                CategoryEntity categoryToSave = CategoryMapper.of().toEntity(category);
                repository.save(categoryToSave);
            }
        } catch (IOException e) {
            log.error("Erro ao tentar salvar as categorias defaut", e);
            throw new BaseException(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> findByParentId(UUID parentId) {
        List<CategoryEntity> listOfCategory = repository.findByParentId(parentId);

        return CategoryMapper.of().toList(listOfCategory);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> findByIds(List<UUID> ids) {
        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }

        List<CategoryEntity> listOfCategory = repository.findAllById(ids);

        return CategoryMapper.of().toList(listOfCategory);
    }
}
