package com.vn.chat.data;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class CommonDTO {
    private String search;
    private Integer pageNumber = 1;
    private Integer pageSize = 1;

    public CommonDTO() {
    }

    public CommonDTO(String search) {
        this.search = search;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Integer getPageNumber() {
        return pageNumber;
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

}
