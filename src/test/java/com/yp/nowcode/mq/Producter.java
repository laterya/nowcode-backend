package com.yp.nowcode.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.yp.nowcode.common.ErrorCode;
import com.yp.nowcode.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author yp
 * @date: 2024/1/27
 */
@SpringBootTest
@Slf4j
public class Producter {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @BeforeAll
    static void initBiMq() {
        log.info("初始化BiMq");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            String EXCHANGE_NAME = "bi_exchange_test";
            channel.exchangeDeclare(EXCHANGE_NAME, "direct",
                    // 添加可选参数，设置为持久化以应对服务器重启等情况
                    true);

            String queueName = "bi_queue_test";
            String BI_ROUTING_KEY = "bi_routeKey_test";
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, EXCHANGE_NAME, BI_ROUTING_KEY);

        } catch (TimeoutException | IOException e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "创建消息队列异常");
        }
    }

    @Test
    public void sendMessage() {
        rabbitTemplate.convertAndSend("bi_exchange_test", "bi_routeKey_test", "数据1");
    }
}
