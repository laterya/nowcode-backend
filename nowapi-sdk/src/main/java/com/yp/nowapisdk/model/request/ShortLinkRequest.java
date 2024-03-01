package com.yp.nowapisdk.model.request;


import com.yp.nowapisdk.model.enums.RequestMethodEnum;
import com.yp.nowapisdk.model.params.ShortLinkParams;
import com.yp.nowapisdk.model.response.ShortLinkResponse;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class ShortLinkRequest extends BaseRequest<ShortLinkParams, ShortLinkResponse> {

    @Override
    public String getPath() {
        return "/shortLink";
    }

    @Override
    public Class<ShortLinkResponse> getResponseClass() {
        return ShortLinkResponse.class;
    }

    @Override
    public String getMethod() {
        return RequestMethodEnum.GET.getValue();
    }
}
