package br.com.systec.opusfinancial.integration.test.fake;

import br.com.systec.opusfinancial.api.vo.CategoryType;
import br.com.systec.opusfinancial.core.web.v1.dto.CategoryInputDTO;

public class CategoryFake {

    public static CategoryInputDTO fakeInputDTO() {
        CategoryInputDTO categoryInputDTO = new CategoryInputDTO();
        categoryInputDTO.setName("Categoria teste 01");
        categoryInputDTO.setColorHex("1123");
        categoryInputDTO.setIconCode("123aa");
        categoryInputDTO.setCategoryType(CategoryType.REVENUE);


        return categoryInputDTO;
    }
}
