package br.com.systec.opusfinancial.api.domain;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class ListCategory implements Serializable {
    @Serial
    private static final long serialVersionUID = 2358153658301498790L;

    private List<Category> categories;

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
