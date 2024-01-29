package com.yp.nowcode.service.inner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yp.nowcode.common.ErrorCode;
import com.yp.nowcode.exception.BusinessException;
import com.yp.nowcode.model.entity.User;
import com.yp.nowcode.service.UserService;
import com.yp.nowcodecommon.model.vo.UserVO;
import com.yp.nowcodecommon.service.inner.InnerUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;

@DubboService
public class InnerUserServiceImpl implements InnerUserService {
    @Resource
    private UserService userService;

    @Override
    public UserVO getInvokeUserByAccessKey(String accessKey) {
        if (StringUtils.isAnyBlank(accessKey)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getAccessKey, accessKey);
        User user = userService.getOne(userLambdaQueryWrapper);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }
}
