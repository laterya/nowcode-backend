package com.yp.nowcodeinterfaces.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yp.nowapisdk.exception.ApiException;
import com.yp.nowapisdk.model.params.ShortLinkParams;
import com.yp.nowapisdk.model.response.ShortLinkResponse;
import com.yp.nowcodeinterfaces.utils.RequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yp
 * @date: 2024/2/1
 */
@RestController
@Slf4j
@RequestMapping("/")
public class InterfacesController {
    @GetMapping("/loveTalk")
    public String randomLoveTalk() {
        return RequestUtils.get("https://api.vvhan.com/api/love");
    }

    @GetMapping("/poisonousChickenSoup")
    public String getPoisonousChickenSoup() {
        return RequestUtils.get("https://api.btstu.cn/yan/api.php?charset=utf-8&encode=json");
    }

    @GetMapping("/shortLink")
    public ShortLinkResponse getShortLink(ShortLinkParams shortLinkParams) throws ApiException {
        String response = RequestUtils.get("https://url.hdgxl.com/api.php", shortLinkParams);
        JSONObject jsonObject = JSONUtil.parseObj(response);
        String url = (String) jsonObject.get("code");
        String msg = (String) jsonObject.get("msg");
        ShortLinkResponse shortLinkResponse = new ShortLinkResponse();
        shortLinkResponse.setShortLinkUrl(url);
        shortLinkResponse.setMsg(msg);
        return shortLinkResponse;
    }
}
