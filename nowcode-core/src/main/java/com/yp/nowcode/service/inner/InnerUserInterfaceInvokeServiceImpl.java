package com.yp.nowcode.service.inner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yp.nowcode.mapper.UserInterfaceInvokeMapper;
import com.yp.nowcode.service.InterfaceInfoService;
import com.yp.nowcode.service.UserService;
import com.yp.nowcodecommon.common.ErrorCode;
import com.yp.nowcodecommon.exception.BusinessException;
import com.yp.nowcodecommon.model.entity.UserInterfaceInvoke;
import com.yp.nowcodecommon.service.inner.InnerUserInterfaceInvokeService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author yp
 * @date: 2024/1/29
 */
@DubboService
public class InnerUserInterfaceInvokeServiceImpl extends ServiceImpl<UserInterfaceInvokeMapper, UserInterfaceInvoke>
        implements InnerUserInterfaceInvokeService {

    @Resource
    private InterfaceInfoService interfaceInfoService;
    @Resource
    private UserService userService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean invoke(Long interfaceInfoId, Long userId, Integer reduceScore) {
        LambdaQueryWrapper<UserInterfaceInvoke> invokeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        invokeLambdaQueryWrapper.eq(UserInterfaceInvoke::getInterfaceId, interfaceInfoId);
        invokeLambdaQueryWrapper.eq(UserInterfaceInvoke::getUserId, userId);
        UserInterfaceInvoke userInterfaceInvoke = this.getOne(invokeLambdaQueryWrapper);
        // 不存在就创建一条记录
        boolean invokeResult;
        if (userInterfaceInvoke == null) {
            userInterfaceInvoke = new UserInterfaceInvoke();
            userInterfaceInvoke.setInterfaceId(interfaceInfoId);
            userInterfaceInvoke.setUserId(userId);
            userInterfaceInvoke.setTotalInvokes(1L);
            invokeResult = this.save(userInterfaceInvoke);
        } else {
            LambdaUpdateWrapper<UserInterfaceInvoke> invokeLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            invokeLambdaUpdateWrapper.eq(UserInterfaceInvoke::getInterfaceId, interfaceInfoId);
            invokeLambdaUpdateWrapper.eq(UserInterfaceInvoke::getUserId, userId);
            invokeLambdaUpdateWrapper.setSql("totalInvokes = totalInvokes + 1");
            invokeResult = this.update(invokeLambdaUpdateWrapper);
        }
        // 更新接口总调用次数
        boolean interfaceUpdateInvokeSave = interfaceInfoService.updateTotalInvokes(interfaceInfoId);
        // 更新用户钱包积分
        boolean reduceWalletBalanceResult = userService.reduceWalletBalance(userId, reduceScore);
        boolean updateResult = invokeResult && interfaceUpdateInvokeSave && reduceWalletBalanceResult;
        if (!updateResult) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "调用失败");
        }
        return true;
    }
}
