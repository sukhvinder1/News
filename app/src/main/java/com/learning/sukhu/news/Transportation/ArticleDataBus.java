package com.learning.sukhu.news.Transportation;

import com.learning.sukhu.news.Dtos.ArticlesDto;

import java.util.List;

/**
 * Created by sukhu on 2016-09-24.
 */
public interface ArticleDataBus {
    void processedData(List<ArticlesDto> sources);
}
