package com.yp.nowapisdk.model.params;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author yp
 * @date: 2024/2/29
 */
@Data
@Accessors(chain = true)
public class ShortLinkParams {

    private String url;
}
