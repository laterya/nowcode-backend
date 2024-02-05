package com.yp.nowcode.service;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yp.nowcode.model.dto.chart.ChartQueryRequest;
import com.yp.nowcode.model.entity.Chart;

/**
 *
 */
public interface ChartService extends IService<Chart> {

    Wrapper<Chart> getQueryWrapper(ChartQueryRequest chartQueryRequest);
}
