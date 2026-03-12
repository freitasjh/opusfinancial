package br.com.systec.opusfinancial.core.web.v1.mapper;

import br.com.systec.opusfinancial.api.domain.Category;
import br.com.systec.opusfinancial.core.web.v1.dto.CategoryInputDTO;
import br.com.systec.opusfinancial.core.web.v1.dto.CategoryResponseDTO;
import br.com.systec.opusfinancial.core.web.v1.dto.CategorySaveResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public class CategoryV1Mapper {

    private CategoryV1Mapper() {}

    public static CategoryV1Mapper of() {
        return new CategoryV1Mapper();
    }

    public Category inputToVO(CategoryInputDTO inputDTO) {
        Category categoryVO = new Category();
        categoryVO.setId(inputDTO.getId());
        categoryVO.setName(inputDTO.getName());
        categoryVO.setParentId(inputDTO.getParentId());
        categoryVO.setColorHex(inputDTO.getColorHex());
        categoryVO.setIconCode(inputDTO.getIconCode());
        categoryVO.setCategoryType(inputDTO.getCategoryType());
        categoryVO.setSpendingLimit(inputDTO.getSpendingLimit());

        return categoryVO;
    }

    public CategorySaveResponseDTO toSaveResponseDTO(Category category) {
        CategorySaveResponseDTO categorySaveResponseDTO = new CategorySaveResponseDTO();
        categorySaveResponseDTO.setId(category.getId());
        categorySaveResponseDTO.setTenantId(category.getTenantId());
        categorySaveResponseDTO.setParentId(category.getParentId());
        categorySaveResponseDTO.setName(category.getName());
        categorySaveResponseDTO.setCategoryType(category.getCategoryType());

        return categorySaveResponseDTO;
    }

    public CategoryResponseDTO toResponseDTO(Category categoryVO) {
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
        categoryResponseDTO.setId(categoryVO.getId());
        categoryResponseDTO.setParentId(categoryVO.getParentId());
        categoryResponseDTO.setTenantId(categoryVO.getTenantId());
        categoryResponseDTO.setName(categoryVO.getName());
        categoryResponseDTO.setIconCode(categoryVO.getIconCode());
        categoryResponseDTO.setColorHex(categoryVO.getColorHex());
        categoryResponseDTO.setCategoryType(categoryVO.getCategoryType().name());

        return categoryResponseDTO;
    }

    public Page<CategoryResponseDTO> toFindResponse(Page<Category> result) {
        return result.map(this::toResponseDTO);
    }

    public List<CategoryResponseDTO> toListDTO(List<Category> listOfCategory) {
        return listOfCategory.stream().map(this::toResponseDTO).toList();
    }
}
