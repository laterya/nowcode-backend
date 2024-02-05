package com.yp.nowcode.manager;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author yp
 * @date: 2024/1/13
 */
@SpringBootTest
class RedisLimiterManagerTest {

    @Resource
    private RedisLimiterManager redisLimiterManager;

    @Test
    void doRateLimit() throws InterruptedException {
        String userId = "1";
        for (int i = 0; i < 2; i++) {
            redisLimiterManager.doRateLimit(userId);
            System.out.println("成功");
        }
        Thread.sleep(1000);
        System.out.println("===========");
        // 连续请求5次，限流，抛出异常
        for (int i = 0; i < 5; i++) {
//            redisLimiterManager.doRateLimit(userId);
            System.out.println("成功");
        }
    }
}