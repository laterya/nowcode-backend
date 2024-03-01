package com.yp.nowcode.retry;

import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import org.junit.jupiter.api.Test;

/**
 * @author yp
 * @date: 2024/1/28
 */
class GuavaRetryTest {


    private int invokeCount = 0;

    public int realAction(int num) {
        invokeCount++;
        System.out.println(String.format("当前执行第 %d 次,num:%d", invokeCount, num));
        if (num <= 0) {
            throw new IllegalArgumentException();
        }
        return num;
    }

    @Test
    public void guavaRetryTest001() {
        Retryer<Integer> retryer = RetryerBuilder.<Integer>newBuilder()
                .withRetryListener(new MyRetryingListener())
                // 非正数进行重试
                .retryIfRuntimeException()
                // 偶数则进行重试
                .retryIfResult(result -> result % 2 == 0)
                // 设置最大执行次数3次
                .withStopStrategy(StopStrategies.stopAfterAttempt(3)).build();

        try {
            invokeCount = 0;
            retryer.call(() -> realAction(0));
        } catch (Exception e) {
            System.out.println("执行0，异常：" + e.getMessage());
        }

        try {
            invokeCount = 0;
            retryer.call(() -> realAction(1));
        } catch (Exception e) {
            System.out.println("执行1，异常：" + e.getMessage());
        }

        try {
            invokeCount = 0;
            retryer.call(() -> realAction(2));
        } catch (Exception e) {
            System.out.println("执行2，异常：" + e.getMessage());
        }
    }


}