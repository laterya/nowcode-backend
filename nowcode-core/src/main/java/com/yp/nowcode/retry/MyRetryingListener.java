package com.yp.nowcode.retry;

import com.github.rholder.retry.Attempt;
import com.github.rholder.retry.RetryListener;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yp
 * @date: 2024/3/1
 * 重试监听器
 */
@Slf4j
public class MyRetryingListener implements RetryListener {
    @Override
    public <V> void onRetry(Attempt<V> attempt) {
    }
}
