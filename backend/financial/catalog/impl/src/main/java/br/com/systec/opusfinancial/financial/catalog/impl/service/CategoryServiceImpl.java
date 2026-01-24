package br.com.systec.opusfinancial.financial.catalog.impl.service;

import br.com.systec.opusfinancial.api.exceptions.CategoryNotFoundException;
import br.com.systec.opusfinancial.api.filter.FilterCategory;
import br.com.systec.opusfinancial.api.service.CategoryService;
import br.com.systec.opusfinancial.api.vo.CategoryVO;
import br.com.systec.opusfinancial.api.vo.ListCategoryVO;
import br.com.systec.opusfinancial.commons.exceptions.BaseException;
import br.com.systec.opusfinancial.financial.catalog.impl.domain.Category;
import br.com.systec.opusfinancial.financial.catalog.impl.filter.CategorySpecification;
import br.com.systec.opusfinancial.financial.catalog.impl.mapper.CategoryMapper;
import br.com.systec.opusfinancial.financial.catalog.impl.repository.CategoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
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
    public CategoryVO create(CategoryVO category) {
        Category categoryBeforeSave = CategoryMapper.of().toEntity(category);
        Category categoryAfterSave = repository.save(categoryBeforeSave);

        return CategoryMapper.of().toVO(categoryAfterSave);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public CategoryVO update(CategoryVO category) {
        Category categorySaved = repository.findById(category.getId()).orElseThrow(CategoryNotFoundException::new);

        Category categoryToUpdate = CategoryMapper.of().toEntity(category);
        categoryToUpdate.setCreateAt(categorySaved.getCreateAt());

        Category categoryAfterUpdate = repository.save(categoryToUpdate);

        return CategoryMapper.of().toVO(categoryAfterUpdate);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public CategoryVO findById(UUID id) {
        Category categoryReturn = repository.findById(id).orElseThrow(CategoryNotFoundException::new);

        return CategoryMapper.of().toVO(categoryReturn);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryVO> findByFilter(FilterCategory filter) {
        Specification<Category> specification = CategorySpecification.of().filter(filter);
        Page<Category> result = repository.findAll(specification, filter.getPageable());

        return result.map(CategoryMapper.of()::toVO);
    }

    @Override
    @Transactional
    public void createDefaultCategory(UUID tenantId) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream("json/category-insert.json")) {
            ListCategoryVO listOfCategories = objectMapper.readValue(inputStream, ListCategoryVO.class);
            for (CategoryVO category : listOfCategories.getCategories()) {
                category.setTenantId(tenantId);
                Category categoryToSave = CategoryMapper.of().toEntity(category);
                repository.save(categoryToSave);
            }
        } catch (IOException e) {
            log.error("Erro ao tentar salvar as categorias defaut", e);
            throw new BaseException(e);
        }
    }
}
