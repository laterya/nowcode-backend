package com.yp.nowcode.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yp.nowcode.model.entity.Chart;

import java.util.List;
import java.util.Map;

public interface ChartMapper extends BaseMapper<Chart> {

    List<Map<String, Object>> queryChartData(String querySql);
}




