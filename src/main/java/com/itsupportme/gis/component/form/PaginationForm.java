package com.itsupportme.gis.component.form;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class PaginationForm {

    protected Integer page;

    @Min(value = 1, message = "min value - 1, max - 1000")
    @Max(value = 1000, message = "min value - 1, max - 1000")
    @NotNull
    protected Integer itemsPerPage;

    public PaginationForm() {
        this.page         = 1;
        this.itemsPerPage = 5;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(Integer itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }
}
