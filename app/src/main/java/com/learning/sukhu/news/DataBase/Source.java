package com.learning.sukhu.news.DataBase;

/**
 * Created by sinsukhv on 9/27/2016.
 */
public class Source {
    private String name;
    private String sourceId;

    public Source(String name, String sourceId) {
        this.name = name;
        this.sourceId = sourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }
}
