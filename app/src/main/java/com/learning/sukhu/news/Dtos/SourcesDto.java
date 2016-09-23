package com.learning.sukhu.news.Dtos;

/**
 * Created by sukhu on 2016-09-22.
 */
public class SourcesDto {
    private String id;
    private String name;
    private String category;
    private String urlSize;

    public SourcesDto(String id, String name, String category, String urlSize) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.urlSize = urlSize;
    }

    @Override
    public String toString() {
        return "Sources{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", urlSize='" + urlSize + '\'' +
                '}';
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlSize() {
        return urlSize;
    }

    public void setUrlSize(String urlSize) {
        this.urlSize = urlSize;
    }
}
