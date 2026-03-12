package br.com.systec.opusfinancial.financial.catalog.impl.fake;

import br.com.systec.opusfinancial.api.domain.Category;

import java.util.UUID;

public class CategoryFake {

    public static Category toFakeVO() {
        Category categoryVO = new Category();
        categoryVO.setId(UUID.randomUUID());

        return categoryVO;
    }

}
