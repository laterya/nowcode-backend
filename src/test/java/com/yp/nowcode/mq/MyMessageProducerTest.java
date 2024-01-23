package com.yp.nowcode.mq;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author yp
 * @date: 2024/1/14
 */
@SpringBootTest
class MyMessageProducerTest {

    @Resource
    private MyMessageProducer myMessageProducer;

    @Test
    void sendMessage() {
        myMessageProducer.sendMessage("code_exchange", "my_routingKey", "测试信息发送");
    }
}