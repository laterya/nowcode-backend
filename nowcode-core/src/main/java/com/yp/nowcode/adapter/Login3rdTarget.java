package com.yp.nowcode.adapter;

import com.yp.nowcode.model.vo.LoginUserVO;

/**
 * @author yp
 * @date: 2024/3/1
 */
public interface Login3rdTarget {

    public LoginUserVO loginByGitee(String code, String state);
}
