package com.itsupportme.gis.component.pagination;

public class Unit {

    private Integer itemsTotal;

    private Integer itemsPerPage;

    private Integer currentPage;

    private Integer linksQuantity;

    private Integer pagesTotal;

    private Integer firstResult;

    private Integer positiveDisplayLimit;

    private Integer negativeDisplayLimit;

    public Unit(Integer itemsTotal, Integer itemsPerPage, Integer currentPage, Integer linksQuantity) {

        this.itemsTotal    = itemsTotal;
        this.itemsPerPage  = itemsPerPage;
        this.currentPage   = currentPage;
        this.linksQuantity = linksQuantity;

        this.pagesTotal = 0;
        do {
            this.pagesTotal++;
        }
        while (this.pagesTotal * itemsPerPage < itemsTotal);

        if (this.currentPage > this.pagesTotal) {
            this.currentPage = this.pagesTotal;
        }

        this.firstResult = (this.getCurrentPage() - 1) * this.getItemsPerPage();

        this.positiveDisplayLimit = this.currentPage + this.linksQuantity;
        this.negativeDisplayLimit = this.currentPage - this.linksQuantity;
    }

    public Integer getItemsTotal() {
        return itemsTotal;
    }

    public void setItemsTotal(Integer itemsTotal) {
        this.itemsTotal = itemsTotal;
    }

    public Integer getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(Integer itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getLinksQuantity() {
        return linksQuantity;
    }

    public void setLinksQuantity(Integer linksQuantity) {
        this.linksQuantity = linksQuantity;
    }

    public Integer getPagesTotal() {
        return pagesTotal;
    }

    public void setPagesTotal(Integer pagesTotal) {
        this.pagesTotal = pagesTotal;
    }

    public Integer getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(Integer firstResult) {
        this.firstResult = firstResult;
    }

    public Integer getPositiveDisplayLimit() {
        return positiveDisplayLimit;
    }

    public void setPositiveDisplayLimit(Integer positiveDisplayLimit) {
        this.positiveDisplayLimit = positiveDisplayLimit;
    }

    public Integer getNegativeDisplayLimit() {
        return negativeDisplayLimit;
    }

    public void setNegativeDisplayLimit(Integer negativeDisplayLimit) {
        this.negativeDisplayLimit = negativeDisplayLimit;
    }
}
