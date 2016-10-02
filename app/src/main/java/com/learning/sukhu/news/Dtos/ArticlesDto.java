package com.learning.sukhu.news.Dtos;

/**
 * Created by sukhu on 2016-09-24.
 */
public class ArticlesDto {
    private String heading;
    private String articleImageUrl;
    private String description;
    private String time;
    private String url;

    public ArticlesDto(String heading, String articleImageUrl, String description, String time, String url) {
        this.heading = heading;
        this.articleImageUrl = articleImageUrl;
        this.description = description;
        this.time = time;
        this.url = url;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getArticleImageUrl() {
        return articleImageUrl;
    }

    public void setArticleImageUrl(String articleImageUrl) {
        this.articleImageUrl = articleImageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
