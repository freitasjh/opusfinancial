package br.com.systec.opusfinancial.financial.catalog.impl.service;

import br.com.systec.opusfinancial.api.exceptions.CategoryNotFoundException;
import br.com.systec.opusfinancial.api.filter.FilterCategory;
import br.com.systec.opusfinancial.api.service.CategoryService;
import br.com.systec.opusfinancial.api.vo.CategoryVO;
import br.com.systec.opusfinancial.financial.catalog.impl.domain.Category;
import br.com.systec.opusfinancial.financial.catalog.impl.filter.CategorySpecification;
import br.com.systec.opusfinancial.financial.catalog.impl.mapper.CategoryMapper;
import br.com.systec.opusfinancial.financial.catalog.impl.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {
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
}
