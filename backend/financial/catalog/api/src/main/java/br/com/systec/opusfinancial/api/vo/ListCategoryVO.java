package br.com.systec.opusfinancial.api.vo;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class ListCategoryVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 2358153658301498790L;

    private List<CategoryVO> categories;

    public List<CategoryVO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryVO> categories) {
        this.categories = categories;
    }
}
