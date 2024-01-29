package com.yp.nowapisdk.service.impi;


import com.yp.nowapisdk.client.NowApiClient;
import com.yp.nowapisdk.exception.ApiException;
import com.yp.nowapisdk.model.request.RandomWallpaperRequest;
import com.yp.nowapisdk.model.response.RandomWallpaperResponse;
import com.yp.nowapisdk.service.ApiService;
import com.yp.nowapisdk.service.BaseService;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class ApiServiceImpl extends BaseService implements ApiService {

    @Override
    public RandomWallpaperResponse getRandomWallpaper(RandomWallpaperRequest request) throws ApiException {
        return request(request);
    }

    @Override
    public RandomWallpaperResponse getRandomWallpaper(NowApiClient nowApiClient, RandomWallpaperRequest request) throws ApiException {
        return request(nowApiClient, request);
    }
}
