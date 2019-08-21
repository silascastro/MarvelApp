package com.scm.app.marvelapp;

import java.util.ArrayList;
import java.util.List;

public class Response {
    private int page;
    private int total_results;
    private int total_pages;
    private List<Movies> results;

    public Response(int page, int total_results, int total_pages, List<Movies> movies) {
        this.page = page;
        this.total_results = total_results;
        this.total_pages = total_pages;
        this.results = movies;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public List<Movies> getMovies() {
        return results;
    }

    public void setMovies(List<Movies> movies) {
        this.results = movies;
    }
}
