package br.com.systec.opusfinancial.financial.jms;

import br.com.systec.opusfinancial.api.vo.CategoryVO;

import java.util.List;

public class ListCategory {

    private List<CategoryVO> categories;

    public List<CategoryVO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryVO> categories) {
        this.categories = categories;
    }
}
