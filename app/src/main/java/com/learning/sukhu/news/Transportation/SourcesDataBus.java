package com.learning.sukhu.news.Transportation;

import com.learning.sukhu.news.Dtos.SourcesDto;

import java.util.List;

/**
 * Created by sukhu on 2016-09-22.
 */
public interface SourcesDataBus {
    void processedData(List<SourcesDto> sources);
}
