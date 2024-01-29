package com.yp.nowapisdk.model.request;

import com.yp.nowapisdk.model.enums.RequestMethodEnum;
import com.yp.nowapisdk.model.params.RandomWallpaperParams;
import com.yp.nowapisdk.model.response.RandomWallpaperResponse;
import com.yp.nowapisdk.model.response.ResultResponse;
import lombok.experimental.Accessors;


@Accessors(chain = true)
public class RandomWallpaperRequest extends BaseRequest<RandomWallpaperParams, RandomWallpaperResponse> {
    @Override
    public String getPath() {
        return "/randomWallpaper";
    }

    /**
     * 获取响应类
     *
     * @return {@link Class}<{@link ResultResponse}>
     */
    @Override
    public Class<RandomWallpaperResponse> getResponseClass() {
        return RandomWallpaperResponse.class;
    }


    @Override
    public String getMethod() {
        return RequestMethodEnum.GET.getValue();
    }
}
