package com.yp.nowcode.model.vo;

import lombok.Data;

/**
 * Bi 的返回结果
 */
@Data
public class BiResponse {

    /**
     * 生成的图表
     */
    private String genChart;

    /**
     * 生成的图表的分析结果
     */
    private String genResult;

    /**
     * 图表的id
     */
    private Long chartId;
}
