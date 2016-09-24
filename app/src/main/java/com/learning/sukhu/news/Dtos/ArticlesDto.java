package com.learning.sukhu.news.Dtos;

/**
 * Created by sukhu on 2016-09-24.
 */
public class ArticlesDto {
    private String title;
    private String url;

    public ArticlesDto(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
