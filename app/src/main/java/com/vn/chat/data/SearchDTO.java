package com.vn.chat.data;

public class SearchDTO {
    private String search = "";
    private String type;
    private Integer pageNumber = 0;
    private Integer pageSize = 50;
    private String sort;

    public SearchDTO() {
    }

    public SearchDTO(String search) {
        this.search = search;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void addPage(){
        this.pageNumber++;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void addPageNumber(){
        this.pageNumber++;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
