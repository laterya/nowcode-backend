package com.yp.nowcodecommon.service.inner;

import com.yp.nowcodecommon.model.vo.UserVO;

public interface InnerUserService {

    /**
     * 通过访问密钥获取invoke用户
     * 按凭证获取invoke用户
     *
     * @param accessKey 访问密钥
     * @return {@link UserVO}
     */
    UserVO getInvokeUserByAccessKey(String accessKey);
}
