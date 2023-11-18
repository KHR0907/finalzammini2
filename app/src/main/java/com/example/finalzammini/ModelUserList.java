package com.example.finalzammini;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelUserList {

    private int page;

    @SerializedName("per_page")
    private int perPage;

    private int total;

    @SerializedName("total_pages")
    private int totalPages;

    private List<ModelUser> data;

    public int getPage() { return page; }

    public void setPage(int page) { this.page = page; }

    public int getPerPage() { return perPage; }

    public void setPerPage(int perPage) { this.perPage = perPage; }

    public int getTotal() { return total; }

    public void setTotal(int total) { this.total = total; }

    public int getTotalPages() { return totalPages; }

    public void setTotalPages(int totalPages) { this.totalPages = totalPages; }

    public List<ModelUser> getData() { return data; }

    public void setData(List<ModelUser> data) { this.data = data; }

    @Override
    public String toString() {
        return "ModelUserList {" +
                "\n  page=" + page +
                "\n, perPage=" + perPage +
                "\n, total=" + total +
                "\n, totalPages=" + totalPages +
                "\n, data=" + data.toString() +
                "\n}";
    }
}
