package com.yp.nowcode.adapter;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yp.nowcode.model.vo.LoginUserVO;
import com.yp.nowcode.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author yp
 * @date: 2024/3/1
 */
@Component
@PropertySource("classpath:/dev/settings.properties")
public class Login3rdGiteeAdapter implements Login3rdTarget {

    @Resource
    private UserService userService;

    @Value("${gitee.tokenUrl}")
    private String tokenUrl;

    @Value("${gitee.userInfoUrl}")
    private String userInfoUrl;

    @Value("${gitee.userPrefix}")
    private String userPrefix;

    @Value("${gitee.state}")
    private String state;

    @Override
    public LoginUserVO loginByGitee(String code, String state) {
        // 进行state判断，state的值是前端与后端商定好的，前端将state传递给gitee平台，gitee平台会将state原样返回给回调接口
        if (!state.equals(this.state)) {
            throw new UnsupportedOperationException("state不正确");
        }
        // 通过code获取accessToken,code从回调接口中获取
        String token = null;
        try (HttpResponse response = HttpUtil.createPost(tokenUrl + code).execute()) {
            String body = response.body();
            token = JSONUtil.parseObj(body).getStr("access_token");
        }
        // 通过accessToken获取用户信息
        String userAccount = null;
        String name = null;
        String userAvatar = null;
        String email = null;
        try (HttpResponse execute = HttpUtil.createGet(userInfoUrl + token).execute()) {
            String body = execute.body();
            JSONObject entries = JSONUtil.parseObj(body);
            userAccount = entries.getStr("id");
            name = entries.getStr("name");
            userAvatar = entries.getStr("avatar_url");
            email = entries.getStr("email");
        }

        return autoRegister3rdAndLogin(userAccount, name, userAvatar, email);
    }

    private LoginUserVO autoRegister3rdAndLogin(String userAccount, String userName, String userAvatar, String email) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        if (!userService.checkUserExist(userAccount)) {
            userService.user3rdRegister(userAccount, userName, userAvatar, email, "12345678");
        }
        return userService.userLogin(userAccount, "12345678", request);
    }
}
