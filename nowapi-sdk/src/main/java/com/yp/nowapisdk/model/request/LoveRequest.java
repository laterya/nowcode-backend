package com.yp.nowapisdk.model.request;


import com.yp.nowapisdk.model.enums.RequestMethodEnum;
import com.yp.nowapisdk.model.response.LoveTalkResponse;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class LoveRequest extends BaseRequest<String, LoveTalkResponse> {

    @Override
    public String getPath() {
        return "/loveTalk";
    }

    @Override
    public Class<LoveTalkResponse> getResponseClass() {
        return LoveTalkResponse.class;
    }


    @Override
    public String getMethod() {
        return RequestMethodEnum.GET.getValue();
    }
}
