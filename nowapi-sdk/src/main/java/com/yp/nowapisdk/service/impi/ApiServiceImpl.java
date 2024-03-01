package com.yp.nowapisdk.service.impi;


import com.yp.nowapisdk.client.NowApiClient;
import com.yp.nowapisdk.exception.ApiException;
import com.yp.nowapisdk.model.request.LoveRequest;
import com.yp.nowapisdk.model.request.ShortLinkRequest;
import com.yp.nowapisdk.model.response.LoveTalkResponse;
import com.yp.nowapisdk.model.response.ShortLinkResponse;
import com.yp.nowapisdk.service.ApiService;
import com.yp.nowapisdk.service.BaseService;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class ApiServiceImpl extends BaseService implements ApiService {

    @Override
    public LoveTalkResponse getLoveTalk(LoveRequest loveRequest) throws ApiException {
        return request(loveRequest);
    }

    @Override
    public LoveTalkResponse getLoveTalk(NowApiClient nowApiClient, LoveRequest loveRequest) throws ApiException {
        return request(nowApiClient, loveRequest);
    }

    @Override
    public ShortLinkResponse getShortLink(ShortLinkRequest shortLinkRequest) throws ApiException {
        return request(shortLinkRequest);
    }

    @Override
    public ShortLinkResponse getShortLink(NowApiClient nowApiClient, ShortLinkRequest shortLinkRequest) throws ApiException {
        return request(nowApiClient, shortLinkRequest);
    }
}
