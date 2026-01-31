package br.com.systec.opusfinancial.core.web.v1.mapper;

import br.com.systec.opusfinancial.api.vo.CategoryVO;
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

    public CategoryVO inputToVO(CategoryInputDTO inputDTO) {
        CategoryVO categoryVO = new CategoryVO();
        categoryVO.setId(inputDTO.getId());
        categoryVO.setName(inputDTO.getName());
        categoryVO.setParentId(inputDTO.getParentId());
        categoryVO.setColorHex(inputDTO.getColorHex());
        categoryVO.setIconCode(inputDTO.getIconCode());
        categoryVO.setCategoryType(inputDTO.getCategoryType());
        categoryVO.setSpendingLimit(inputDTO.getSpendingLimit());

        return categoryVO;
    }

    public CategorySaveResponseDTO toSaveResponseDTO(CategoryVO category) {
        CategorySaveResponseDTO categorySaveResponseDTO = new CategorySaveResponseDTO();
        categorySaveResponseDTO.setId(category.getId());
        categorySaveResponseDTO.setTenantId(category.getTenantId());
        categorySaveResponseDTO.setParentId(category.getParentId());
        categorySaveResponseDTO.setName(category.getName());
        categorySaveResponseDTO.setCategoryType(category.getCategoryType());

        return categorySaveResponseDTO;
    }

    public CategoryResponseDTO toResponseDTO(CategoryVO categoryVO) {
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

    public Page<CategoryResponseDTO> toFindResponse(Page<CategoryVO> result) {
        return result.map(this::toResponseDTO);
    }

    public List<CategoryResponseDTO> toListDTO(List<CategoryVO> listOfCategory) {
        return listOfCategory.stream().map(this::toResponseDTO).toList();
    }
}
