package br.com.systec.opusfinancial.financial.catalog.impl.fake;

import br.com.systec.opusfinancial.api.vo.CategoryVO;

import java.util.UUID;

public class CategoryFake {

    public static CategoryVO toFake() {
        CategoryVO categoryVO = new CategoryVO();
        categoryVO.setId(UUID.randomUUID());

        return categoryVO;
    }
}
