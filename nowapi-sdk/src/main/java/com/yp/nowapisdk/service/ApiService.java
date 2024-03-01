package com.yp.nowapisdk.service;

import cn.hutool.http.HttpResponse;
import com.yp.nowapisdk.client.NowApiClient;
import com.yp.nowapisdk.exception.ApiException;
import com.yp.nowapisdk.model.request.BaseRequest;
import com.yp.nowapisdk.model.request.LoveRequest;
import com.yp.nowapisdk.model.request.ShortLinkRequest;
import com.yp.nowapisdk.model.response.LoveTalkResponse;
import com.yp.nowapisdk.model.response.ResultResponse;
import com.yp.nowapisdk.model.response.ShortLinkResponse;


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

    LoveTalkResponse getLoveTalk(LoveRequest loveRequest) throws ApiException;

    LoveTalkResponse getLoveTalk(NowApiClient nowApiClient, LoveRequest loveRequest) throws ApiException;

    ShortLinkResponse getShortLink(ShortLinkRequest shortLinkRequest) throws ApiException;

    ShortLinkResponse getShortLink(NowApiClient nowApiClient, ShortLinkRequest shortLinkRequest) throws ApiException;
}
