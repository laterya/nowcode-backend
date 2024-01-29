package com.yp.nowapisdk.service;

import cn.hutool.http.HttpResponse;
import com.yp.nowapisdk.client.NowApiClient;
import com.yp.nowapisdk.exception.ApiException;
import com.yp.nowapisdk.model.request.BaseRequest;
import com.yp.nowapisdk.model.request.RandomWallpaperRequest;
import com.yp.nowapisdk.model.response.RandomWallpaperResponse;
import com.yp.nowapisdk.model.response.ResultResponse;


public interface ApiService {
    /**
     * 通用请求
     *
     * @param request 要求
     * @return {@link HttpResponse}
     * @throws ApiException 业务异常
     */

    <O, T extends ResultResponse> T request(BaseRequest<O, T> request) throws ApiException;

    /**
     * 通用请求
     *
     * @param nowApiClient qi api客户端
     * @param request      要求
     * @return {@link T}
     * @throws ApiException 业务异常
     */
    <O, T extends ResultResponse> T request(NowApiClient nowApiClient, BaseRequest<O, T> request) throws ApiException;

    /**
     * 获取随机壁纸
     *
     * @param request 要求
     * @return {@link RandomWallpaperResponse}
     * @throws ApiException 业务异常
     */
    RandomWallpaperResponse getRandomWallpaper(RandomWallpaperRequest request) throws ApiException;

    /**
     * 获取随机壁纸
     *
     * @param nowApiClient qi api客户端
     * @param request      要求
     * @return {@link RandomWallpaperResponse}
     * @throws ApiException 业务异常
     */
    RandomWallpaperResponse getRandomWallpaper(NowApiClient nowApiClient, RandomWallpaperRequest request) throws ApiException;
}
