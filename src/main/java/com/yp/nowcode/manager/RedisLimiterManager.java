package com.yp.nowcode.manager;

import com.yp.nowcode.common.ErrorCode;
import com.yp.nowcode.exception.BusinessException;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author yp
 * @date: 2024/1/13
 */
@Component
public class RedisLimiterManager {
    @Resource
    private RedissonClient redissonClient;

    public void doRateLimit(String key) {
        // 创建一个限流器
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(key);
        // 限流1分钟内2个请求
        rateLimiter.trySetRate(RateType.OVERALL, 2, 1, RateIntervalUnit.MINUTES);
        // 每当一个操作来了后，请求一个令牌
        boolean canOp = rateLimiter.tryAcquire(1);
        if (!canOp) {
            throw new BusinessException(ErrorCode.TOO_MANY_REQUEST);
        }
    }
}
